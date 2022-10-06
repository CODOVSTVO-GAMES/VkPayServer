package ru.codovstvo.srvadmin.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.UserData;

public interface UserDataRepo extends CrudRepository<UserData, Long> {
    Set<UserData> findByUser(Long user);
    void deleteByUser(Long user);
}
