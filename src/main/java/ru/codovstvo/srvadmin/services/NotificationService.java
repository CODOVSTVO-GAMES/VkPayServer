package ru.codovstvo.srvadmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.codovstvo.srvadmin.repo.UserDataRepo;

@Transactional
@Service
public class NotificationService {

    @Autowired
    UserDataRepo userDataRepo;

    //Энергия восстановлена
    //фрукты выросли
    //бесплатный сундук уже доступен
    
    
}
