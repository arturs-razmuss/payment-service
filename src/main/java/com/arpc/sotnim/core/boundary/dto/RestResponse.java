package com.arpc.sotnim.core.boundary.dto;

import com.arpc.sotnim.core.boundary.ErrorCodes;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RestResponse<T> {

    private String code;
    private String message;
    private T data;

    public static <T> RestResponse<T> success(T data) {
        RestResponse<T> response = new RestResponse<>();
        response.code = "00000";
        response.message = "success";
        response.data = data;
        return response;
    }

    public static <T> RestResponse<T> error(ErrorCodes errorCodes) {
        RestResponse<T> response = new RestResponse<>();
        response.code = errorCodes.getCode();
        response.message = errorCodes.getMessage();
        response.data = null;
        return response;
    }
}