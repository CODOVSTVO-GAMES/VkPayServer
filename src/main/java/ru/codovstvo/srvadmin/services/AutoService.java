package ru.codovstvo.srvadmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ru.codovstvo.srvadmin.entitys.NotificationsBuffer;
import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.repo.EventRepo;
import ru.codovstvo.srvadmin.repo.NotificationBufferRepo;
import ru.codovstvo.srvadmin.repo.SessionsRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;


@Transactional
@Service
public class AutoService {

    @Autowired
    SecureVkApiService secureVkApiService;    

    @Autowired
    UserEntityRepo userEntityRepo;

    @Autowired
    SessionsRepo sessionsRepo;

    @Autowired
    UserService userService;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    NotificationBufferRepo notificationBufferRepo;

    @Scheduled(initialDelay = 20000, fixedDelay = 240000) // каждую минуту 60000
    public void AutoSessionEnd() {
        System.out.println("Запущено удаление сессий");
        List<UserEntity> list = userEntityRepo.findAllByActive(true);
        Date date = new Date();
        long thisDate = date.getTime();

        for(UserEntity user : list){
            if (thisDate - user.getLastActivity() > 60000l) //больше 1 минуты назад 60000
            {
                userService.deactivateUser(user);
                if(user.getId() == 55190l) { continue; }
                notificationBufferRepo.save(new NotificationsBuffer(user));
            }
        }
    }

    @Scheduled(initialDelay = 100, fixedDelay = 6000000)
    public void AutoAdminLogsDelete(){
        int[] admins = new int[] { 77517618, 81313640, 36733860, 141398825, 186475046, 532515793, 19346574 };
        System.out.println("Запущено удаление логов админов");

        for(int i = 0; i < admins.length; i++){
            try{
                eventRepo.deleteAllByUserEntity(userEntityRepo.findByPlatformUserId(Integer.toString(admins[i])));
            }catch (Exception e) {
                System.out.println("Юзеров несколько. После перезахода ошибка исчезнет. Второго юзера система удалит");
            }
        }
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 3600000) // 5 часов 18000000
    public void SendNotifications() throws Exception {
        System.out.println("Запущена отправка уведомлений через 5 часов бездействия");

        String[] notifications = new String[] { "Энергия восстановлена", "Пора собирать фрукты", "Обновлены бонусные сундуки" };

        // запросить рандомную строку
        // проверить есть ли во внутреннем массиве
        // положить во внутренний массив

        Date date = new Date();
        long thisDate = date.getTime();

        Map<String, List<NotificationsBuffer>> queueMap = new HashMap<>();

        List<NotificationsBuffer> queueUsersUnits = notificationBufferRepo.findAll();
        System.out.println("Игроков в бд " + queueUsersUnits.size());

        for(NotificationsBuffer unit : queueUsersUnits) {
            System.out.println(unit.getUserEntity().getPlatformUserId());
            if (thisDate - unit.getUserEntity().getLastActivity() < 18000000l){
                queueUsersUnits.remove(unit);
            }
        }

        System.out.println("Игроков которых небыло больше 5 часов: " + queueUsersUnits.size());
        if(queueUsersUnits.size() == 0) { return; }

        for(String notification : notifications) {
            List<NotificationsBuffer> queueForSendNotification = new ArrayList<>();
            
            for(NotificationsBuffer unit : queueUsersUnits) {
                if (!notification.equals(unit.getUserEntity().getLastNotification())){
                    if(unit.getUserEntity().getPlatformUserId().equals("")) { continue; }
                    queueForSendNotification.add(unit);
                }
            }

            System.out.println("Игроков на отправку сообщения " + notification + " ---- " + queueForSendNotification.size());
            if(queueForSendNotification.isEmpty()) { continue; };

            for (NotificationsBuffer unit : queueForSendNotification) {
                queueUsersUnits.remove(unit);
            }

            queueMap.put(notification, queueForSendNotification);
        }

        for (Map.Entry<String, List<NotificationsBuffer>> entry : queueMap.entrySet()) {
            System.out.println("Долетело " + entry.getValue().size());
            String[] ids = new String[entry.getValue().size()];

            for (int i = 0; i < entry.getValue().size(); i++) {
                String id = entry.getValue().get(i).getUserEntity().getPlatformUserId();
                ids[i] = id;

                entry.getValue().get(i).getUserEntity().setLastNotification(entry.getKey());
                userEntityRepo.save(entry.getValue().get(i).getUserEntity());
                notificationBufferRepo.delete(entry.getValue().get(i));
            }

            secureVkApiService.sendNotification(ids, entry.getKey());

            System.out.println("Отправлено уведомление: " + entry.getKey());
        }    
    }
}
