package com.lumidion.cars.components;

import com.lumidion.cars.repository.ApiKeyRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!Objects.equals(request.getRequestURI(), "/customers")
                && request.getRequestURI().matches("^/customers/.*")) {
            DefaultBearerTokenResolver resolver = new DefaultBearerTokenResolver();
            String token = resolver.resolve(request);
            String apiKey = new String(Base64.getDecoder().decode(token)).trim();

            try {
                Optional<Integer> customerIdOption = apiKeyRepository.getCustomerIdFromApiKey(apiKey);

                if (customerIdOption.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                } else {
                    try {
                        String customerIdAsString = request.getRequestURI()
                                .replaceFirst("^/customers/", "")
                                .replaceFirst("/.*", "");
                        Integer customerId = Integer.decode(customerIdAsString);

                        if (!Objects.equals(customerId, customerIdOption.get())) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            return;
                        } else {
                            Authentication authentication = new BearerTokenAuthentication(
                                    new DefaultOAuth2User(null, Map.of("id", customerId), "id"),
                                    new OAuth2AccessToken(
                                            OAuth2AccessToken.TokenType.BEARER,
                                            apiKey,
                                            Instant.now(),
                                            Instant.now().plusMillis(60000),
                                            Set.of()),
                                    null);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    } catch (Exception err) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                }
            } catch (Exception err) {
                logger.error(err.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

        filterChain.doFilter(request, response);
    }
}
