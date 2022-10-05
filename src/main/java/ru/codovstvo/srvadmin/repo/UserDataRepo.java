package ru.codovstvo.srvadmin.repo;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.UserData;
import ru.codovstvo.srvadmin.entitys.UserEntity;

public interface UserDataRepo extends CrudRepository<UserData, Long> {
    UserData findByUserAndTitle(UserEntity user, String title);
    int countByUserIdAndTitle(int userId, String title);
}
