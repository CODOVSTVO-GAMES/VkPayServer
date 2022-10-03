package ru.codovstvo.srvadmin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;

@Transactional
@Service
public class UserService {

    @Autowired
    UserEntityRepo userEntityRepo;


    public UserEntity createOrFindVersion(String userId){
        List<UserEntity> users = userEntityRepo.findAllByPlatformUserId(userId);
        if (users.isEmpty()){
            UserEntity user = new UserEntity(userId);
            userEntityRepo.save(user);
            System.out.println("Создан новый пользователь");
        }
        return users.get(0);
    }

    public UserEntity createOrFindVersion(int userId){
        return createOrFindVersion(Integer.toString(userId));
    }
    
}
