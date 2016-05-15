package org.ruthie.exec.rbc.service;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7246737802983373931L;
    private static final String errMsgTemplate = "%s '%s' is not found.";
    
    public ResourceNotFoundException(Class<?> clazz, String key) {
        super(String.format(errMsgTemplate, clazz.getSimpleName(), key));
    }
}
