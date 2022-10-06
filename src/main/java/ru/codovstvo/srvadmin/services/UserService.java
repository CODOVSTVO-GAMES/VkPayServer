package ru.codovstvo.srvadmin.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.codovstvo.srvadmin.entitys.EventEntity;
import ru.codovstvo.srvadmin.entitys.UserData;
import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.repo.EventRepo;
import ru.codovstvo.srvadmin.repo.UserDataRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;

@Transactional
@Service
public class UserService {

    @Autowired
    UserEntityRepo userEntityRepo;

    @Autowired
    UserDataRepo userDataRepo;

    @Autowired
    EventRepo eventRepo;

    public UserEntity createOrFindUser(String userIdentifier){
        List<UserEntity> users = userEntityRepo.findAllByPlatformUserId(userIdentifier);
        if (users.isEmpty()){
            UserEntity user = new UserEntity(userIdentifier);
            userEntityRepo.save(user);
            System.out.println("Создан новый пользователь id: " + user.getId());
            return user;
        }

        // for(UserEntity user : users){ //удалит повторки юзеров втупую . Не лучший вариант. Все будет работать хорошо если нигде в системе не создастся два пользователя с одинаковым платформ айди
        //     if(user == users.get(0)) continue; 
        //     userEntityRepo.delete(user);
        //     //залогировать
        // }
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

        // for(UserEntity user : users){ //удалит повторки юзеров втупую . Не лучший вариант. Все будет работать хорошо если нигде в системе не создастся два пользователя с одинаковым платформ айди
        //     if(user == users.get(0)) continue; 
        //     userEntityRepo.delete(user);
        //     //залогировать
        // }
        return users.get(0);
    }

    public UserEntity findOrNullUser(int userIdentifier){
        return findOrNullUser(Integer.toString(userIdentifier));
    }


    // public void saveData(UserEntity user, String key, String value){
    //     Set<UserData> datas = user.getUserData();
    //     if (datas == null)System.out.println("keks");
    //     if (datas.isEmpty())System.out.println("ddfkeks");
    //     if (datas != null && !datas.isEmpty()){
    //         for (UserData data : datas){
    //             if (data.getTitle().equals(key)){
    //                 data.setData(value);
    //                 userDataRepo.save(data);
    //                 System.out.println("сохранение обновлено id: " + user.getPlatformUserId() + " | key : "  + key);
    //                 return;
    //             }
    //         }
    //     }
    //     System.out.println("сохранения нет id:" + user.getPlatformUserId() + " | key : "  + key);
    //     UserData data = new UserData(user, key, value);
    //     userDataRepo.save(data);
    // }

    // public String GetDataByUserAndKey(UserEntity user, String key){
    //     Set<UserData> datas = user.getUserData();
    //     if (datas != null && !datas.isEmpty()){
    //         for (UserData data : datas){
    //             if (data.getTitle().equals(key)){
    //                 return data.getData();
    //             }
    //         }
    //     }
    //     return new String();
    // }
    
}
