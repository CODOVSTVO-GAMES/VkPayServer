package ru.codovstvo.srvadmin.repo;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.Item;

public interface ItemRepo extends CrudRepository<Item, Long> {
    Item findByTitle(String title);
}