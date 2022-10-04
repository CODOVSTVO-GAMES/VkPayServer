package ru.codovstvo.srvadmin.services;

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

    public Version createOrFindVersion(String versionIdentifier){
        Version version = versionRepo.findByVersionIdentifier(versionIdentifier);
        if (version == null){
            version = new Version(versionIdentifier);
            versionRepo.save(version);

            // Version v = findLastVersion();
            // v.endSession();
            // versionRepo.save(v);
        }

        return version;
    }

    private Version findLastVersion(){
        Iterable<Version> list = versionRepo.findAll();
        long bufferMaxStartValue = 0;
        Version lastVersion = null;
        for (Version v : list){
            if (bufferMaxStartValue < v.getStartDateLong()){
                bufferMaxStartValue = v.getStartDateLong();
                lastVersion = v;
            }
        }
        return lastVersion;
    }
}
