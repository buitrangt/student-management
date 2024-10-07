package org.aibles.quanlysinhvien.exception;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
@Setter
public class BaseException extends RuntimeException {
    private String code;
    private Timestamp timestamp;
    private String message;

    public BaseException(String message, String code) {
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
        this.message = message;
        this.code = code;
    }

    public BaseException() {
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }
}