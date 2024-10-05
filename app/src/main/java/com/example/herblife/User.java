package com.example.herblife;

public class User {

    String name, lastMessage, lastMsgTime, phoneNo, common, desc;
    int imageId;

    public User(String name, String phoneNo, String common, int imageId) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.imageId = imageId;
        this.common = common;

    }
}