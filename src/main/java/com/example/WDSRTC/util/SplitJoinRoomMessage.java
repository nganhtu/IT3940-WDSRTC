package com.example.WDSRTC.util;


import org.springframework.stereotype.Service;

@Service
public class SplitJoinRoomMessage {

    public static String[] splitJoinRoomMessage(String message) {
        return message.split("\\s+");
    }
}
