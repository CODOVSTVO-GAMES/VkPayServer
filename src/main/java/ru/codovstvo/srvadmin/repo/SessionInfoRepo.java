package ru.codovstvo.srvadmin.repo;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.SessionInfo;

public interface SessionInfoRepo extends CrudRepository<SessionInfo, Long> {
}
