package org.aibles.quanlysinhvien.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Void>> handleBusinessException(BusinessException businessException) {
        return new ResponseEntity<>(
                BaseResponse.fail(businessException.getResponseStatus()),
                businessException.getResponseStatus().getStatus()
        );
    }


}

