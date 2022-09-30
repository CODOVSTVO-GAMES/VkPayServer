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
public class Version {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String versionIdentifier;


    private LocalTime startTime;

    private LocalDate startDate;

    private long startDateLong;

    
    private LocalTime endTime;

    private LocalDate endDate;

    private long endDateLong;


    public Version(){}

    public Version(String version){
        this.versionIdentifier = version;
        this.startTime = LocalTime.now(ZoneId.of("GMT+03:00"));
        this.startDate = LocalDate.now(ZoneId.of("GMT+03:00"));

        Date date = new Date();
        this.startDateLong = date.getTime();
    }

    public void endSession(){
        this.endTime = LocalTime.now(ZoneId.of("GMT+03:00"));
        this.endDate = LocalDate.now(ZoneId.of("GMT+03:00"));

        Date date = new Date();
        this.endDateLong = date.getTime();
    }
}
