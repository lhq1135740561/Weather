package com.example.lhq.weather.activity.network.http.basis.exception;

import com.example.lhq.weather.activity.network.http.basis.config.HttpCode;

public class ParamterInvalidException extends BaseException {

    public ParamterInvalidException() {

        super(HttpCode.CODE_PARAMETER_INVALID,"参数有误");
    }
}
