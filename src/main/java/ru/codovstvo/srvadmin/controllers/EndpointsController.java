package ru.codovstvo.srvadmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.codovstvo.srvadmin.entitys.EventEntity;
import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.entitys.Version;
import ru.codovstvo.srvadmin.repo.EventRepo;
import ru.codovstvo.srvadmin.repo.SessionsRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;
import ru.codovstvo.srvadmin.repo.VersionRepo;

import org.springframework.transaction.annotation.Transactional;

@Transactional
@RestController
@RequestMapping(value = "back/data")
public class EndpointsController {

    @Autowired
    UserEntityRepo userEntityRepo;

    @Autowired
    SessionsRepo sessionsRepo;
    
    @Autowired
    EventRepo eventRepo;

    @Autowired
    VersionRepo versionRepo;
    
    @GetMapping(value="/allusers")
    public Iterable<UserEntity> getUsers() {
        Iterable<UserEntity> users = userEntityRepo.findAll();
        return users;
    }

    @GetMapping(value="/allevents")
    public Iterable<EventEntity> getEvents() {
        Iterable<EventEntity> events = eventRepo.findAll();
        return events;
    }

    @GetMapping(value="/allversions")
    public Iterable<Version> getVersions() {
        Iterable<Version> versions = versionRepo.findAll();
        return versions;
    }
    
}
