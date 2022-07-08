package ru.codovstvo.srvadmin.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;

import ru.codovstvo.srvadmin.entitys.Event;
import ru.codovstvo.srvadmin.repo.EventRepo;

@RestController
@RequestMapping(value = "back/events")
public class EventsController {

    @Autowired
    private EventRepo eventRepo;

    @PostMapping
    public void newEvent(@RequestParam int key,
                        @RequestParam int userId,
                        @RequestParam String version,
                        @RequestParam String platform,
                        @RequestParam String deviceType,
                        @RequestParam String event,
                        @RequestParam(name = "lang", required=false, defaultValue="") String lang,
                        @RequestParam(name = "referrer", required=false, defaultValue="") String referrer,
                        @RequestHeader("Referer") Map ref
                        ) {
        System.out.println(ref);
        System.out.println(ref.get("sign_keys"));
        if (key/7-8180902 == userId) {
            Event evvent = new Event(userId, version, platform, deviceType, event, lang, referrer);
            eventRepo.save(evvent);
        }
        else
        {
            System.out.println("Подпись неверна");
            System.out.println(key);
            System.out.println(userId);
        }
    }

}