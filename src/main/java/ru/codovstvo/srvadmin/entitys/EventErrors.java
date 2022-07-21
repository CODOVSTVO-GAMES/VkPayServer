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
public class EventErrors {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String errorParams;

    private LocalTime localTime;

    private LocalDate date;

    public EventErrors(){}

    public EventErrors(String params){
        this.errorParams = params;
        this.localTime = LocalTime.now(ZoneId.of("GMT+03:00"));
        this.date = LocalDate.now(ZoneId.of("GMT+03:00"));
    }

}
