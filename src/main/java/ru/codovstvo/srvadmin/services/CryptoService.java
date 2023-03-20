package ru.codovstvo.srvadmin.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

@Transactional
@Service
public class CryptoService {
    
    public static String encodeHmac256(String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        String key = "programmistika";
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] hash = sha256_HMAC.doFinal(data.getBytes());
        return DatatypeConverter.printBase64Binary(hash).replace("=", "").replace("/", "").replace("+", "");
    }

    public static String getMd5Hash(String inputString) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(inputString.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
