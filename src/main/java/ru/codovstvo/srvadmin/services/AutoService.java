package ru.codovstvo.srvadmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vk.api.sdk.actions.Donut;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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

    final Long[] ADMINS = new Long[] { 77517618l, 81313640l, 36733860l, 141398825l, 186475046l, 532515793l, 19346574l, 580946266481l, 586485652436l };

    final String[] NOTIFICATIONS = new String[] { "Энергия восстановлена. Впереди много дел!", "Соберем фрукты и продолжим исследовать лес!", "Обновлены бонусные сундуки, пора их собрать!", "Продолжи исследовать лес и узнавать его тайны!" };

    final Date date = new Date();

    @Autowired
    SecureVkApiService secureVkApiService;    

    @Autowired
    OKService okService;

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

    // написать удаление тех кто заходил больше чем 10 часов назад(защита от крашей)

    @Scheduled(initialDelay = 20000, fixedDelay = 240000) // каждую минуту 60000
    public void AutoSessionEnd() {
        System.out.println("Запущено удаление сессий");
        List<UserEntity> list = userEntityRepo.findAllByActive(true);
        long thisDate = date.getTime();

        for(UserEntity user : list){
            if (thisDate - user.getLastActivity() > 60000l) //больше 1 минуты назад 60000
            {
                userService.deactivateUser(user);
                if(user.getPlatform() == null || user.getPlatform().equals("yandex")) { continue; }
                notificationBufferRepo.save(new NotificationsBuffer(user));
            }
        }
    }

    @Scheduled(initialDelay = 100, fixedDelay = 6000000)// не тестировал сборку 03.03.2023
    public void AutoAdminLogsDelete(){
        System.out.println("Запущено удаление логов админов");

        for(int i = 0; i < ADMINS.length; i++){
            try{
                eventRepo.deleteAllByUserEntity(userEntityRepo.findByPlatformUserId(Long.toString(ADMINS[i])));
            }catch (Exception e) {
                System.out.println("Юзеров несколько. После перезахода ошибка исчезнет. Второго юзера система удалит");
            }
        }
    }

    @Scheduled(initialDelay = 500, fixedDelay = 6000000)// не тестировал сборку 03.03.2023
    public void Test(){
        System.out.println("---------------------------------------------------------ОМГ");
        okService.sendNotification("Адай араба", 580946266481l);
        System.out.println("---------------------------------------------------------ОМГ2");
    }

    @Scheduled(initialDelay = 10000l, fixedDelay = 600000) // 5 часов 18000000
    public void SendNotifications() throws Exception {
        System.out.println("Запущена отправка уведомлений через 5 часов бездействия");

        List<NotificationsBuffer> queueUsersUnits = notificationBufferRepo.findAll();

        System.out.println("Игроков в бд " + queueUsersUnits.size());

        queueUsersUnits = deleteWithLessDelay(queueUsersUnits);

        System.out.println("Игроков которых небыло больше 5 часов: " + queueUsersUnits.size());
        if(queueUsersUnits.size() == 0) { System.out.println("Операция остановлена"); return; }

        List<NotificationsBuffer> queueUsersUnitsVK = findNBByPlatform(queueUsersUnits, "vk");
        List<NotificationsBuffer> queueUsersUnitsOK = findNBByPlatform(queueUsersUnits, "ok");

        System.out.println("Игроков ВК " + queueUsersUnitsVK.size());
        System.out.println("Игроков ОК " + queueUsersUnitsOK.size());


        Map<String, List<NotificationsBuffer>> queueMapVK = prepareCollectionForShipping(queueUsersUnitsVK);
        Map<String, List<NotificationsBuffer>> queueMapOK = prepareCollectionForShipping(queueUsersUnitsOK);

        //написать метод отправки в одноклы


        for (Map.Entry<String, List<NotificationsBuffer>> entry : queueMapVK.entrySet()) {
            System.out.println("Долетело " + entry.getValue().size());
            
            String[] ids = parseId(entry.getValue(), entry.getKey());

            secureVkApiService.sendNotification(ids, entry.getKey());

            System.out.println("Отправлено уведомление: " + entry.getKey());
        }    


    }
    
    private Map<String, List<NotificationsBuffer>> prepareCollectionForShipping(List<NotificationsBuffer> list){
        Map<String, List<NotificationsBuffer>> queueMap = new HashMap<>();
        int maxNumberUsers = 0; //ограничивает пачки юзеров на отправку за раз
        
        for (int i = 0; i < NOTIFICATIONS.length; i++) {
            String notification = getRandomNotification(NOTIFICATIONS); //f костыль, решает проблему повторяемости двух уведомлений подряд

            List<NotificationsBuffer> queueForSendNotification = splitUsersByLastNotification(list, notification);
            System.out.println("Игроков на отправку сообщения " + notification + " ---- " + queueForSendNotification.size());
            if(queueForSendNotification.isEmpty()) { continue; };
            list = deleteItersections(list, queueForSendNotification);
            queueMap.put(notification, queueForSendNotification);
            maxNumberUsers += 1;
            if (maxNumberUsers >= 99) { break; }
        }
        return queueMap;
    }

    private String[] parseId(List<NotificationsBuffer> list, String notification){
        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            String id = list.get(i).getUserEntity().getPlatformUserId();
            ids[i] = id;
            list.get(i).getUserEntity().setLastNotification(notification);
            userEntityRepo.save(list.get(i).getUserEntity());
            notificationBufferRepo.delete(list.get(i));
        }
        return ids;
    }

    private String getRandomNotification(String[] notifications){
        int rand = (int) (Math.random() * notifications.length);
        return notifications[rand];
    }

    private List<NotificationsBuffer> findNBByPlatform(List<NotificationsBuffer> allQueueNotificationUsers, String platform)
    {
        List<NotificationsBuffer> NBByPlatform = new ArrayList<>();
        for (NotificationsBuffer not : allQueueNotificationUsers){
            UserEntity user = not.getUserEntity();
            if (user.getPlatform() == platform){
                NBByPlatform.add(not);
            }
        }
        return NBByPlatform;
    }

    private List<NotificationsBuffer> deleteWithLessDelay(List<NotificationsBuffer> notifications){
        long thisDate = date.getTime();
        for (int i = 0; i < notifications.size(); i++) {
            if (thisDate - notifications.get(i).getUserEntity().getLastActivity() < 18000000l){
                notifications.remove(notifications.get(i));
            }
        }
        return notifications;
    }
    
    private List<NotificationsBuffer> splitUsersByLastNotification(List<NotificationsBuffer> queueUsersUnits, String notification){
        List<NotificationsBuffer> queueForSendNotification = new ArrayList<>();

        for(NotificationsBuffer unit : queueUsersUnits) {
            if (!notification.equals(unit.getUserEntity().getLastNotification())){
                if(unit.getUserEntity().getPlatformUserId().equals("")) { continue; } //платформа непонятна. Заполнить\игнорировать\удалить
                queueForSendNotification.add(unit);
            }
        }
        return queueForSendNotification;
    }

    private List<NotificationsBuffer> deleteItersections(List<NotificationsBuffer> bigList, List<NotificationsBuffer> miniList){
        for(NotificationsBuffer not : miniList){
            bigList.remove(not);
        }
        return bigList;
    }
    
}
