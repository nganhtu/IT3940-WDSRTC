package com.example.WDSRTC.controller;

import com.example.WDSRTC.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
public class MainController {

    @GetMapping({"", "/"})
    public ModelAndView displayHomePage() {
        return MainService.displayHomePage();
    }

    @GetMapping("/random")
    public ModelAndView displayRandomRoom() {
        return MainService.displayRandomRoom();
    }

    @GetMapping("/{roomId}")
    public ModelAndView displaySelectedRoom(
            @PathVariable("roomId") final String roomId
    ) {
        return MainService.displaySelectedRoom(roomId);
    }
}
