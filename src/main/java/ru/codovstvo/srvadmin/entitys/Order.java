package ru.codovstvo.srvadmin.entitys;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
@Table(name="orderEntity")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long orderId;

    private Long orderVkId;
    
    private Long appId;

    @ManyToOne
    private Item item;

    private Long userId;

    private Long receiverId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Long vkDate;

    private LocalTime localTime;

    private LocalDate date;


    public Order(Long orderVkId, Long appId, Item item, Long userId, Long receiverId, OrderStatus orderStatus) {
        this.orderVkId  = orderVkId;
        this.appId = appId;
        this.item = item;
        this.userId = userId;
        this.receiverId = receiverId;
        this.orderStatus = orderStatus;
        this.localTime = LocalTime.now(ZoneId.of("GMT+03:00"));
        this.date = LocalDate.now(ZoneId.of("GMT+03:00"));
    }

    public Order() {}

}