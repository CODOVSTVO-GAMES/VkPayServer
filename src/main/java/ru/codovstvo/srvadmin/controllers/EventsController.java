package ru.codovstvo.srvadmin.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

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

    // @PostMapping
    // public void newEvent(@RequestParam int key,
    //                     @RequestParam int userId,
    //                     @RequestParam String version,
    //                     @RequestParam String platform,
    //                     @RequestParam String deviceType,
    //                     @RequestParam String event,
    //                     @RequestParam(name = "lang", required=false, defaultValue="") String lang,
    //                     @RequestParam(name = "referrer", required=false, defaultValue="") String referrer
    //                     ) {


    //     if (key/7-8180902 == userId) {
    //         Event evvent = new Event(userId, version, platform, deviceType, event, lang, referrer);
    //         eventRepo.save(evvent);
    //     }
    //     else
    //     {
    //         System.out.println("Подпись неверна");
    //         System.out.println(key);
    //         System.out.println(userId);
    //     }
    // }
    @PostMapping
    public void newEvent(@RequestParam String hash) throws Exception{
        System.out.println(hash);
        encode(hash);
    }

    public static void encode(String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        String key = "programmistika";
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] hash = sha256_HMAC.doFinal(data.getBytes());
        DatatypeConverter.printBase64Binary(hash);
        System.out.println(DatatypeConverter.printBase64Binary(hash));
    }

}