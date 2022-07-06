package ru.codovstvo.srvadmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import ru.codovstvo.srvadmin.entitys.Event;

@RestController
@RequestMapping(value = "back/events")
public class EventsController {

    @PostMapping
    public void newEvent(@RequestParam int key,
                        @RequestParam int userId,
                        @RequestParam String version,
                        @RequestParam String platform,
                        @RequestParam String deviceType,
                        @RequestParam String event
                        ) {
        if (key/3-8180902 == userId) {
            Event evvent = new Event(userId, version, platform, deviceType, event);
        }
        else
        {
            System.out.println("Чота тут не так");
            System.out.println(key);
            System.out.println(userId);
        }
    }

}