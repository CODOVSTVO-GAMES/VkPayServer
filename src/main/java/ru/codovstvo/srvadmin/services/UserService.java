package ru.codovstvo.srvadmin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.codovstvo.srvadmin.entitys.UserData;
import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.repo.UserDataRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;

@Transactional
@Service
public class UserService {

    @Autowired
    UserEntityRepo userEntityRepo;

    @Autowired
    UserDataRepo userDataRepo;

    public UserEntity createOrFindUser(String userIdentifier){
        List<UserEntity> users = userEntityRepo.findAllByPlatformUserId(userIdentifier);
        if(users == null) System.out.println("hello worrrddfsdf");
        if(users.isEmpty()) System.out.println("hello worrrd");
        if (users.isEmpty()){
            UserEntity user = new UserEntity(userIdentifier);
            userEntityRepo.save(user);
            System.out.println("Создан новый пользователь");
            return user;
        }

        for(UserEntity user : users){ //удалит повторки юзеров втупую . Не лучший вариант. Все будет работать хорошо если нигде в системе не создастся два пользователя с одинаковым платформ айди
            if(user == users.get(0)) continue; 
            userEntityRepo.delete(user);
            //залогировать
        }
        return users.get(0);
    }

    public UserEntity createOrFindUser(int userIdentifier){
        return createOrFindUser(Integer.toString(userIdentifier));
    }

    public void activateUser(UserEntity user){
        user.setActive(true);
        user.setLastActivityInThisTime();
        userEntityRepo.save(user);
    }

    public UserEntity findOrNullUser(String userIdentifier){
        List<UserEntity> users = userEntityRepo.findAllByPlatformUserId(userIdentifier);
        if (users.isEmpty()){
            return null;
        }

        for(UserEntity user : users){ //удалит повторки юзеров втупую . Не лучший вариант. Все будет работать хорошо если нигде в системе не создастся два пользователя с одинаковым платформ айди
            if(user == users.get(0)) continue; 
            userEntityRepo.delete(user);
            //залогировать
        }
        return users.get(0);
    }

    public UserEntity findOrNullUser(int userIdentifier){
        return findOrNullUser(Integer.toString(userIdentifier));
    }

    public void saveData(UserEntity user, String key, String value){
        if (user.getUserData() != null){
            for(UserData data : user.getUserData()){
                if (data.getTitle().equals(key)){
                    data.setData(value);
                    userDataRepo.save(data);
                    return;
                }
            }
        }
        UserData data = new UserData(key, value);
        userDataRepo.save(data);
        user.addData(data);
        userEntityRepo.save(user);
    }

    public String getDataByKey(UserEntity user, String key){
        if (user.getUserData() != null){
            for(UserData data : user.getUserData()){
                if(data.getTitle().equals(key)){
                    return data.getTitle();
                }
            }
        }
        return new String();
    }
    
}
