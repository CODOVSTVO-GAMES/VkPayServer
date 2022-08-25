package ru.codovstvo.srvadmin.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.Event;

public interface EventRepo extends CrudRepository<Event, Long> {
    long countByEventName(String eventName);
    long countByEventNameAndVersion(String eventName, String version);
    Set<Event> findAllByEventName(String eventName);
    Set<Event> findAllByEventNameAndVersion(String eventName, String version);
}