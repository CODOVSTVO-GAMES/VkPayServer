package ru.codovstvo.srvadmin.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.EventEntity;

public interface EventRepo extends CrudRepository<EventEntity, Long> {
    long countByEventName(String eventName);
    long countByEventNameAndVersion(String eventName, String version);
    Set<EventEntity> findAllByEventName(String eventName);
    Set<EventEntity> findAllByEventNameAndVersion(String eventName, String version);
}