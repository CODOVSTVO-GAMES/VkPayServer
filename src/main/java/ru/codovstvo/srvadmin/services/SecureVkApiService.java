package ru.codovstvo.srvadmin.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.objects.base.responses.OkResponse;
import com.vk.api.sdk.objects.secure.responses.CheckTokenResponse;
import com.vk.api.sdk.queries.secure.SecureSendNotificationQuery;
import com.vk.api.sdk.streaming.clients.VkStreamingApiClient;

@Service
public class SecureVkApiService {
    
    public void sendLevelUpEvent(int level, int userId) throws Exception {
        HttpTransportClient httpClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(httpClient);

        ServiceActor actor = new ServiceActor(8180902, "3diesn7ViJiMazGJa7my", "c7c21837c7c21837c7c21837fbc7becc91cc7c2c7c21837a534417df3e7eab26d5a63cf");

        OkResponse okResponse = vk.secure().addAppEvent(actor, userId, 1).value(level).execute();
    }

    public void sendProgressMission(int mission, int userId) throws Exception{
        HttpTransportClient httpClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(httpClient);

        ServiceActor actor = new ServiceActor(8180902, "3diesn7ViJiMazGJa7my", "c7c21837c7c21837c7c21837fbc7becc91cc7c2c7c21837a534417df3e7eab26d5a63cf");

        OkResponse okResponse = vk.secure().addAppEvent(actor, userId, mission).execute();
    }

    public void sendNotification(Long userId, String message) throws ApiException, ClientException {
        HttpTransportClient httpClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(httpClient);

        ServiceActor actor = new ServiceActor(8180902, "3diesn7ViJiMazGJa7my", "c7c21837c7c21837c7c21837fbc7becc91cc7c2c7c21837a534417df3e7eab26d5a63cf");
        
        vk.secure().sendNotification(actor, message).execute();
    }
}
