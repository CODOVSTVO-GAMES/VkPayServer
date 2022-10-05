package ru.codovstvo.srvadmin.services;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.codovstvo.srvadmin.repo.UserDataRepo;

@Transactional
@Service
public class DataService {

    @Autowired
    UserDataRepo userDataRepo;
    
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
}
