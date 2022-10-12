package ru.codovstvo.srvadmin.entitys;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.lang.Nullable;

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

    private String referer;

    private LocalDate registrationDate;

    private Integer adsCounter;

    private String platform;

    private String deviseType;

    public UserEntity(){}

    public UserEntity(int platformUserId, String platform, String deviceType){
        this.platformUserId = Integer.toString(platformUserId);
        this.setActive(false);

        this.registrationDate = LocalDate.now(ZoneId.of("GMT+03:00"));
        this.referer = "no_referrer";
        this.adsCounter = 0;

        this.platform = platform;
        this.deviseType = deviceType;
    }

    public UserEntity(String platformUserId, String platform, String deviceType){
        this.platformUserId = platformUserId;
        this.setActive(false);

        this.registrationDate = LocalDate.now(ZoneId.of("GMT+03:00"));
        this.referer = "no_referrer";
        this.adsCounter = 0;

        this.platform = platform;
        this.deviseType = deviceType;
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
    
    public void addAdsCount(){
        if(adsCounter == null){adsCounter = 0;}
        adsCounter = adsCounter + 1;
    }
    
    public void updateDeviseType(String newDeviseType){
        if(deviseType.contains("&")){
            String[] types = deviseType.split("&");
            
            for(int i = 0; i < types.length; i++){
                if (types[i].equals(newDeviseType)){
                    break;
                }
            }
            deviseType = "&" + newDeviseType;
        }
        else {
            if(!newDeviseType.equals(deviseType)){
                deviseType = "&" + newDeviseType;
            }
        }
    }
}
