package ru.codovstvo.srvadmin.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.UserEntity;

public interface UserEntityRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findByPlatformUserId(String platformUserId);
    Set<UserEntity> findAllByPlatformUserId(String platformUserId);
    List<UserEntity> findAllByActive(Boolean active);

    Set<UserEntity> findAllBySessionCounter(int sessionCounter);

    Set<UserEntity> findAllBySessionCounterAndDeviceType(int sessionCounter, String deviceType);
}
