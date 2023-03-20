package ru.codovstvo.srvadmin.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public void sendNotification(String textNotification, Long userId){
        sendNotification(textNotification, userId.toString());
    }

    public void sendNotification(String textNotification, Integer userId){
        sendNotification(textNotification, userId.toString());
    }
    
    public void sendNotification(String textNotification, String userId){
        String staticParams = "application_key=CLBMKLKGDIHBABABA&format=json&mark=simpl&method=notifications.sendSimple";
        String user = "&uid=" + userId;
        String text = "&text=" + textNotification;
        String secret = "FF11CBFD2AB27E5ABD7BC18D";
        String sig = "";

        String sigBuilder = (staticParams + text + user + secret).replace("&", "").replace("?", "");
        try{
            sig = "&sig=" + cryptoService.getMd5Hash(sigBuilder);
        }catch (NoSuchAlgorithmException e){
            System.out.println("ошибка NoSuchAlgorithmException");
        }
        
        
        try{
            URL url = new URL(OKDOMEN + staticParams + text + user + sig);
            System.out.println(url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            // wr.writeUTF("");
            wr.close();

            int resCode = connection.getResponseCode();
            System.out.println(resCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            StringBuffer response = new StringBuffer();
            
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());

        }
        catch (IOException e){
            System.out.println("ошибка IOException");
        }

    }

}
