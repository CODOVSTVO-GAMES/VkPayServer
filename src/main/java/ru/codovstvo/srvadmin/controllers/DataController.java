package ru.codovstvo.srvadmin.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.codovstvo.srvadmin.entitys.UserData;
import ru.codovstvo.srvadmin.repo.UserDataRepo;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "back/data")
public class DataController {

    @Autowired
    UserDataRepo userDataRepo;
    
    @PostMapping("set")
    public ResponseEntity setData(
                                        @RequestBody String requestBody
                                    // @RequestParam int userId,
                                    // @RequestParam String key,
                                    // @RequestParam String value
                                    ){
                                        // System.out.println(requestBody.toString());
        Map<String, String> parameters =  new HashMap<>();
        String[] params = requestBody.toString().split("&");
        for(String para : params){
            try{
                String[] keyValue = para.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }catch (Exception e){
                parameters.put(para.replace("=", ""), "");
            }
        }
        int userId = Integer.parseInt(parameters.get("userId"));
        String key = parameters.get("key");
        String data = parameters.get("value");
        System.out.println("--------");
        System.out.println(userId);
        System.out.println(key);
        System.out.println("--------");
        try{
            UserData userData = userDataRepo.findByUserIdAndTitle(userId, key);
            userData.setData(data);
            userDataRepo.save(userData);
            System.out.println("rg4erggewrg");
        }catch (Exception e){
            userDataRepo.save(new UserData(userId, key, data));
            System.out.println("ffrists");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("get")
    public String getData(@RequestParam int userId,
                            @RequestParam String key){
            try{
                return userDataRepo.findByUserIdAndTitle(userId, key).getData();
            }catch (Exception e){
                return new String();
            }
    }
}
