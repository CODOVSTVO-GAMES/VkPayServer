package ru.codovstvo.srvadmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Date;

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

    @Scheduled(initialDelay = 1000, fixedDelay = 3600000)
    public void SendNotifications() throws Exception {
        System.out.println("Запущена отправка сообщений");

        String[] notifications = new String[] { "Энергия восстановлена", "Пора собирать фрукты" };

        Date date = new Date();
        long thisDate = date.getTime();

        List<NotificationsBuffer> activeUnits = new ArrayList<NotificationsBuffer>();

        List<NotificationsBuffer> units =  notificationBufferRepo.findAll();
        for(NotificationsBuffer unit : units){
            if(thisDate - unit.getUserEntity().getLastActivity() > 10800000l){ //10800000l - 3 часа
                for(String not : notifications){
                    if (not.equals(unit.getUserEntity().getLastNotification())){
                        continue;
                    }
                    
                    try{
                        secureVkApiService.sendNotification(unit.getUserEntity().getPlatformUserId(), not);
                    }catch (Exception e){System.out.println("Сообщение крашнулось");}
                    try {
                        Thread.sleep(1 * 500);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }

                    unit.getUserEntity().setLastNotification(not);
                    userEntityRepo.save(unit.getUserEntity());
                    activeUnits.add(unit);
                    // notificationBufferRepo.delete(unit);
                    System.out.println("Конец");
                }
            }
        }

        notificationBufferRepo.deleteAll(activeUnits);
        System.out.println("activeunits");
    }
    
}
