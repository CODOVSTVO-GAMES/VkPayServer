package ru.codovstvo.srvadmin.services;

import java.util.HashMap;
import java.util.Map;

import ru.codovstvo.srvadmin.dto.ErrorTransfer;
import ru.codovstvo.srvadmin.dto.ResponseTransfer;

public class PaymentsService {
    
    public ResponseTransfer response() {
        ResponseTransfer responseTransfer = new ResponseTransfer();



        return responseTransfer;
    }

    public ErrorTransfer error(int errorCode) {
        ErrorTransfer errorTransfer = new ErrorTransfer();
        Map response = new HashMap<>();
        if (errorCode == 1) {
            response.put("error_code", "1");
            response.put("error_msg", "Событие не обрабатывается сервером");
            response.put("critical", true);
        }
        return errorTransfer;
    }
}