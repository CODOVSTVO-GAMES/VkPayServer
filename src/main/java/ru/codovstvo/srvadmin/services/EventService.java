package ru.codovstvo.srvadmin.services;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.codovstvo.srvadmin.dto.GamerDTO;
import ru.codovstvo.srvadmin.entitys.GamerEntity;
import ru.codovstvo.srvadmin.repo.EventRepo;
import ru.codovstvo.srvadmin.repo.GamerRepo;

@Transactional
@Service
public class EventService {
    
    @Autowired
    EventRepo eventRepo;

    @Autowired
    GamerRepo gamerRepo;


    public GamerEntity getGamer(long gamerId) throws NotFoundException {
        GamerEntity gamer = gamerRepo.findById(gamerId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return gamer;
    }

    public void saveGamer(GamerDTO dto) {
        GamerEntity gamer = gamerRepo.findById(dto.userId).orElse(new GamerEntity(dto));

        gamerRepo.save(gamer);
    }

    public void generatePlace() {
        // получаем всех пользаков
        // сортируем в нужном порядке
        // сохраняем комнату
    }
    
    public void deletePlaces() {

    }
}
