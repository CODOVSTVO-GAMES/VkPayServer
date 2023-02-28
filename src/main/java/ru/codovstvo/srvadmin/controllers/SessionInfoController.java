package ru.codovstvo.srvadmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


// import ru.codovstvo.srvadmin.repo.SessionInfoRepo;
import ru.codovstvo.srvadmin.services.CryptoService;
import ru.codovstvo.srvadmin.services.SessionInfoService;


@RestController
@RequestMapping(value = "back/sessioninfo")
public class SessionInfoController {

    // @Autowired
    // SessionInfoRepo SessionInfoRepo;

    @Autowired
    CryptoService cryptoService;

    @Autowired
    SessionInfoService sessionInfoService;

    @PostMapping("taker")
    public ResponseEntity taker(@RequestParam String hash, @RequestBody String requestBody) throws Exception {

        if (!cryptoService.encodeHmac256(requestBody).equals(hash)){// если хеш неверный
            System.out.println("неверный хеш дата контроллер");
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        System.out.println(requestBody);

        sessionInfoService.main(requestBody);

        return new ResponseEntity(HttpStatus.OK);
    }
}
