package ru.codovstvo.srvadmin.entitys;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity

public class Sessions {
        
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private UserEntity user;

    private LocalTime startSessionTime;

    private LocalDate startSessionDate;

    private long startSessionDateLong;

    private LocalTime endSessionTime;

    private LocalDate endSessionDate;

    private long sessionLeght;

    private int numberSession;

    public Sessions(){}

    public Sessions(UserEntity user, int numberSession){
        this.user = user;
        this.numberSession = numberSession;
        this.startSessionDate = LocalDate.now(ZoneId.of("GMT+03:00"));
        this.startSessionTime = LocalTime.now(ZoneId.of("GMT+03:00"));

        Date date = new Date();
        this.startSessionDateLong = date.getTime();
    }

    public void endSession()
    {
        this.endSessionDate = LocalDate.now(ZoneId.of("GMT+03:00"));
        this.endSessionTime = LocalTime.now(ZoneId.of("GMT+03:00"));

        Date date = new Date();
        this.sessionLeght = date.getTime() - startSessionDateLong;
    }

}
