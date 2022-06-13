package ru.codovstvo.srvadmin.entitys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long itemId;

    private String title;

    private String photoUrl;

    private int price;

    private int discount;

    private int appId;
    
    public Item() {}

}