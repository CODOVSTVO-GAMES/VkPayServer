package ru.codovstvo.srvadmin.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import ru.codovstvo.srvadmin.repo.EventRepo;

@RestController
@RequestMapping(value = "back/stat")
public class StatController {

    @Autowired
    private EventRepo eventRepo;

    // public int getAverageLoadime(@RequestParam(name = "version", required=false, defaultValue="") String version){
    //     return 0;
    // }
    
    @GetMapping("funnel")
    public Map getFunnelStartEvents(@RequestParam(name = "version", required=false, defaultValue="") String version){
        Map responce = new HashMap<>();
        if (version.equals("")){
            responce.put("first_load", eventRepo.countByEventName("first_load"));
        }

        return responce;
    }

}