package com.example.lib;

import java.io.Serializable;

public class Message implements Serializable {
    private String from; //发送者
    private String to;   //接收者
    private String info;//消息包

    public Message() {
    }

    public Message(String from, String to, String info) {
        this.from = from;
        this.to = to;
        this.info = info;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
