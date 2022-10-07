package ru.codovstvo.srvadmin.entitys;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    private Boolean active;

    private long lastActivity;

    @OneToMany
    private Set<Sessions1>  sessions;

    @OneToMany
    private Set<UserData> userData;

    @OneToMany
    private Set<EventEntity> events;

    public UserEntity(){}

    public UserEntity(int platformUserId){
        this.platformUserId = Integer.toString(platformUserId);
        this.setActive(false);
    }

    public UserEntity(String platformUserId){
        this.platformUserId = platformUserId;
        this.setActive(false);
    }


    public void setLastActivityInThisTime(){
        Date date = new Date();
        this.lastActivity = date.getTime();
    }

    public void cleanUserData(){
        userData = new HashSet<>();
    }

    public void addSessionCount(){
        sessionCounter = sessionCounter + 1;
    }

    public void updatePlayTime(long sessionPlaytime){
        playTime = playTime + sessionPlaytime;
    }
    

}
