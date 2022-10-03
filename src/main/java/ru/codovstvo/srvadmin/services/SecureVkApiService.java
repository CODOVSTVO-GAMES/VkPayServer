package ru.codovstvo.srvadmin.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

@Transactional
@Service
public class SecureVkApiService {
    
    public void sendLevelUpEvent(int level, int userId) throws Exception {
        HttpTransportClient httpClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(httpClient);

        ServiceActor actor = new ServiceActor(8180902, "3diesn7ViJiMazGJa7my", "c7c21837c7c21837c7c21837fbc7becc91cc7c2c7c21837a534417df3e7eab26d5a63cf");

        vk.secure().addAppEvent(actor, userId, 1).value(level).execute();
    }

    public void sendProgressMission(int mission, int userId) throws Exception{
        HttpTransportClient httpClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(httpClient);

        ServiceActor actor = new ServiceActor(8180902, "3diesn7ViJiMazGJa7my", "c7c21837c7c21837c7c21837fbc7becc91cc7c2c7c21837a534417df3e7eab26d5a63cf");

        vk.secure().addAppEvent(actor, userId, mission).execute();
    }

    public void sendNotification(Long userId, String message) throws ApiException, ClientException {
        HttpTransportClient httpClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(httpClient);

        ServiceActor actor = new ServiceActor(8180902, "3diesn7ViJiMazGJa7my", "c7c21837c7c21837c7c21837fbc7becc91cc7c2c7c21837a534417df3e7eab26d5a63cf");
        
        vk.secure().sendNotification(actor, message).execute();
    }
}
