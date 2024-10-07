package org.aibles.quanlysinhvien.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super();
        this.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        this.setMessage(message);
    }
}
