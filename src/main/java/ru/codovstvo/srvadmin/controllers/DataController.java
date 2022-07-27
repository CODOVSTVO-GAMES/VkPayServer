package ru.codovstvo.srvadmin.controllers;

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
                                        System.out.println(requestBody.toString());
                                        // System.out.println(userId);
        // System.out.println(key);
        // System.out.println(value);
        // try{
        //     UserData userData = userDataRepo.findByUserIdAndTitle(userId, key);
        //     userData.setData(value);
        // }catch (Exception e){
        //     userDataRepo.save(new UserData(userId, key, value));
        // }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("get")
    public String getData(@RequestParam int userId,
                            @RequestParam String key){
        return userDataRepo.findByUserIdAndTitle(userId, key).getData();
    }
}
