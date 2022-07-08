package ru.codovstvo.srvadmin.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
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
                        @RequestHeader Map header
                        ) throws Exception{
        Map<String, String> parameters =  new HashMap<>();
        String[] uriRef = header.get("referer").toString().replace("https://codovstvo.ru/games/Merge3/index.html?", "").split("&");
        
        for(String para : uriRef){
            try{
                String[] keyValue = para.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }catch (Exception e){
                parameters.put(para.replace("=", ""), "");
            }
        }

        String signDoHash = "";
        String[] signKeys = parameters.get("sign_keys").split(",");

        for (String parameter : signKeys){
            signDoHash = signDoHash + parameter + "=" + parameters.get(parameter) + "&";
        }

        signDoHash = signDoHash.substring(0, signDoHash.lastIndexOf("&"));        

        
        System.out.println(parameters.get("sign"));
        System.out.println(parameters.get("sign_keys"));
        System.out.println(signDoHash);
        encode("7xg1eGa5YiRS3MdMwPhl", signDoHash);

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

    public static void encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes());
        DatatypeConverter.printBase64Binary(hash);

        System.out.println(DatatypeConverter.printBase64Binary(hash));
        // Base64.encodeBase64String(sha256_HMAC.doFinal(data.getBytes()))
    }

}