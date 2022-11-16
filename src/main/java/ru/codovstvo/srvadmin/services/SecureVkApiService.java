package ru.codovstvo.srvadmin.services;

import java.util.Arrays;

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
    
    public void sendLevelUpEvent(int level, String userId) throws Exception {
        try{
            HttpTransportClient httpClient = new HttpTransportClient();
            VkApiClient vk = new VkApiClient(httpClient);

            ServiceActor actor = new ServiceActor(8180902, "3diesn7ViJiMazGJa7my", "c7c21837c7c21837c7c21837fbc7becc91cc7c2c7c21837a534417df3e7eab26d5a63cf");

            vk.secure().addAppEvent(actor, Integer.parseInt(userId), 1).value(level).execute();
        }
        catch (Exception e){System.out.println("Уровень равен или меньше текущего");}
    }

    public void sendProgressMission(int mission, String userId) throws Exception{
        try{
        HttpTransportClient httpClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(httpClient);

        ServiceActor actor = new ServiceActor(8180902, "3diesn7ViJiMazGJa7my", "c7c21837c7c21837c7c21837fbc7becc91cc7c2c7c21837a534417df3e7eab26d5a63cf");

        vk.secure().addAppEvent(actor, Integer.parseInt(userId), mission).execute();
        } catch(Exception e){System.out.println("Знакомство с иваном уже было");}
    }

    public void sendNotification(String userId, String message) throws ApiException, ClientException {
        HttpTransportClient httpClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(httpClient);

        ServiceActor actor = new ServiceActor(8180902, "3diesn7ViJiMazGJa7my", "c7c21837c7c21837c7c21837fbc7becc91cc7c2c7c21837a534417df3e7eab26d5a63cf");
        
        vk.secure().sendNotification(actor, message).userId(Integer.parseInt(userId)).execute();
    }

    public void sendNotification(String[] userIds, String message) throws ApiException, ClientException {
        HttpTransportClient httpClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(httpClient);

        ServiceActor actor = new ServiceActor(8180902, "3diesn7ViJiMazGJa7my", "c7c21837c7c21837c7c21837fbc7becc91cc7c2c7c21837a534417df3e7eab26d5a63cf");

        Integer[] ids = new Integer[userIds.length];

        for(int i = 0; i < userIds.length; i++){
            System.out.println("---" + ids[i]);
            ids[i] = Integer.parseInt(userIds[i]);
        }
        
        vk.secure().sendNotification(actor, message).userIds(ids).execute();
    }

    public void sendWallPost(Long userId, int level){
    }
}
