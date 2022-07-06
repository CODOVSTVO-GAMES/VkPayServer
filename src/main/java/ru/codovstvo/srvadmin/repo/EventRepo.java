package ru.codovstvo.srvadmin.repo;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.Event;

public interface EventRepo extends CrudRepository<Event, Long> {
}