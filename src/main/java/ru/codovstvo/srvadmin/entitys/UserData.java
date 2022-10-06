package ru.codovstvo.srvadmin.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
public class UserData {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String title;

    @Column(columnDefinition="TEXT")
    private String data;

    public UserData(){}

    public UserData(String title, String data){
        this.title = title;
        this.data = data;
    }
}
