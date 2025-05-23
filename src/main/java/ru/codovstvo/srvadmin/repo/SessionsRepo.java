package ru.codovstvo.srvadmin.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.Sessions1;
import ru.codovstvo.srvadmin.entitys.UserEntity;

public interface SessionsRepo extends CrudRepository<Sessions1, Long> {
    Sessions1 findByUserEntityAndNumberSession(UserEntity userEntity, int numberSession);// подвержено ошибкам
    Set<Sessions1> findAllByUserEntity(UserEntity user);
    
    Set<Sessions1> findAllByNumberSession(int numberSession);

}
