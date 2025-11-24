package com.selimhorri.app.config.feature;

public class FeatureNotEnabledException extends RuntimeException {
    
    public FeatureNotEnabledException(String message) {
        super(message);
    }
}

