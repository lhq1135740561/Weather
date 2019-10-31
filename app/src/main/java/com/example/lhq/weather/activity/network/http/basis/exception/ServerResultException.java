package com.example.lhq.weather.activity.network.http.basis.exception;

public class ServerResultException extends BaseException {

    public ServerResultException(int message, String errorCode) {
        super(message, errorCode);
    }
}
