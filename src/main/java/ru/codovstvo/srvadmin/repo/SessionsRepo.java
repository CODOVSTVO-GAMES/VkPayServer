package ru.codovstvo.srvadmin.repo;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.Sessions;
import ru.codovstvo.srvadmin.entitys.UserEntity;

public interface SessionsRepo extends CrudRepository<Sessions, Long> {
    Sessions findByUserAndNumberSession(UserEntity userEntity, int numberSession);
}
