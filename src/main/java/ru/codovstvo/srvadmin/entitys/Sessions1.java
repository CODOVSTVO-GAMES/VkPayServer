package ru.codovstvo.srvadmin.entitys;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
public class Sessions1 {
        
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ManyToOne
    private UserEntity userEntity;

    private LocalTime startSessionTime;

    private LocalDate startSessionDate;

    private long startSessionDateLong;

    private LocalTime endSessionTime;

    private LocalDate endSessionDate;

    private long sessionLeght;

    private int numberSession;

    private boolean isEnd;

    private boolean isForseEnd;

    @OneToMany
    private Set<EventEntity> events;

    public Sessions1(){}

    public Sessions1(UserEntity user, int numberSession){
        this.userEntity = user;
        this.numberSession = numberSession;
        this.startSessionDate = LocalDate.now(ZoneId.of("GMT+03:00"));
        this.startSessionTime = LocalTime.now(ZoneId.of("GMT+03:00"));

        Date date = new Date();
        this.startSessionDateLong = date.getTime();
        this.isEnd = false;
        this.isForseEnd = false;
    }

    public void endSession()
    {
        if(!isEnd){
            this.isEnd = true;
            this.endSessionDate = LocalDate.now(ZoneId.of("GMT+03:00"));
            this.endSessionTime = LocalTime.now(ZoneId.of("GMT+03:00"));

            this.sessionLeght = userEntity.getLastActivity() - startSessionDateLong;
        }

    }

    public void forseEnd(){
        if(!isEnd){
            this.isEnd = true;
            this.isForseEnd = true;
            this.endSessionDate = LocalDate.now(ZoneId.of("GMT+03:00"));
            this.endSessionTime = LocalTime.now(ZoneId.of("GMT+03:00"));

            this.sessionLeght = userEntity.getLastActivity() - startSessionDateLong;
        }
    }

    public boolean getIsEnd(){
        return isEnd;
    }

    public long getTimeFromStartSession(){
        Date date = new Date();
        return date.getTime() - startSessionDateLong;
    }

}
