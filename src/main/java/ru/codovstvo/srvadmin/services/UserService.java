package ru.codovstvo.srvadmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;

@Service
public class UserService {

    @Autowired
    UserEntityRepo userEntityRepo;


    public UserEntity createOrFindVersion(String userId){
        UserEntity user = userEntityRepo.findByPlatformUserId(userId);
        if (user == null){
            user = new UserEntity(userId);
            userEntityRepo.save(user);
            System.out.println("Создан новый пользователь");
        }
        return user;
    }

    public UserEntity createOrFindVersion(int userId){
        return createOrFindVersion(Integer.toString(userId));
    }
    
}
