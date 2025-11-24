package com.selimhorri.app.config.feature;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeatureToggleService {
    
    private final FeatureToggleProperties featureToggleProperties;
    
    public boolean isFeatureEnabled(String featureName) {
        boolean enabled = featureToggleProperties.isEnabled(featureName);
        log.debug("Feature '{}' is {}", featureName, enabled ? "enabled" : "disabled");
        return enabled;
    }
    
    public boolean isFeatureDisabled(String featureName) {
        return !isFeatureEnabled(featureName);
    }
    
    public void requireFeature(String featureName) {
        if (isFeatureDisabled(featureName)) {
            throw new FeatureNotEnabledException("Feature '" + featureName + "' is not enabled");
        }
    }
}

