package ru.codovstvo.srvadmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.codovstvo.srvadmin.entitys.Event;
import ru.codovstvo.srvadmin.repo.EventRepo;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

@Service
public class EventsService {

    public static String encodeHmac256(String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        String key = "programmistika";
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] hash = sha256_HMAC.doFinal(data.getBytes());
        return DatatypeConverter.printBase64Binary(hash).replace("=", "").replace("/", "").replace("+", "");
    }

} 