package ru.codovstvo.srvadmin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.codovstvo.srvadmin.entitys.Version;
import ru.codovstvo.srvadmin.repo.VersionRepo;

@Transactional
@Service
public class VersionService {
     
    @Autowired
    VersionRepo versionRepo;

    public Version createOrFindVersion(String versionIdentifier, String platform){
        List<Version> versions = versionRepo.findAllByVersionIdentifierAndPlatform(versionIdentifier, platform);
        if (versions.isEmpty() || versions == null){
            Version version = new Version(versionIdentifier, platform);
            versionRepo.save(version);
            System.out.println("Создана новая версия: " + versionIdentifier + ", platform: " + platform);
            return version;
        }

        // for(Version v : versions){
        //     if (v == versions.get(0)){
        //         versionRepo.delete(v);
        //         //залогировать
        //     }
        // }
        return versions.get(0);
    }
}
