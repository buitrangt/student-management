package org.aibles.quanlysinhvien.constant;

public enum ResponseCode {
    SUCCESS("success"),
    NOT_FOUND("not_found"),
    BAD_REQUEST("bad_request"),
    CONFLICT("conflict"),
    FORBIDDEN("forbidden"),
    UNAUTHORIZED("unauthorized"),
    INTERNAL_SERVER_ERROR("internal_server_error");





    private final String value;

    ResponseCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
//cai nay ha
//