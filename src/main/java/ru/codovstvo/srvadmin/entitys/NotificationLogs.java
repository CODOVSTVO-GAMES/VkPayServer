package ru.codovstvo.srvadmin.entitys;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Data
@Entity
public class NotificationLogs {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long Id;

    private Long UserId;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String title;

    private LocalTime localTime;

    private LocalDate date;

    public NotificationLogs (){}

    public NotificationLogs (Long userId, NotificationType notificationType, String title){
        this.UserId = userId;
        this.notificationType = notificationType;
        this.title = title;
        this.localTime = LocalTime.now(ZoneId.of("GMT+03:00"));
        this.date = LocalDate.now(ZoneId.of("GMT+03:00"));
    }

}
