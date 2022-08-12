package ru.codovstvo.srvadmin.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import ru.codovstvo.srvadmin.entitys.Event;
import ru.codovstvo.srvadmin.repo.EventRepo;
import ru.codovstvo.srvadmin.services.SecureVkApiService;

@RestController
@RequestMapping(value = "back/stat")
public class StatController {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private SecureVkApiService secureVkApiService;

    @GetMapping("averageLoadTime")
    public long getAverageLoadime(@RequestParam(name = "place", required=false, defaultValue = "") String place,
                                @RequestParam(name = "version", required=false, defaultValue = "") String version){
        long counter = 0;
        long allTime = 0;
        Set<Event> allEvents = new HashSet<>();
        if(version.equals("")){
            if(place.equals("start")){
                allEvents = eventRepo.findAllByEventName("started_game");
            }else if(place.equals("first")){
                allEvents = eventRepo.findAllByEventName("first_load");

            }else{
                allEvents = eventRepo.findAllByEventName("started_game");
                allEvents.addAll(eventRepo.findAllByEventName("first_load"));
            }
        }else{
            if(place.equals("start")){
                allEvents = eventRepo.findAllByEventNameAndVersion("started_game", version);
            }else if(place.equals("first")){
                allEvents = eventRepo.findAllByEventNameAndVersion("first_load", version);
            }else{
                allEvents = eventRepo.findAllByEventNameAndVersion("started_game", version);
                allEvents.addAll(eventRepo.findAllByEventNameAndVersion("first_load", version));
            }
        }
        for(Event event : allEvents){
            allTime += Long.parseLong(event.getLoadTime());
            counter++;
        }
        return allTime / counter;
    }
    
    @GetMapping("funnel")
    public Map getFunnelStartEvents(@RequestParam(name = "version", required=false, defaultValue="") String version){
        Set eventsName =  new LinkedHashSet<String>(Arrays.asList("first_load","started_game",
                                                                "dialogue_marya_close_0","merge_marya",
                                                                "dialogue_marya_close_1","merge_stebel",
                                                                "dialogue_marya_close_2","merge_bloom_tree",
                                                                "dialogue_marya_close_3","quest_done_0",
                                                                "level_up_2","quest_open_1",
                                                                "2_apple_harvested","first_click_in_marya",
                                                                "click_exchange","click_speedup",
                                                                "quest_done_1","quest_open_2",
                                                                "open_map_2","first_click_in_root",
                                                                "dialogue_cat_close_1","dialogue_marya_close_6",
                                                                "quest_done_2","level_up_3",
                                                                "dialogue_cat_close_2","second_click_in_root",
                                                                "second_click_in_root","dialogue_cat_close_4",
                                                                "first_click_in_chest","first_click_in_chest_open",
                                                                "quest_open_6","dialogue_cat_close_5")); 

        Map responce = new LinkedHashMap<>();
        
        if (version.equals("")){
            for(Object object : eventsName){
                String event = (String) object;
                responce.put(event, eventRepo.countByEventName(event));
            }
        } else if (!version.equals("")){
            for(Object object : eventsName){
                String event = (String) object;
                responce.put(event, eventRepo.countByEventNameAndVersion(event, version));
            }
        }

        return responce;
    }

    @GetMapping("test")
    public void test() throws Exception{
        secureVkApiService.test();
    }

}