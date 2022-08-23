package ru.codovstvo.srvadmin.entitys;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Data
@Entity
public class NotificationQueue {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long Id;

    private Long userId;

    private Long expirationDate;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public NotificationQueue(){}

    public NotificationQueue(Long userId, NotificationType notificationType){
        this.userId = userId;
        this.notificationType = notificationType;
        
        Date date = new Date();
        this.expirationDate = date.getTime() + 10800000L;
    }

}
