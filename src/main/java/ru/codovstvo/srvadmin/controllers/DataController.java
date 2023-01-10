package ru.codovstvo.srvadmin.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.services.CryptoService;
import ru.codovstvo.srvadmin.services.DataService;
import ru.codovstvo.srvadmin.services.UserService;



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
    public ResponseEntity setData(@RequestParam String hash, @RequestBody String requestBody, @RequestHeader Map<String, String> headers) throws Exception {

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
        System.out.println(headers.get("Referer"));

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("dellall")
    public void deleteData(@RequestBody String requestBody) throws Exception {

        Map<String, String> parameters =  dataService.requestBodyParser(requestBody);

        String hash = parameters.get("hash");

        if (cryptoService.encodeHmac256(parameters.get("userId")).equals(hash)){
            UserEntity user = userService.createOrFindUser(parameters.get("userId"));
            dataService.deleteUserData(user);
        }
    }

    @GetMapping("get")
    public String getData(@RequestParam String userId, @RequestParam String key){
        UserEntity user = userService.findOrNullUser(userId);
        if (user == null){
            return new String();
        }
        return dataService.GetDataByUserAndKey(user, key);
    }

}
