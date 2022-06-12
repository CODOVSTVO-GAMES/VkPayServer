package ru.codovstvo.srvadmin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;

import ru.codovstvo.srvadmin.dto.*;

@RestController
@RequestMapping(value = "vk")
public class VkController {
    
    @PostMapping
    public ItemResponseDto VkNotification(@RequestParam String notification_type,
                                    @RequestParam String app_id,
                                    @RequestParam String user_id,
                                    @RequestParam(name = "item", required=false) String item,
                                    @RequestParam(name = "receiver_id", required=false) String receiver_id,
                                    @RequestParam(name = "order_id", required=false) String order_id,
                                    @RequestParam(name = "version", required=false) String version,
                                    @RequestParam(name = "lang", required=false) String lang,
                                    @RequestParam(name = "sig", required=false) String sig,
                                    @RequestParam(name = "date", required=false) String date,
                                    @RequestParam(name = "status", required=false) String status,
                                    @RequestParam(name = "item_id", required=false) String item_id,
                                    @RequestParam(name = "item_title", required=false) String item_title,
                                    @RequestParam(name = "item_photo_url", required=false) String item_photo_url,
                                    @RequestParam(name = "item_price", required=false) String item_price,
                                    @RequestParam(name = "item_discount", required=false) String item_discount
                                    ) {
        if (notification_type.equals("get_item_test")) {
            System.out.println("Обработчик события: " + notification_type);
            ItemResponseDto itemResponseDto = new ItemResponseDto();
            Map response = new HashMap<>();
            response.put("title", "item1");
            response.put("price", 10);
            itemResponseDto.setResponse(response);
            // response.put("expiration", 6000);
            return itemResponseDto;
        } else if (notification_type.equals("order_status_change_test")) {
            System.out.println("Обработчик события: " + notification_type);
            ItemResponseDto itemResponseDto = new ItemResponseDto();
            Map response = new HashMap<>();
            response.put("order_id", order_id);
            response.put("app_order_id", Math.random() * 1000);
            itemResponseDto.setResponse(response);
            return itemResponseDto;
        }
        System.out.println("Событие не существует: " + notification_type);
        ItemResponseDto itemResponseDto = new ItemResponseDto();
        Map response = new HashMap<>();
        itemResponseDto.setResponse(response);
        return itemResponseDto;
    }
}