package com.khorcha.filters;

import com.khorcha.utils.ThreadLocalEmail;
import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ServerFilter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.http.filter.ServerFilterPhase;
import io.micronaut.security.utils.SecurityService;
import org.reactivestreams.Publisher;


@Filter(Filter.MATCH_ALL_PATTERN)
public class JwtEmailFilter implements HttpServerFilter {
    private final SecurityService securityService;

    public JwtEmailFilter(SecurityService securityService) {
        this.securityService = securityService;
    }


    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        // Extract the email from the SecurityService (Micronaut's way to access the JWT)
        securityService.getAuthentication().ifPresent(authentication -> {
            String email = (String) authentication.getAttributes().get("email");
            if (email != null) {
                ThreadLocalEmail.setEmail(email); // Store in ThreadLocal
            }
        });

        return chain.proceed(request);
    }

    @Override
    public int getOrder(){
        return ServerFilterPhase.SECURITY.after();
    }
}