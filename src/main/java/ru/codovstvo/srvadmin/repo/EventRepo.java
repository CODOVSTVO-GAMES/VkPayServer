package ru.codovstvo.srvadmin.repo;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.EventEntity;
import ru.codovstvo.srvadmin.entitys.UserEntity;

public interface EventRepo extends CrudRepository<EventEntity, Long> {
    long countByEventName(String eventName);
    long countByEventNameAndVersion(String eventName, String version);

    Optional<EventEntity> findByUserEntityAndEventName(UserEntity userEntity, String eventName);

    Set<EventEntity> findAllByEventName(String eventName);
    Set<EventEntity> findAllByEventNameAndVersion(String eventName, String version);
    void deleteAllByUserEntity(UserEntity userEntity);
}