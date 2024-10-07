package org.aibles.quanlysinhvien.dto.response;

import lombok.Data;
import org.aibles.quanlysinhvien.constant.ResponseCode;

@Data
public class BaseResponse<T> {
    private String code;
    private long timestamp;
    private T data;

    public BaseResponse(ResponseCode responseCode, long timestamp, T data) {
        this.code = responseCode.getValue();
        this.timestamp = timestamp;
        this.data = data;
    }
}
