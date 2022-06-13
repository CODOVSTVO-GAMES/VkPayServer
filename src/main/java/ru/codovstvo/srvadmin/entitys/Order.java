package ru.codovstvo.srvadmin.entitys;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
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

    private Long initDate;

    private Long executeDate;

    public Order(Long orderVkId, Long appId, Item item, Long userId, Long receiverId, OrderStatus orderStatus, Long initDate) {
        this.orderVkId  = orderVkId;
        this.appId = appId;
        this.item = item;
        this.userId = userId;
        this.receiverId = receiverId;
        this.orderStatus = orderStatus;
        this.initDate = initDate;
    }

    public Order() {}

}