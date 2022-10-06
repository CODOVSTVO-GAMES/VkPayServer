package ru.codovstvo.srvadmin.repo;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.Sessions1;
import ru.codovstvo.srvadmin.entitys.UserEntity;

public interface SessionsRepo extends CrudRepository<Sessions1, Long> {
    Sessions1 findByUserAndNumberSession(UserEntity userEntity, int numberSession);
}
