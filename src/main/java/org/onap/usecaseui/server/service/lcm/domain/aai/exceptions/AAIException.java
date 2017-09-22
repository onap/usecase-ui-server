package org.onap.usecaseui.server.service.lcm.domain.aai.exceptions;

public class AAIException extends RuntimeException {

    public AAIException(String message, Throwable cause) {
        super(message, cause);
    }
}
