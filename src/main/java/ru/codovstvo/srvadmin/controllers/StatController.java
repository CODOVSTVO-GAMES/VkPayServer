package ru.codovstvo.srvadmin.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import ru.codovstvo.srvadmin.entitys.Event;
import ru.codovstvo.srvadmin.repo.EventRepo;

@RestController
@RequestMapping(value = "back/stat")
public class StatController {

    @Autowired
    private EventRepo eventRepo;

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
    
    @GetMapping("funnel") //первая версия воронки обучения, не актуальна
    public Map getFunnelStartEvents(@RequestParam(name = "version", required=false, defaultValue="") String version){
        Set eventsName =  new LinkedHashSet<String>(Arrays.asList("first_load","started_game",
                                                                "dialogue_marya_close_0","merge_marya",
                                                                "dialogue_marya_close_1","merge_stebel",
                                                                "dialogue_marya_close_2","merge_bloom_tree",
                                                                "dialogue_marya_close_3","quest_done_0",
                                                                "level_up_2","quest_open_1",
                                                                "2_apple_harvested","dialogue_marya_close_4",
                                                                "dialogue_marya_close_5", //"first_click_in_marya",
                                                                // "click_exchange","click_speedup",
                                                                "quest_done_1", "dialogue_marya_close_6",
                                                                "open_map_2", "quest_open_2", "quest_open_17", 
                                                                "dialogue_mc_close_0", "dialogue_cat_close_0",
                                                                "first_click_in_root", "second_click_in_root",
                                                                "dialogue_cat_close_1",
                                                                "first_click_in_chest","first_click_in_chest_open",
                                                                "quest_done_2", "level_up_3",
                                                                "dialogue_cat_close_1", 
                                                                "open_map_3", "dialogue_cat_close_8", 
                                                                "quest_done_17", "quest_done_2", 
                                                                "quest_open_5", "level_up_4",
                                                                "open_map_4", "open_map_5", 
                                                                "dialogue_cat_close_2", "dialogue_mc_close_7",
                                                                "dialogue_cat_close_5", "dialogue_cat_close_6", 
                                                                "dialogue_cat_close_3", "dialogue_mc_close_1", 
                                                                "quest_done_5", "quest_open_7",
                                                                "quest_open_8", 
                                                                // "second_click_in_root",
                                                                "quest_open_3", "quest_open_4", "dialogue_cat_close_4", 
                                                                "quest_open_6", "quest_done_3", 
                                                                "quest_open_9", "dialogue_mc_close_6", 
                                                                "quest_done_7", "dialogue_cat_close_5", 
                                                                "quest_open_10", "quest_done_9", 
                                                                "quest_open_14", "quest_open_16", 
                                                                "quest_open_15", "level_up_5", 
                                                                "quest_done_4",
                                                                "level_up_6", "level_up_7",
                                                                "open_map_6", "open_map_7"
                                                                ));

        Map responce = new HashMap<String, Long>();
        
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

        return sortByValue(responce);
    }
    
    @GetMapping("startfunnel")
    public Map getStartFunnel(@RequestParam(name = "version", required=false, defaultValue="") String version){
        Set eventsName =  new LinkedHashSet<String>(Arrays.asList("first_load", "spawn-marya", "merge-appleTree-1", "merge-appleTree-2", "merge-appleTree-3",
                                                                        "first-exchange", "first-key-collect", "first-cutting-of-the-root", "first-merge-logs", "second-cutting-of-the-root", 
                                                                        "first-delete-root", "first-open-chestS", "second-open-chestS", "first-drop-apples"
                                                                )); 

        Map responce = new HashMap<String, Long>();
        
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
        return sortByValue(responce);
    }

    @GetMapping("levelfunnel")
    public Map getLevelFunnel(@RequestParam(name = "version", required=false, defaultValue="") String version){
        Set eventsName =  new LinkedHashSet<String>(Arrays.asList("first_load", "level_up_2", "level_up_3","level_up_4","level_up_5",
                                                                    "level_up_6", "level_up_7", "level_up_8","level_up_9","level_up_10"
                                                                )); 

        Map responce = new HashMap<String, Long>();
        
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
        return sortByValue(responce);
    }

    @GetMapping("questfunnel")
    public Map getQuestsDoneFunnel(@RequestParam(name = "version", required=false, defaultValue="") String version){
        Set eventsName =  new LinkedHashSet<String>(Arrays.asList("first_load", "quest_done_0", "quest_done_1", "quest_done_2", "quest_done_3", "quest_done_4", 
                                                                    "quest_done_5", "quest_done_6", "quest_done_7", "quest_done_8", "quest_done_9",
                                                                    "quest_done_10", "quest_done_11", "quest_done_12", "quest_done_13", "quest_done_14",
                                                                    "quest_done_15", "quest_done_16", "quest_done_17", "quest_done_18", "quest_done_19"   
                                                                )); 

        Map responce = new HashMap<String, Long>();
        
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
        return sortByValue(responce);
    }

    @GetMapping("terrfunnel")
    public Map getTerritoryasOpenFunnel(@RequestParam(name = "version", required=false, defaultValue="") String version){
        Set eventsName =  new LinkedHashSet<String>(Arrays.asList("first_load", "open_map_2", "open_map_3", "open_map_4", "open_map_5",
                                                                        "open_map_6", "open_map_7", "open_map_8", "open_map_9", "open_map_10"
                                                                )); 

        Map responce = new HashMap<String, Long>();
        
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
        return sortByValue(responce);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        Map reversedResult = new LinkedHashMap<String, Long>();
        List<K> keys = new ArrayList<K>(result.keySet());
        List<V> values = new ArrayList<V>(result.values());
        
        for (int i = result.size() - 1; i >= 0; i--)
        {
            reversedResult.put(keys.get(i), values.get(i));
        }

        return reversedResult;
    }

}