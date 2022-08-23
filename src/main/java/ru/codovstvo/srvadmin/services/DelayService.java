package ru.codovstvo.srvadmin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vk.api.sdk.actions.Secure;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import ru.codovstvo.srvadmin.entitys.NotificationLogs;
import ru.codovstvo.srvadmin.entitys.NotificationQueue;
import ru.codovstvo.srvadmin.entitys.NotificationType;
import ru.codovstvo.srvadmin.repo.NotificationLogsRepo;
import ru.codovstvo.srvadmin.repo.NotificationQueueRepo;

@Service
public class DelayService {

    @Autowired
    private NotificationQueueRepo notificationQueueRepo;

    @Autowired
    private NotificationLogsRepo notificationLogsRepo;

    @Autowired
    private SecureVkApiService secureVkApiService;

    @Scheduled(initialDelay = 1000 , fixedDelay = 6000)
    public void notificationManager() throws ApiException, ClientException {
        System.out.println("-------------------------------------------------------12345");
        List<Long> sentUsers = new ArrayList<Long>();
        Date date = new Date();
        Long dateTime = date.getTime();
        for (NotificationType type : NotificationType.values()){
            Set queue = notificationQueueRepo.findAllByNotificationType(type);
            for (Object itemObj : queue){
                NotificationQueue item = (NotificationQueue)itemObj;
                if (item.getExpirationDate() < dateTime) {
                    if (sentUsers.indexOf(item.getUserId()) == -1) { // если небыло отправлено в этот вызов метода
                        String message = "";
                        if (type == NotificationType.COLLERCTAPPLE){
                            message = "Пора собирать урожай яблок!";
                        } else if (type == NotificationType.COLLERCTTANGETINE){
                            message = "Пора собирать урожай мандаринов!";
                        }
                        secureVkApiService.sendNotification(item.getUserId(), message);
                        notificationLogsRepo.save(new NotificationLogs(item.getUserId(), type, message));
                        notificationQueueRepo.deleteAllByUserIdAndNotificationType(item.getUserId(), type);
                        sentUsers.add(item.getUserId());
                    }
                }
            }
        }
    }
    
}
