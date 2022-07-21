package ru.codovstvo.srvadmin.repo;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.EventErrors;

public interface EventErrorRepo extends CrudRepository<EventErrors, Long> {
}