package com.epam.interview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoClientExistsException extends ServiceException {

    public NoClientExistsException() {
        super();
    }

    public NoClientExistsException(String message) {
        super(message);
    }

}
