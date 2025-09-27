package ru.codovstvo.srvadmin.repo;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.GamerEntity;

public interface GamerRepo extends CrudRepository<GamerEntity, Long>{
    
}
