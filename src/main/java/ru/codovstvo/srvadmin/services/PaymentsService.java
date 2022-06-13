package ru.codovstvo.srvadmin.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.codovstvo.srvadmin.dto.ErrorTransfer;
import ru.codovstvo.srvadmin.dto.ResponseTransfer;
import ru.codovstvo.srvadmin.entitys.Item;
import ru.codovstvo.srvadmin.entitys.Order;
import ru.codovstvo.srvadmin.entitys.OrderStatus;
import ru.codovstvo.srvadmin.repo.ItemRepo;
import ru.codovstvo.srvadmin.repo.OrderRepo;

@Service
public class PaymentsService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private OrderRepo orderRepo;

    public Object orderInit(String itemTitle, Long appId, Long orderVkId, Long userId, Long receiverId) {
        Item item = itemRepo.findByTitle(itemTitle);

        if (!item.equals(null) ) {
            Order order = new Order(orderVkId, appId, item, userId, receiverId, OrderStatus.INITIALIZED);
            orderRepo.save(order);

            Map response = new HashMap<>();
            response.put("title", item.getTitle());
            response.put("price", item.getPrice());
            response.put("photo_url", item.getPhotoUrl());
            response.put("discount", item.getDiscount());
            response.put("item_id", item.getItemId());
            response.put("expiration", 0);
            return response(response);
        } else {
            return error(20);
        }
    }

    public Object OrderExecuted(Long orderVkId, Long vkDate) {
        Order order = orderRepo.findByOrderVkId(orderVkId);
        if (!order.equals(null)) {
            order.setOrderStatus(OrderStatus.EXECUTED);
            order.setVkDate(vkDate);
            orderRepo.save(order);
            
            Map response = new HashMap<>();
            response.put("order_id", order.getOrderVkId());
            response.put("app_order_id", order.getOrderId());
            return response(response);
        } else {
            return error(11);
        }
    }

    public ResponseTransfer response(Map response) {
        ResponseTransfer responseTransfer = new ResponseTransfer();
        responseTransfer.setResponse(response);
        return responseTransfer;
    }

    public ErrorTransfer error(int errorCode) {
        ErrorTransfer errorTransfer = new ErrorTransfer();
        Map response = new HashMap<>();
        if (errorCode == 1) {
            response.put("error_code", "1");
            response.put("error_msg", "Event is not processed by the server");
            response.put("critical", true);
        } else if (errorCode == 404) {
            response.put("error_code", "404");
            response.put("error_msg", "Not found");
            response.put("critical", true);
        } else if (errorCode == 20) {
            response.put("error_code", "20");
            response.put("error_msg", "Item not found");
            response.put("critical", true);
        } else if (errorCode == 11) {
            response.put("error_code", "11");
            response.put("error_msg", "Order not found");
            response.put("critical", true);
        }
        return errorTransfer;
    }
}