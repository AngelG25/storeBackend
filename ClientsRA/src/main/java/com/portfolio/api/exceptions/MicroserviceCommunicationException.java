package com.portfolio.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class MicroserviceCommunicationException extends RuntimeException {

  public MicroserviceCommunicationException(String message) {
    super(message);
  }
}
