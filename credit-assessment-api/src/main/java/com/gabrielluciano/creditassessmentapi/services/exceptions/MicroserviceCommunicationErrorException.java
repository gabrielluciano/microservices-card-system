package com.gabrielluciano.creditassessmentapi.services.exceptions;

import lombok.Getter;

@Getter
public class MicroserviceCommunicationErrorException extends Exception {

    private final Integer status;

    public MicroserviceCommunicationErrorException(String msg, Integer status, Throwable cause) {
        super(msg, cause);
        this.status = status;
    }
}
