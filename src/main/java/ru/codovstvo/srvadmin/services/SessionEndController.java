package ru.codovstvo.srvadmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

import ru.codovstvo.srvadmin.entitys.Sessions;
import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.repo.SessionsRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;

@Service
public class SessionEndController {

    @Autowired
    UserEntityRepo userEntityRepo;

    @Autowired
    SessionsRepo sessionsRepo;

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void AutoSessionEnd() {
        List<UserEntity> list = userEntityRepo.findAllByActive(true);
        Date date = new Date();
        long thisDate = date.getTime();

        for(UserEntity user : list){
            if (thisDate - user.getLastActivity() > 10000l)
            {
                Sessions session = sessionsRepo.findByUserAndNumberSession(user, user.getSessionCounter());
                session.endSession();
                sessionsRepo.save(session);

                user.setActive(false);
                user.setPlayTime(user.getPlayTime() + session.getSessionLeght());
                userEntityRepo.save(user);
            }
        }
    }
    
}
