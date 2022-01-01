package com.example.WDSRTC.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Service
public class MainService {

    public static ModelAndView displayHomePage() {
        return new ModelAndView("home");
    }

    public static ModelAndView displayRandomRoom() {
        UUID randomRoomId = UUID.randomUUID();
        String redirectToRandomRoom = "redirect:/" + randomRoomId;

        return new ModelAndView(redirectToRandomRoom);
    }

    public static ModelAndView displaySelectedRoom(final String roomId) {
        ModelAndView roomModelAndView = new ModelAndView("room");
        roomModelAndView.addObject("roomId", roomId);

        return roomModelAndView;
    }
}
