package ru.codovstvo.srvadmin.controllers;

import java.util.Map;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ru.codovstvo.srvadmin.entitys.Event;
import ru.codovstvo.srvadmin.entitys.EventErrors;
import ru.codovstvo.srvadmin.entitys.NotificationQueue;
import ru.codovstvo.srvadmin.entitys.NotificationType;
import ru.codovstvo.srvadmin.entitys.Sessions;
import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.repo.EventErrorRepo;
import ru.codovstvo.srvadmin.repo.EventRepo;
import ru.codovstvo.srvadmin.repo.NotificationQueueRepo;
import ru.codovstvo.srvadmin.repo.SessionsRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;
import ru.codovstvo.srvadmin.services.EventsService;
import ru.codovstvo.srvadmin.services.SecureVkApiService;

@Transactional
@RestController
@RequestMapping(value = "back/events")
public class EventsController {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private EventErrorRepo eventErrorRepo;

    @Autowired
    private NotificationQueueRepo notificationQueueRepo;

    @Autowired
    EventsService eventsService;

    @Autowired
    SecureVkApiService secureVkApiService;

    @Autowired
    UserEntityRepo userEntityRepo;

    @Autowired
    SessionsRepo sessionsRepo;

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
            
            UserEntity user = userEntityRepo.findByPlatformUserId(Integer.toString(userId));
            
            if (user == null){
                System.out.println("Создан новый пользователь в евент контроллере");
                user = userEntityRepo.save(new UserEntity(userId));
            }

            if (user.getActive()) //если сессия прошлая сессия не завершена, он ее завершит и начнет новую
            {
                Sessions sessionn = sessionsRepo.findByUserAndNumberSession(user, user.getSessionCounter());
                sessionn.endSession();
                sessionsRepo.save(sessionn);

                user.setActive(false);
                user.setPlayTime(user.getPlayTime() + sessionn.getSessionLeght());
                userEntityRepo.save(user);
            }

            user.setSessionCounter(session);
            user.setActive(true);
            sessionsRepo.save(new Sessions(user, session));
        }
        else if(type.equals("ordinary")){
            parameters = "&userId=" + userId + "&version=" + version + "&platform=vk" + "&deviceType=" + deviceType + "&event=" + event + "&type=ordinary" + "&session=" + session;
        }

        if(eventsService.encodeHmac256(parameters).equals(hash)){
            Event evvvent = new Event(userId, version, platform, deviceType, event, lang, referrer, loadTime);
            eventRepo.save(evvvent);
            
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
            else if (event.contains("apple_collect")) {
                long userIdLong = userId;
                notificationQueueRepo.save(new NotificationQueue(userIdLong, NotificationType.COLLERCTAPPLE));
            }
            else if (event.contains("tangerine_collect")) {
                long userIdLong = userId;
                notificationQueueRepo.save(new NotificationQueue(userIdLong, NotificationType.COLLERCTTANGETINE));
            }
            else if (event.contains("appleTree_death")) {
                long userIdLong = userId;
                notificationQueueRepo.deleteByUserIdAndNotificationType(userIdLong, NotificationType.COLLERCTAPPLE);
            }
            else if (event.contains("tangerineTree_death")) {
                long userIdLong = userId;
                notificationQueueRepo.deleteByUserIdAndNotificationType(userIdLong, NotificationType.COLLERCTTANGETINE);
            }

            return new ResponseEntity(HttpStatus.OK);
        } else {
            eventErrorRepo.save(new EventErrors(allParams.toString()));
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

}