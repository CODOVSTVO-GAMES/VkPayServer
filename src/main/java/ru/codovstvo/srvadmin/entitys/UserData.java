package ru.codovstvo.srvadmin.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
public class UserData {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ManyToOne
    private UserEntity user;

    private String title;

    @Column(columnDefinition="TEXT")
    private String data;

    public UserData(){}

    public UserData(UserEntity user, String title, String data){
        this.user = user;
        this.title = title;
        this.data = data;
    }
}
