package ru.codovstvo.srvadmin.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.NotificationsBuffer;
import ru.codovstvo.srvadmin.entitys.UserEntity;

public interface NotificationBufferRepo extends CrudRepository<NotificationsBuffer, Long> {
    List<NotificationsBuffer> findAll();
    void deleteAllByUserEntity(UserEntity user);
}
