package com.boltech.service_insurance.audit;

import com.boltech.service_insurance.security.dto.AuthorizationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null) {
                AuthorizationToken authorizationToken = (AuthorizationToken) auth.getPrincipal();
                return Optional.ofNullable(authorizationToken.getUserId());
            }
        } catch (Exception ex) {
            log.debug("Unable to get current auditor errorMessage[{}]", ex.getMessage());
        }

        return Optional.empty();
    }

}
