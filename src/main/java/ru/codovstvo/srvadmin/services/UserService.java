package ru.codovstvo.srvadmin.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.codovstvo.srvadmin.entitys.Sessions1;
import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.repo.EventRepo;
import ru.codovstvo.srvadmin.repo.SessionsRepo;
import ru.codovstvo.srvadmin.repo.UserDataRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;
import ru.codovstvo.srvadmin.repo.VersionRepo;

@Transactional
@Service
public class UserService {

    @Autowired
    UserEntityRepo userEntityRepo;

    @Autowired
    UserDataRepo userDataRepo;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    SessionsRepo sessionsRepo;

    public UserEntity createOrFindUser(String userIdentifier){
        Set<UserEntity> users = userEntityRepo.findAllByPlatformUserId(userIdentifier);
        if (users.size() == 0){
            UserEntity userEntity = new UserEntity(userIdentifier);
            userEntityRepo.save(userEntity);
            return userEntity;
        }
        else if(users.size() == 1){
            return users.iterator().next();
        }
        else{
            UserEntity buffer = null;
            for(UserEntity user : users){
                if(buffer == null || user.getLastActivity() > buffer.getLastActivity()){
                    buffer = user;
                }
            }
            return buffer;
        }
    }

    public UserEntity createOrFindUser(int userIdentifier){
        return createOrFindUser(Integer.toString(userIdentifier));
    }

    public UserEntity findOrNullUser(String userIdentifier){ // можно лучше
        Set<UserEntity> users = userEntityRepo.findAllByPlatformUserId(userIdentifier);
        if (users.isEmpty()){
            return null;
        }
        return users.iterator().next();
    }

    public UserEntity findOrNullUser(int userIdentifier){
        return findOrNullUser(Integer.toString(userIdentifier));
    }


    public void forceCloseSessionIfUserActive(UserEntity user){ //если сессия прошлая не завершена, он ее завершит
        if (user.getActive()){
            Set<Sessions1> userSession = sessionsRepo.findAllByUserEntity(user);
            for (Sessions1 s : userSession){
                if(!s.getIsEnd()){
                    s.forseEnd();
                    sessionsRepo.save(s);
                    user.updatePlayTime(s.getSessionLeght());
                    userEntityRepo.save(user);
                }
            }
            user.setLastActivityInThisTime();
            user.setActive(false);
            userEntityRepo.save(user);
        }
    }

    public void activateUser(UserEntity user){ //создаст или обновит сессию, пропишею юзеру последнюю активность и активирует его
        getLastOrCreateSession(user);
        user.setActive(true);
        user.setLastActivityInThisTime();
        userEntityRepo.save(user);
    }

    public void deactivateUser(UserEntity user){
        Sessions1 session = sessionsRepo.findByUserEntityAndNumberSession(user, user.getSessionCounter());
        session.endSession();
        sessionsRepo.save(session);
        user.setLastActivityInThisTime();
        user.setActive(false);
        user.updatePlayTime(session.getSessionLeght());
        userEntityRepo.save(user);
    }

    private Sessions1 createNewSession(UserEntity user){
        user.addSessionCount();
        user.setLastActivityInThisTime();
        user.setActive(true);
        Sessions1 session = new Sessions1(user, user.getSessionCounter());
        sessionsRepo.save(session);
        userEntityRepo.save(user);
        System.out.println("Создана новая сессия " + user.getPlatformUserId() +" " + user.getSessionCounter());
        return session;
    }

    public Sessions1 getLastOrCreateSession(UserEntity user){
        if (user.getActive()){ //найдет последнюю активную сессию и закроет остальные
            Set<Sessions1> userSession = sessionsRepo.findAllByUserEntity(user);
            Sessions1 session = null;
            
            for (Sessions1 s : userSession){
                if(!s.getIsEnd()){
                    if(session == null || s.getNumberSession() > session.getNumberSession()){ 
                        session = s;
                    }
                }
            }

            if (session == null){
                return createNewSession(user);
            }

            for(Sessions1 s : userSession){
                if(!s.getIsEnd()){
                    if(s != session){
                        s.forseEnd();
                        sessionsRepo.save(s);
                    }
                }
            }
            return session;
        }
        else{
            return createNewSession(user);
        }
    }

    public long getTimeFromStart(UserEntity user){
        Sessions1 session = getLastOrCreateSession(user);
        return user.getPlayTime() + session.getTimeFromStartSession();
    }
}
