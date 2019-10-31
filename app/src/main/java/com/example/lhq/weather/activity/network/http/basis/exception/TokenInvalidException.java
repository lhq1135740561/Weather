package com.example.lhq.weather.activity.network.http.basis.exception;

import com.example.lhq.weather.activity.network.http.basis.config.HttpCode;

public class TokenInvalidException extends BaseException {

    public TokenInvalidException() {
        super(HttpCode.CODE_TOKEN_INVALID,"Token失效");
    }
}
