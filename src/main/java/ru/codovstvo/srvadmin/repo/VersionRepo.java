package ru.codovstvo.srvadmin.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ru.codovstvo.srvadmin.entitys.Version;

public interface VersionRepo extends CrudRepository<Version, Long> {
    List<Version> findAllByVersionIdentifierAndPlatform(String versionIdentifier, String platform);
    Version findByVersionIdentifierAndPlatform(String versionIdentifier, String platform);
}
