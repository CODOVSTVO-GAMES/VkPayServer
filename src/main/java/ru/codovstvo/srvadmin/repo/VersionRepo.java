package ru.codovstvo.srvadmin.repo;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.Version;

public interface VersionRepo extends CrudRepository<Version, Long> {
    Version findByVersionIdentifier(String versionIdentifier);
}
