package de.mVISE.pocService.service;

import de.mVISE.pocService.entity.NameEntity;
import de.mVISE.pocService.repository.NameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NameService {

    @Autowired
    private NameRepository nameRepository;

    public String createNameEntity(NameEntity nameEntity){
        try{
            nameRepository.save(nameEntity);
            return "Name is saved";
        }catch (Exception e){
            return "Name is not Saved";
        }

    }

    public void deleteNameEntityById(Integer id){
        nameRepository.deleteById(id);
    }



    public String updateName(Integer id,String name){
        Optional<NameEntity> nameFindById = nameRepository.findById(id);

        if(name == null){
            return "The name cant be updated...";
        }

        try{

            NameEntity getName =nameFindById.get();
            getName.setName(name);
            nameRepository.save(getName);
            return "The name is updated";
        }catch (Exception e){
            return "The name cant be updated...";
        }


    }

    public List<NameEntity> getNameByName(String name){
        if (name == null) {
            return null;
        }
        List<NameEntity> nameFindByName = nameRepository.findByName(name);
        return nameFindByName;
    }

    public NameEntity getNameById(Integer id){
        if (id == null) {
            return null;
        }
        Optional<NameEntity> nameFindById = nameRepository.findById(id);

        if (nameFindById.isPresent()){
            NameEntity getName =nameFindById.get();
            return getName;
        }

        return null;
    }




}
