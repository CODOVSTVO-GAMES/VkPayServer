package ru.codovstvo.srvadmin.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.Event;

public interface EventRepo extends CrudRepository<Event, Long> {
    long countByEventName(String eventName);
    long countByEventNameAndVersion(String eventName, String version);
    List<String> findAllLoadTime();
}