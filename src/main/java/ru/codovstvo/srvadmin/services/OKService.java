package ru.codovstvo.srvadmin.services;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OKService {
    
    @Autowired
    CryptoService cryptoService;

    private final String OKDOMEN = "https://api.ok.ru/fb.do?";
    
    public void sendNotification(){
        String staticParams = "application_key=CLBMKLKGDIHBABABA&format=json&mark=simpl&method=notifications.sendSimple";
        String userId = "&uid=580946266481";
        String textNotification = "&text=HelloWorld";
        String secret = "FF11CBFD2AB27E5ABD7BC18D";
        String sig = "";

        String sigBuilder = (staticParams + textNotification + userId + secret).replace("&", "").replace("?", "");
        try{
            sig = "&sig=" + cryptoService.getMd5Hash(sigBuilder);
        }catch (NoSuchAlgorithmException e){
            System.out.println("Бобик сдох");
        }
        
        
        System.out.println(sig);

        System.out.println(OKDOMEN + staticParams + textNotification + userId + sig);
        
        try{
            URL url = new URL(OKDOMEN + staticParams + textNotification + userId + sig);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeUTF("");
            wr.close();
        }
        catch (IOException e){
            System.out.println("ошибка IOException");
        }

    }

}
