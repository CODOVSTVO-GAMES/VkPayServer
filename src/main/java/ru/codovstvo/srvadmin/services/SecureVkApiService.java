package ru.codovstvo.srvadmin.services;

import org.springframework.stereotype.Service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.objects.base.responses.OkResponse;
import com.vk.api.sdk.objects.secure.responses.CheckTokenResponse;

@Service
public class SecureVkApiService {
    
    public void test() throws Exception {
        
        HttpTransportClient httpClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(httpClient);

        ServiceClientCredentialsFlowResponse authResponse = vk.oAuth().serviceClientCredentialsFlow(8180902,"3diesn7ViJiMazGJa7my").execute();

        ServiceActor actor = new ServiceActor(8180902, authResponse.getAccessToken());
        System.out.println(actor.getAccessToken());

        // OkResponse okResponse = vk.secure().addAppEvent(actor, 77517618, 1).value(2).execute();
    }
}
