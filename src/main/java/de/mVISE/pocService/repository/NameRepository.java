package de.mVISE.pocService.repository;

import de.mVISE.pocService.entity.NameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NameRepository extends CrudRepository<NameEntity,Integer> {

    public List<NameEntity> findByName(String name);
}
