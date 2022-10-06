package ru.codovstvo.srvadmin.entitys;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String platformUserId;

    private int sessionCounter;

    private long playTime;

    private long lastActivity;

    private Boolean active;

    public UserEntity(){}

    public UserEntity(int platformUserId){
        this.platformUserId = Integer.toString(platformUserId);
    }

    public UserEntity(String platformUserId){
        this.platformUserId = platformUserId;
    }

    public void setLastActivityInThisTime(){
        Date date = new Date();
        this.lastActivity = date.getTime();
    }

}
