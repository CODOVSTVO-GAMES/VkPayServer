package ru.codovstvo.srvadmin.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.services.CryptoService;
import ru.codovstvo.srvadmin.services.DataService;
import ru.codovstvo.srvadmin.services.UserService;

import org.springframework.web.bind.annotation.RequestParam;

@Transactional
@RestController
@RequestMapping(value = "back/data")
public class DataController {

    @Autowired
    DataService dataService;

    @Autowired
    CryptoService cryptoService;
    
    @Autowired
    UserService userService;
    
    @PostMapping("set")
    public ResponseEntity setData(@RequestParam String hash, @RequestBody String requestBody) throws Exception {

        if (!cryptoService.encodeHmac256(requestBody).equals(hash)){// если хеш неверный
            System.out.println("неверный хеш дата контроллер");
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
            
        Map<String, String> parameters =  dataService.requestBodyParser(requestBody);
        
        String userId = parameters.get("userId");
        String key = parameters.get("key");
        String data = parameters.get("value");

        UserEntity user = userService.createOrFindUser(userId);

        dataService.saveData(user, key, data);

        userService.activateUser(user);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("dellall")
    public void deleteData(@RequestBody String requestBody) throws Exception {

        Map<String, String> parameters =  dataService.requestBodyParser(requestBody);

        int userId = Integer.parseInt(parameters.get("userId"));
        String hash = parameters.get("hash");

        if (cryptoService.encodeHmac256(parameters.get("userId")).equals(hash)){
            UserEntity user = userService.createOrFindUser(userId);
            dataService.deleteUserData(user);
        }
    }

    @GetMapping("get")
    public String getData(@RequestParam int userId, @RequestParam String key){
        UserEntity user = userService.findOrNullUser(userId);
        if (user == null){
            return new String();
        }
        return dataService.GetDataByUserAndKey(user, key);
    }

}
