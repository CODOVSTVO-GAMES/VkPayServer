package ru.codovstvo.srvadmin.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import ru.codovstvo.srvadmin.entitys.Event;
import ru.codovstvo.srvadmin.entitys.EventErrors;
import ru.codovstvo.srvadmin.repo.EventErrorRepo;
import ru.codovstvo.srvadmin.repo.EventRepo;
import ru.codovstvo.srvadmin.services.EventsService;

@RestController
@RequestMapping(value = "back/events")
public class EventsController {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private EventErrorRepo eventErrorRepo;

    @Autowired
    EventsService eventsService;

    @PostMapping
    public ResponseEntity newEvent(@RequestParam String hash,
                        @RequestParam String type,
                        @RequestParam int userId,
                        @RequestParam String version,
                        @RequestParam String platform,
                        @RequestParam String deviceType,
                        @RequestParam String event,
                        @RequestParam(name = "lang", required=false, defaultValue="") String lang,
                        @RequestParam(name = "referrer", required=false, defaultValue="") String referrer,
                        @RequestParam(name = "loadtime", required=false, defaultValue="") String loadTime,
                        @RequestParam Map<String, String> allParams
                        ) throws Exception {
        String parameters = new String();

        if(type.equals("start")){
            parameters = "&userId=" + userId + "&version=" + version + "&platform=vk" + "&deviceType=" + deviceType + "&event=" + event + "&referrer=" + referrer + "&lang=" + lang + "&loadtime=" + loadTime + "&type=start";
        }
        else if(type.equals("ordinary")){
            parameters = "&userId=" + userId + "&version=" + version + "&platform=vk" + "&deviceType=" + deviceType + "&event=" + event + "&type=ordinary";
        }
        else if(type.equals("firstload")){
            parameters = "&userId=" + userId + "&version=" + version + "&platform=vk" + "&deviceType=" + deviceType + "&event=" + event + "&referrer=" + referrer + "&lang=" + lang + "&loadtime=" + loadTime + "&type=firstload";
        }
        
        if(eventsService.encodeHmac256(parameters).equals(hash)){
            Event evvvent = new Event(userId, version, platform, deviceType, event, lang, referrer, loadTime);
            eventRepo.save(evvvent);
            return new ResponseEntity(HttpStatus.OK);
        }else{
            eventErrorRepo.save(new EventErrors(allParams.toString()));
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

}