package com.example.lhq.weather.activity.network.http.basis.exception;

import com.example.lhq.weather.activity.network.http.basis.config.HttpCode;

public class ResultInvalidException extends BaseException {

    public ResultInvalidException() {
        super(HttpCode.CODE_RESULT_INVALID,"无效请求");
    }
}
