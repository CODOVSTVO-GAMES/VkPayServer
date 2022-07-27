package ru.codovstvo.srvadmin.repo;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.UserData;


public interface UserDataRepo extends CrudRepository<UserData, Long> {
    
}
