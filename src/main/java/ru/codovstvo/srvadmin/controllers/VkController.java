package ru.codovstvo.srvadmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.codovstvo.srvadmin.entitys.UserEntity;
import ru.codovstvo.srvadmin.services.PaymentsService;
import ru.codovstvo.srvadmin.services.UserService;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "back/payments")
public class VkController {

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    UserService userService;

    @PostMapping
    public Object VkNotification(@RequestParam String notification_type,
                                @RequestParam Long app_id,
                                @RequestParam Long user_id,
                                @RequestParam(name = "order_id", required=false) Long order_Vk_id,
                                @RequestParam(name = "item", required=false) String item,
                                @RequestParam(name = "receiver_id", required=false) Long receiver_id,
                                @RequestParam(name = "version", required=false) String version,
                                @RequestParam(name = "lang", required=false) String lang,
                                @RequestParam(name = "sig", required=false) String sig,
                                @RequestParam(name = "date", required=false) Long vkDate,
                                @RequestParam(name = "status", required=false) String status,
                                @RequestParam(name = "item_id", required=false) Long item_id,
                                @RequestParam(name = "item_title", required=false) String item_title,
                                @RequestParam(name = "item_photo_url", required=false) String item_photo_url,
                                @RequestParam(name = "item_price", required=false) Long item_price,
                                @RequestParam(name = "item_discount", required=false) Long item_discount
                                ) {
        if (notification_type.equals("get_item_test")) {
            String userId = user_id.toString();
            UserEntity user = userService.createOrFindUser(userId);

            return paymentsService.orderInit(item, app_id, order_Vk_id, user_id, receiver_id, userService.getTimeFromStart(user));

        } else if(notification_type.equals("order_status_change_test")) {
            return paymentsService.OrderExecuted(order_Vk_id, vkDate);

        } else if (notification_type.equals("get_item")) {
            String userId = user_id.toString();
            UserEntity user = userService.createOrFindUser(userId);

            return paymentsService.orderInit(item, app_id, order_Vk_id, user_id, receiver_id, userService.getTimeFromStart(user));

        } else if(notification_type.equals("order_status_change")) {
            return paymentsService.OrderExecuted(order_Vk_id, vkDate);

        } else {
            return paymentsService.error(1);
        }
    }

}