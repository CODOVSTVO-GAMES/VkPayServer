package ru.codovstvo.srvadmin.entitys;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.lang.Nullable;

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

    public Event(){}

    public Event(int userId, String version, String platform, String deviceType, String eventName) {
        this.userId = userId;
        this.version = version;
        this.platform = platform;
        this.deviceType = deviceType;
        this.eventName = eventName;
    }

}
