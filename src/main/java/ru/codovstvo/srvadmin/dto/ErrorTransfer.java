package ru.codovstvo.srvadmin.dto;

import java.util.Map;

import lombok.Data;

@Data
public class ErrorTransfer {
    private Map error;
    
    // private int error_code;
    // private String error_msg;
    // private String critical;

}