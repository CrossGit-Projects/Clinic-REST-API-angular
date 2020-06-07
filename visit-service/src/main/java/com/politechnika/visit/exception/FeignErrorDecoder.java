package com.politechnika.visit.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultError = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            throw new VisitEnrollmentException(("User does not exist"));
        } else if (HttpStatus.valueOf(response.status()).is2xxSuccessful()) {
            throw new VisitEnrollmentException(("User cannot be enroll on visit"));
        }

        return defaultError.decode(methodKey, response);
    }
}
