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
public class EventEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    // @ManyToOne
    // private UserEntity user;

    private String version;

    private String platform;

    private String deviceType;

    private String eventName;

    private String language;

    private String referrer;

    private LocalTime localTime;

    private LocalDate date;

    private String loadTime;

    public EventEntity(){}

    public EventEntity(
        // UserEntity user, 
        Version version, String platform, String deviceType, String eventName, String language, String referrer, String loadTime) {
        // this.user = user;
        this.version = version.getVersionIdentifier();
        this.platform = platform;
        this.deviceType = deviceType;
        this.eventName = eventName;
        this.language = language;
        this.referrer = referrer;
        this.loadTime = loadTime;
        
        this.localTime = LocalTime.now(ZoneId.of("GMT+03:00"));
        this.date = LocalDate.now(ZoneId.of("GMT+03:00"));
    }

}
