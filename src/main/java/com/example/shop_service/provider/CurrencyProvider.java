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
public class CurrencyProvider extends OncePerRequestFilter {

    private final CurrencySessionBean currencySession;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        String currencyHeader = request.getHeader("currency");

        if (currencyHeader != null) {
            currencySession.setCurrency(currencyHeader);
        }

        filterChain.doFilter(request, response);

    }

}
