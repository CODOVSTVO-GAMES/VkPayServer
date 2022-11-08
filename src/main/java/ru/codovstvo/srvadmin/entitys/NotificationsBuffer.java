package ru.codovstvo.srvadmin.entitys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
public class NotificationsBuffer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ManyToOne
    private UserEntity userEntity;

    public NotificationsBuffer(UserEntity user){
        this.userEntity = user;
    }

    public NotificationsBuffer(){
    }
    
}
