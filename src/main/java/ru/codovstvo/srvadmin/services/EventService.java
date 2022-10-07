package ru.codovstvo.srvadmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.codovstvo.srvadmin.entitys.EventEntity;
import ru.codovstvo.srvadmin.repo.EventRepo;

@Transactional
@Service
public class EventService {
    
    @Autowired
    EventRepo eventRepo;

    public void newEvent(EventEntity eventEntity){
        eventRepo.save(eventEntity);
    }
}
