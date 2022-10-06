package ru.codovstvo.srvadmin.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.UserData;
import ru.codovstvo.srvadmin.entitys.UserEntity;

public interface UserDataRepo extends CrudRepository<UserData, Long> {
    Set<UserData> findByUserEntity(UserEntity user);
    void deleteByUserEntity(UserEntity user);
}
