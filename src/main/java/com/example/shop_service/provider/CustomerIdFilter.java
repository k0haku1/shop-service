package com.example.shop_service.provider;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomerIdFilter extends OncePerRequestFilter {

    private final CustomerIdRequestBean customerIdRequestBean;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String customerIdHeader = request.getHeader("customerId");

        if (customerIdHeader != null) {
            try {
                customerIdRequestBean.setCustomerId(Long.parseLong(customerIdHeader));
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Customer ID");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'customerId' header");
            return;
        }

        filterChain.doFilter(request, response);

    }
}
