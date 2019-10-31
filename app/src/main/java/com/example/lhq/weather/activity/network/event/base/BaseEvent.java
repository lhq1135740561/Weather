package com.example.lhq.weather.activity.network.event.base;

/**
 * 既然是消息驱动，抽象消息类型的Event类
 */
public class BaseEvent {

    private int action;

    public BaseEvent(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }
}
