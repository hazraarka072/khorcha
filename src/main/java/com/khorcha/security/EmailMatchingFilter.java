package com.khorcha.security;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.http.filter.ServerFilterPhase;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.utils.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

import java.util.Optional;

@Slf4j
@Filter(Filter.MATCH_ALL_PATTERN)
public class EmailMatchingFilter implements HttpServerFilter {
    private final SecurityService securityService;

    public EmailMatchingFilter(SecurityService securityService) {
        this.securityService = securityService;
    }


    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        // Extract the email from the SecurityService (Micronaut's way to access the JWT)
        Optional<Authentication> authOpt = securityService.getAuthentication();
        if (authOpt.isEmpty())
            return Publishers.just(HttpResponse.ok("Testing"));

        Authentication authentication = authOpt.get();
        String jwtEmail = (String) authentication.getAttributes().get("email");
        String pathEmail = getEmailFromRequest(request);

        log.info("Received request for email {} with jwt email {}", pathEmail, jwtEmail);

        if (!pathEmail.equalsIgnoreCase(jwtEmail))
            return Publishers.just(HttpResponse.unauthorized());

        return chain.proceed(request);
    }

    private String getEmailFromRequest(HttpRequest<?> request) {
        String path = request.getPath();
        String[] pathArray = path.split("/");
        return pathArray[2];
    }

    @Override
    public int getOrder(){
        return ServerFilterPhase.SECURITY.after();
    }
}
