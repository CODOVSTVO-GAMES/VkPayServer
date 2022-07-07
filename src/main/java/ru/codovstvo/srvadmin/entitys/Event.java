package ru.codovstvo.srvadmin.entitys;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.lang.Nullable;

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

    public Event(){}

    public Event(int userId, String version, String platform, String deviceType, String eventName, String language, String referrer) {
        this.userId = userId;
        this.version = version;
        this.platform = platform;
        this.deviceType = deviceType;
        this.eventName = eventName;
        this.language = language;
        this.referrer = referrer;
        this.localTime = LocalTime.now(ZoneId.of("GMT+03:00"));
        this.date = LocalDate.now(ZoneId.of("GMT+03:00"));
    }

}
