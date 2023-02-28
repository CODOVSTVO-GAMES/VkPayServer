package ru.codovstvo.srvadmin.services;

import org.springframework.stereotype.Service;

import org.json.JSONObject;  

@Service
public class SessionInfoService {
    
    public void main(String reqString){
        JSONObject json = new JSONObject(reqString);  

        System.out.println("----------------------");
        System.out.println("session: " + json.getString("countSeccion"));
        System.out.println("lengthSeccion: " + json.getString("lengthSeccion"));
        System.out.println("----------------------");
    }
}
