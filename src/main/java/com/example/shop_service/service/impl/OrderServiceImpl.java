package com.example.shop_service.service.impl;

import com.example.shop_service.enumeration.OrderStatus;
import com.example.shop_service.exception.AccessDeniedException;
import com.example.shop_service.exception.NotAvailableOrNotEnoughAmountException;
import com.example.shop_service.exception.OrderCannotBeCancelledException;
import com.example.shop_service.exception.OrderNotFoundException;
import com.example.shop_service.exception.ProductNotFound;
import com.example.shop_service.persistence.models.CustomerEntity;
import com.example.shop_service.persistence.models.OrderEntity;
import com.example.shop_service.persistence.models.OrderProductEntity;
import com.example.shop_service.persistence.models.OrderProductKey;
import com.example.shop_service.persistence.models.Product;
import com.example.shop_service.persistence.projection.CompressedProductForOrderProjection;
import com.example.shop_service.persistence.repository.OrderProductRepository;
import com.example.shop_service.persistence.repository.OrderRepository;
import com.example.shop_service.persistence.repository.ProductRepository;
import com.example.shop_service.service.CustomerService;
import com.example.shop_service.service.OrderService;
import com.example.shop_service.service.dto.OrderRequest;
import com.example.shop_service.service.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final CustomerService customerServiceImpl;
    private final ProductRepository productRepository;


    @Transactional
    public UUID createOrder(Long customerId, OrderRequest orderRequest) {

        CustomerEntity customer = customerServiceImpl.getCustomerById(customerId);

        OrderEntity orderEntity = OrderEntity.builder()
                .customer(customer)
                .status(OrderStatus.CREATED)
                .deliveryAddress(orderRequest.getDeliveryAddress())
                .build();

        List<OrderProductEntity> orderProducts = buildOrderProducts(orderEntity, orderRequest);

        orderEntity.setOrderProducts(orderProducts);
        orderRepository.save(orderEntity);

        List<CompressedProductForOrderProjection> products = orderProductRepository.findProductsByOrderId(orderEntity.getId());

        BigDecimal totalPrice = products.stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderResponse(orderEntity.getId(), products, totalPrice).getOrderId();
    }

    @Transactional
    public UUID addProductToOrder(Long customerId, UUID orderId, List<OrderRequest.CompressedProduct> products) {
        OrderEntity order = findById(orderId);

        if (checkCustomer(order, customerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to modify this order");
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order status is not CREATED, cannot add products");
        }

        Map<UUID, Product> productMap = productRepository.findAllById(
                        products.stream().map(OrderRequest.CompressedProduct::getId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(Product::getId, product -> product));

        for (OrderRequest.CompressedProduct compressedProduct : products) {
            Product productEntity = productMap.get(compressedProduct.getId());
            if (productEntity == null) {
                throw new ProductNotFound(compressedProduct.getId());
            }

            if (!productEntity.getIs_available() || productEntity.getQuantity() < compressedProduct.getQuantity()) {
                throw new NotAvailableOrNotEnoughAmountException(compressedProduct.getId());
            }

            Optional<OrderProductEntity> existingOrderProduct = order.getOrderProducts().stream()
                    .filter(orderProduct -> orderProduct.getProduct().getId().equals(compressedProduct.getId()))
                    .findFirst();

            if (existingOrderProduct.isPresent()) {
                OrderProductEntity productInOrder = existingOrderProduct.get();
                productInOrder.setQuantity(productInOrder.getQuantity() + compressedProduct.getQuantity());
                productEntity.setQuantity(productEntity.getQuantity() - compressedProduct.getQuantity());  // обновляем склад
                productInOrder.setPrice(productEntity.getPrice());  // обновляем цену
            } else {
                OrderProductKey key = new OrderProductKey(order.getId(), productEntity.getId());
                OrderProductEntity newOrderProduct = OrderProductEntity.builder()
                        .id(key)
                        .order(order)
                        .product(productEntity)
                        .quantity(compressedProduct.getQuantity())
                        .price(productEntity.getPrice())
                        .build();
                order.getOrderProducts().add(newOrderProduct);
            }
        }

        return orderRepository.save(order).getId();
    }

    @Transactional
    public OrderResponse findOrderById(Long customerId, UUID orderId) {

        OrderEntity order = findById(orderId);

        if (checkCustomer(order, customerId)) {
            throw new AccessDeniedException("You cannot cancel this order");
        }

        List <CompressedProductForOrderProjection> products = orderProductRepository.findProductsByOrderId(orderId);

        Map<UUID, BigDecimal> currentPrices = productRepository.findAllById(
                products.stream().map(CompressedProductForOrderProjection::getId).collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(Product::getId, Product::getPrice));

        BigDecimal totalPrice = products.stream()
                .map(p -> currentPrices.get(p.getId()).multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return OrderResponse.builder()
                .orderId(order.getId())
                .products(products)
                .totalPrice(totalPrice)
                .build();
    }

    @Transactional
    public void deleteOrder(Long customerId, UUID orderId) {

        OrderEntity order = findById(orderId);

        if (checkCustomer(order, customerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete this order");
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new OrderCannotBeCancelledException(orderId);
        }

        order.setStatus(OrderStatus.CANCELLED);

        for(OrderProductEntity orderProduct : order.getOrderProducts()) {
            Product productEntity = orderProduct.getProduct();
            productEntity.setQuantity(productEntity.getQuantity() + orderProduct.getQuantity());
            orderProductRepository.delete(orderProduct);
        }

        order.getOrderProducts().clear();

    }

    @Transactional
    public OrderResponse confirmOrder(UUID orderId) {
        return null;
    }

    @Transactional
    public UUID changeOrderStatus (UUID orderId, OrderStatus orderStatus, Long customerId) {

        OrderEntity order = findById(orderId);

        if (checkCustomer(order, customerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete this order");
        }

        order.setStatus(orderStatus);

        return orderRepository.save(order).getId();

    }

    private OrderEntity findById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private Boolean checkCustomer(OrderEntity order, Long customerId) {
        CustomerEntity customer = customerServiceImpl.getCustomerById(customerId);
        return !Objects.equals(customer.getId(), order.getCustomer().getId());
    }

    private List<OrderProductEntity> buildOrderProducts(OrderEntity orderEntity, OrderRequest orderRequest) {

        List<UUID> productIds = orderRequest.getProducts().stream()
                .map(OrderRequest.CompressedProduct::getId)
                .toList();

        Map<UUID, Product> productsMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        return orderRequest.getProducts().stream()
                .map(productRequest -> {
                    Product productEntity = productsMap.get(productRequest.getId());

                    if (productEntity == null || !productEntity.getIs_available() || productEntity.getQuantity() < productRequest.getQuantity()) {
                        throw new NotAvailableOrNotEnoughAmountException(productRequest.getId());
                    }

                    productEntity.setQuantity(productEntity.getQuantity() - productRequest.getQuantity());
                    productRepository.save(productEntity);

                    OrderProductKey key = new OrderProductKey(orderEntity.getId(), productEntity.getId());
                    return OrderProductEntity.builder()
                            .id(key)
                            .order(orderEntity)
                            .product(productEntity)
                            .quantity(productRequest.getQuantity())
                            .price(productRequest.getPrice())
                            .build();
                })
                .toList();
    }

}
