package ru.codovstvo.srvadmin.services;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.codovstvo.srvadmin.entitys.UserData;
import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.repo.UserDataRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;

@Transactional
@Service
public class DataService {

    @Autowired
    UserDataRepo userDataRepo;

    @Autowired
    UserEntityRepo userEntityRepo;

    public Map<String, String> requestBodyParser(String requestBody){
        Map<String, String> parameters =  new HashMap<>();
        String[] params = requestBody.toString().split("&");
        for(String para : params){
            try{
                String[] keyValue = para.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }catch (Exception e){ //если пустое значение
                parameters.put(para.replace("=", ""), "");
            }
        }
        return parameters;
    }

    public void saveData(UserEntity user, String key, String value){
        Set<UserData> datas = userDataRepo.findByUserEntity(user);
        if (!datas.isEmpty()){
            for (UserData data : datas){
                if (data.getTitle().equals(key)){
                    data.setData(value);
                    userDataRepo.save(data);
                    System.out.println("сохранение обновлено id: " + user.getPlatformUserId() + " | key : "  + key);
                    return;
                }
            }
        }
        System.out.println("сохранения нет id:" + user.getPlatformUserId() + " | key : "  + key);
        UserData data = new UserData(user, key, value);
        userDataRepo.save(data);
    }

    public String GetDataByUserAndKey(UserEntity user, String key){
        Set<UserData> datas = userDataRepo.findByUserEntity(user);
        if (!datas.isEmpty()){
            for (UserData data : datas){
                if (data.getTitle().equals(key)){
                    return data.getData();
                }
            }
        }
        return new String();
    }

    public void deleteUserData(UserEntity user){
        userDataRepo.deleteAllByUserEntity(user);

        System.out.println("Данные удалены id: " + user.getPlatformUserId());
    }

}
