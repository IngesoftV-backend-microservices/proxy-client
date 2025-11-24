package com.selimhorri.app.config.feature;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "features")
@Data
public class FeatureToggleProperties {
    
    private Map<String, Boolean> toggles = new HashMap<>();
    
    public boolean isEnabled(String featureName) {
        return toggles.getOrDefault(featureName, false);
    }
    
    public boolean isDisabled(String featureName) {
        return !isEnabled(featureName);
    }
}

