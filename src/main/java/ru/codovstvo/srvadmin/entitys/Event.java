package ru.codovstvo.srvadmin.entitys;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private int userId;

    private String version;

    private String platform;

    private String deviceType;

    private String eventName;

    private String language;

    private String referrer;

    private LocalTime localTime;

    private LocalDate date;

    private String loadTime;

    public Event(){}

    public Event(int userId, String version, String platform, String deviceType, String eventName, String language, String referrer, String loadTime) {
        this.userId = userId;
        this.version = version;
        this.platform = platform;
        this.deviceType = deviceType;
        this.eventName = eventName;
        this.language = language;
        this.referrer = referrer;
        this.loadTime = loadTime;
        this.localTime = LocalTime.now(ZoneId.of("GMT+03:00"));
        this.date = LocalDate.now(ZoneId.of("GMT+03:00"));
    }
    
    public Event(int userId, String eventName)
    {
        this.userId = userId;
        this.eventName = eventName;
    }

}
