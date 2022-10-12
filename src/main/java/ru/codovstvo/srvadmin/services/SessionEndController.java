package ru.codovstvo.srvadmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.repo.EventRepo;
import ru.codovstvo.srvadmin.repo.SessionsRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;

@Transactional
@Service
public class SessionEndController {

    @Autowired
    UserEntityRepo userEntityRepo;

    @Autowired
    SessionsRepo sessionsRepo;

    @Autowired
    UserService userService;

    @Autowired
    EventRepo eventRepo;

    @Scheduled(initialDelay = 600000, fixedDelay = 6000000) // каждую минуту 60000
    public void AutoSessionEnd() {
        System.out.println("Запущено удаление сессий");
        List<UserEntity> list = userEntityRepo.findAllByActive(true);
        Date date = new Date();
        long thisDate = date.getTime();

        for(UserEntity user : list){
            if (thisDate - user.getLastActivity() > 60000l) //больше 1 минуты назад 60000
            {
                userService.deactivateUser(user);
            }
        }
    }

    @Scheduled(initialDelay = 600000, fixedDelay = 6000000)
    public void AutoAdminLogsDelete(){
        int[] admins = new int[] { 77517618, 81313640, 36733860, 141398825 };

        for(int i = 0; i < admins.length; i++){
            try{
                eventRepo.deleteAllByUserEntity(userEntityRepo.findByPlatformUserId(Integer.toString(admins[i])));
            }catch (Exception e) {
                System.out.println("Юзеров несколько. После перезахода ошибка исчезнет. Второго юзера система удалит сама");
            }
            

        }
    }
    
}
