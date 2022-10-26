package ru.codovstvo.srvadmin.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ru.codovstvo.srvadmin.entitys.EventEntity;
import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.entitys.Version;
import ru.codovstvo.srvadmin.repo.SessionsRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;
import ru.codovstvo.srvadmin.services.CryptoService;
import ru.codovstvo.srvadmin.services.EventService;
import ru.codovstvo.srvadmin.services.SecureVkApiService;
import ru.codovstvo.srvadmin.services.UserService;
import ru.codovstvo.srvadmin.services.VersionService;

@Transactional
@RestController
@RequestMapping(value = "back/events")
public class EventsController {

    String[] eventsAds = {"ads_merge_item", "shopCrystal_open_ads_1-3", "shopEnergy_open_ads_5", 
                            "shopFruit_x2", "shopResources_x2", "shopWorker_open_worker"};

    String[] rewardEventAds = {"shopEnergy_reward_ads_5", "shopEnergy_reward_ads_30", "ads_merge_item", 
                                "shopCrystal_reward_1", "shopFruit_x2_reward", "shopResources_x2_reward", 
                                "shopWorker_reward_worker",
                            
                                "shopCrystal_1-3_r_reward", "shopCrystal_1-3_ads_reward",
                                "shopEnergy_5_r_reward", "shopEnergy_5_ads_reward",
                                "shop_worker_r_reward", "shop_worker_ads_reward",
                                "television_r_reward", "television_ads_reward",
                                "merge_item_r_reward", "merge_item_ads_reward",
                                "shopEnergy_30_r_reward", "shopEnergy_30_ads_reward",
                                "shopCrystal_reward_1-3", ""};

    @Autowired
    CryptoService cryptoService;

    @Autowired
    SecureVkApiService secureVkApiService;

    @Autowired
    UserEntityRepo userEntityRepo;

    @Autowired
    SessionsRepo sessionsRepo;

    @Autowired
    UserService userService;

    @Autowired
    VersionService versionService;

    @Autowired
    EventService eventService;

    @PostMapping
    public ResponseEntity newEvent(@RequestParam String hash,
                                    @RequestParam String type,
                                    @RequestParam String userId,
                                    @RequestParam String version,
                                    @RequestParam String platform,
                                    @RequestParam String deviceType,
                                    @RequestParam String event,
                                    @RequestParam(name = "lang", required=false, defaultValue="") String lang,
                                    @RequestParam(name = "referrer", required=false, defaultValue="") String referrer,
                                    @RequestParam(name = "loadtime", required=false, defaultValue="") String loadTime,
                                    @RequestParam(name = "session", required=false, defaultValue="") int session,
                                    @RequestParam Map<String, String> allParams
                                ) throws Exception {
        String parameters = new String();

        if(type.equals("start")){
            parameters = "&userId=" + userId + "&version=" + version + "&platform=vk" + "&deviceType=" + deviceType + "&event=" + event + "&referrer=" + referrer + "&lang=" + lang + "&loadtime=" + loadTime + "&type=start" + "&session=" + session;
        }
        else if(type.equals("ordinary")){
            parameters = "&userId=" + userId + "&version=" + version + "&platform=vk" + "&deviceType=" + deviceType + "&event=" + event + "&type=ordinary" + "&session=" + session;
        }

        if (!cryptoService.encodeHmac256(parameters).equals(hash)){ // если хеш неверный
            System.out.println("неверный хеш евент контроллер");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Version vestionInstanse = versionService.createOrFindVersion(version, platform);

        UserEntity user = userService.createOrFindUser(userId);

        if (type.equals("start")){
            user.updateDeviseType(deviceType);
            user.setFirstPlatform(platform);

            // userService.forceCloseSessionIfUserActive(user);
            userService.activateUser(user);

            if(user.getSessionCounter() == 1){
                if(user.getReferer().equals("no_referrer")){
                    user.setReferer(referrer);
                    userEntityRepo.save(user);
                }
            }
        }

        eventService.newEvent(new EventEntity(user, vestionInstanse, platform, deviceType, event, lang, referrer, loadTime, 
                                                userService.getLastOrCreateSession(user), 
                                                userService.getTimeFromStart(user)));

        for (int i = 0; i < eventsAds.length; i++) {
            if(rewardEventAds[i].equals(event)){
                user.addAdsCount();
                userEntityRepo.save(user);
                break;
            }
        }

        //переработать
        try{
            if (event.contains("level_up")) {
                String level = event.replace("level_up_", "");
                secureVkApiService.sendLevelUpEvent(Integer.parseInt(level), userId);

            }
            else if (event.contains("quest_done_4")) {
                secureVkApiService.sendProgressMission(3, userId); // познакомиться с Иваном Царевичем
            }

        }catch(Exception e){System.out.println("Ошибка отправки сообщения вк");}
        //переработать

        return new ResponseEntity(HttpStatus.OK);
    }

}