package ru.codovstvo.srvadmin.entitys;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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

    private long lastActivity;

    private Boolean active;

    @OneToMany(orphanRemoval=true)
    @JoinColumn(name="userEntityKek")
    private Set<UserData> userData;

    @OneToMany
    private Set<EventEntity> events;

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

    public void cleanUserData(){
        userData = new HashSet<>();
    }

    public UserData addOrUpdateUserData(String key, String data){
        if (userData == null || userData.isEmpty()){
            userData = new HashSet<>();
            UserData inst = new UserData(this, key, data);
            userData.add(inst);
            return inst;
        }
        
        for(UserData ins : userData){
            if (ins.getTitle().equals(key)){
                ins.setData(data);
                return ins;
            }
        }
        System.out.println("EBANY NULL");
        return null;
    }

}
