package ru.codovstvo.srvadmin.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.UserEntity;

public interface UserEntityRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findByPlatformUserId(String platformUserId);
    List<UserEntity> findAllByActive(Boolean active);
}
