package ru.codovstvo.srvadmin.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
        }

        return responce;
    }

}