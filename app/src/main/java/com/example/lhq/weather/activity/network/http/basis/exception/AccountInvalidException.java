package com.example.lhq.weather.activity.network.http.basis.exception;

import com.example.lhq.weather.activity.network.http.basis.config.HttpCode;

public class AccountInvalidException extends BaseException {

    public AccountInvalidException() {
        super(HttpCode.CODE_ACCOUNT_INVALID,"用户或者密码错误");
    }
}
