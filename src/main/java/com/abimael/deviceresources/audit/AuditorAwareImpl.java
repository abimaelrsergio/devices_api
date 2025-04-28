package com.abimael.deviceresources.audit;

import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * Returns the current auditor, which is the microservice that is performing the action.
     *
     * @return the current auditor
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Device_MS");
    }
}
