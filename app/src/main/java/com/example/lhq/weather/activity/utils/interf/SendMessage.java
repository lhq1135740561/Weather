package com.example.lhq.weather.activity.utils.interf;

import com.example.lhq.weather.activity.activity.FirstActivity;

public class SendMessage {

    Propose propose;

    public interface Propose{

        void answer(String info);
    }

    public void response(FirstActivity propose){
        this.propose = (Propose) propose;
    }


    public SendMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*5);
                    if(propose != null){
                        propose.answer("你好");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
