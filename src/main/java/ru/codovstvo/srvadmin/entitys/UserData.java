package ru.codovstvo.srvadmin.entitys;

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

    private int userId;

    private String title;

    private String data;

    public UserData(){}

    public UserData(int userId, String title, String data){
        this.userId = userId;
        this.title = title;
        this.data = data;
    }
}
