package org.aibles.quanlysinhvien.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.exception.ErrorResponse;
import org.aibles.quanlysinhvien.exception.NotFoundException;
import org.aibles.quanlysinhvien.exception.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidRequestException(InvalidRequestException exception) {
        log.error("Invalid request: {}", exception.getMessage());
        ErrorResponse<String> errorResponse = new ErrorResponse<>(ResponseCode.INVALID_REQUEST.getValue(), // Sử dụng ResponseCode.INVALID_REQUEST
                System.currentTimeMillis(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleStudentCourseNotFoundException(NotFoundException exception) {
        log.info("(handleStudentCourseNotFoundException) : {}", exception.getMessage());
        ErrorResponse<String> errorResponse = new ErrorResponse<>(exception.getCode(), exception.getTimestamp().getTime(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}

