package ru.codovstvo.srvadmin.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.client.HttpClientErrorException;
import ru.codovstvo.srvadmin.dto.GamerDTO;
import ru.codovstvo.srvadmin.entitys.*;
import ru.codovstvo.srvadmin.repo.SessionsRepo;
import ru.codovstvo.srvadmin.repo.UserEntityRepo;
import ru.codovstvo.srvadmin.services.CryptoService;
import ru.codovstvo.srvadmin.services.EventService;
// import ru.codovstvo.srvadmin.services.SecureVkApiService;
import ru.codovstvo.srvadmin.services.UserService;
import ru.codovstvo.srvadmin.services.VersionService;

@Transactional
@RestController
@RequestMapping(value = "back/events")
public class EventsController {

    @Autowired
    CryptoService cryptoService;

    @Autowired
    EventService eventService;

    @GetMapping("plase")
    public PlaceEntity getPlace(@RequestParam("hash") String hash, @RequestParam long userId) throws Exception {
        if (!CryptoService.encodeHmac256(String.valueOf(userId)).equals(hash)) {// если хеш неверный
            throw new RuntimeException("неверный хеш дата контроллер");
        }

        //получаем всех пользаков в комнате
        //сортируем как хотим
        //отдаем
        return new PlaceEntity();
    }

    @PostMapping("gamer")
    public ResponseEntity setGamerInfo(@RequestParam("hash") String hash, @RequestBody GamerDTO gamer) throws Exception {
        if (!CryptoService.encodeHmac256(gamer.toString()).equals(hash)) {// если хеш неверный
            throw new RuntimeException("неверный хеш дата контроллер");
        }

        //сохраняем инфу о пользаке
        eventService.saveGamer(gamer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("gamer")
    public ResponseEntity<?> getGamerInfo(@RequestParam("hash") String hash, @RequestParam long userId) throws Exception {
        try {
            if (!CryptoService.encodeHmac256(String.valueOf(userId)).equals(hash)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный хеш");
            }
            
            GamerEntity gamer = eventService.getGamer(userId);
            if (gamer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Игрок не найден");
            }
            
            return ResponseEntity.ok(gamer);
        } catch (Exception e) {
            e.printStackTrace(); // Добавьте логирование
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Внутренняя ошибка сервера");
        }
    }

}