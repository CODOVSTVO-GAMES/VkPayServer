package ru.codovstvo.srvadmin.entitys;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.lang.Nullable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long itemId;

    @Nullable
    private String title;

    @Nullable
    private String photoUrl;

    @Nullable
    private int price;

    @Nullable
    private int discount;

    @Nullable
    private int appId;
    
    public Item() {}

}//