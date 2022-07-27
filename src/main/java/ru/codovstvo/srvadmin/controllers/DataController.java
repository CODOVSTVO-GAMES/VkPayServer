package ru.codovstvo.srvadmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "back/data")
public class DataController {
    
    @PostMapping("set")
    public ResponseEntity setData(@RequestParam String data){
        System.out.println(data);
        return new ResponseEntity(HttpStatus.OK);
    }
}
