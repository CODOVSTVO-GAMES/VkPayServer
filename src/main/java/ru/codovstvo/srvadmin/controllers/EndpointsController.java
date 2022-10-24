package ru.codovstvo.srvadmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.repo.SessionsRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;

import org.springframework.transaction.annotation.Transactional;

@Transactional
@RestController
@RequestMapping(value = "back/data")
public class EndpointsController {

    @Autowired
    UserEntityRepo userEntityRepo;

    @Autowired
    SessionsRepo sessionsRepo;
    
    @GetMapping(value="/allusers")
    public Iterable<UserEntity> getUsers() {
        Iterable<UserEntity> users = userEntityRepo.findAll();
        return users;
    }

    @GetMapping(value="/allevents")
    public Iterable<UserEntity> getEvents() {
        Iterable<UserEntity> users = userEntityRepo.findAll();
        return users;
    }

    @GetMapping(value="/allversions")
    public Iterable<UserEntity> getVersions() {
        Iterable<UserEntity> users = userEntityRepo.findAll();
        return users;
    }
    
}
