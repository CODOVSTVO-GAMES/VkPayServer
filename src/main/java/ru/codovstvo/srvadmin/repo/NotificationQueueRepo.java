// package ru.codovstvo.srvadmin.repo;

// import java.util.HashSet;

// import org.springframework.data.repository.CrudRepository;

// import ru.codovstvo.srvadmin.entitys.NotificationQueue;
// import ru.codovstvo.srvadmin.entitys.NotificationType;

// public interface NotificationQueueRepo extends CrudRepository<NotificationQueue, Long>{
//     NotificationQueue findByUserIdAndNotificationType(Long userId, NotificationType notificationType);
//     void deleteByUserIdAndNotificationType(Long userId, NotificationType notificationType);
//     void deleteAllByUserIdAndNotificationType(Long userId, NotificationType notificationType);
//     HashSet findAllByNotificationType(NotificationType notificationType);
// }
