package ru.codovstvo.srvadmin.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DelayService {

    @Scheduled(initialDelay = 1000 , fixedDelay = 6000)
    public void notificationManager(){
        System.out.println("-------------------------------------------------------12345");
    }
    
}
