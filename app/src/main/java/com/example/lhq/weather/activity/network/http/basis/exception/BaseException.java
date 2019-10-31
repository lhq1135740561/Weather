package com.example.lhq.weather.activity.network.http.basis.exception;

import com.example.lhq.weather.activity.network.http.basis.config.HttpCode;

/**
 *自定义 BaseException
 */

public class BaseException extends RuntimeException {

    private int errorCode = HttpCode.CODE_UNKNOWN;

    public BaseException() {
    }

    public BaseException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
