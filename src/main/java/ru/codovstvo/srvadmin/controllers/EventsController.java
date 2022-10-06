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
import ru.codovstvo.srvadmin.entitys.Sessions1;
import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.entitys.Version;
import ru.codovstvo.srvadmin.repo.EventRepo;
import ru.codovstvo.srvadmin.repo.SessionsRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;
import ru.codovstvo.srvadmin.services.CryptoService;
import ru.codovstvo.srvadmin.services.SecureVkApiService;
import ru.codovstvo.srvadmin.services.UserService;
import ru.codovstvo.srvadmin.services.VersionService;

@Transactional
@RestController
@RequestMapping(value = "back/events")
public class EventsController {

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
    EventRepo eventRepo;

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

        UserEntity user = userService.createOrFindUser(userId);

        Version vestionInstanse = versionService.createOrFindVersion(version, platform);

        eventRepo.save(new EventEntity(user, vestionInstanse, platform, deviceType, event, lang, referrer, loadTime));

        if (type.equals("start")){
            if(user.getActive()) { //если сессия прошлая сессия не завершена, он ее завершит и начнет новую
                Sessions1 s = sessionsRepo.findByUserAndNumberSession(user, session);
                s.endSession();
                sessionsRepo.save(s);

                user.setActive(false);
                user.setPlayTime(user.getPlayTime() + s.getSessionLeght());
            }

            user.setSessionCounter(user.getSessionCounter() + 1);
            user.setActive(true);
            sessionsRepo.save(new Sessions1(user, session));

        }

        //переработать
        try{
            if (event.contains("level_up")) {
                String level = event.replace("level_up_", "");
                try {
                    secureVkApiService.sendLevelUpEvent(Integer.parseInt(level), userId);
                } catch (Exception e) {
                    System.out.println("Уровень равен или меньше текущего");
                }
            }
            else if (event.contains("quest_done_4")) {
                try{
                    secureVkApiService.sendProgressMission(3, userId); // познакомиться с Иваном Царевичем
                } catch (Exception e) {
                    System.out.println("Знакомство с иваном уже было");
                }
            }

        }catch(Exception e){System.out.println("Ошибка отправки сообщения вк");}
        //переработать

        return new ResponseEntity(HttpStatus.OK);
    }

}