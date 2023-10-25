package de.mVISE.pocService.service;

import de.mVISE.pocService.entity.NameEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NameServiceTest {

    @Autowired
    private NameService nameService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createNameEntity() {
        nameService.createNameEntity(new NameEntity(2222,"Lanesra"));

        NameEntity getNameById = nameService.getNameById(2222);
        Assertions.assertThat(getNameById).isNotNull();
        Assertions.assertThat(getNameById.getName()).isEqualTo("Lanesra");

        nameService.deleteNameEntityById(2222);
    }

    @Test
    void createNameEntity_nullParameter_notSaved() {
        String createName = nameService.createNameEntity(null);

        Assertions.assertThat(createName).isEqualTo("Name is not Saved");
    }

    @Test
    void createNameEntity_wrongParameter_notSaved() {
        String createName = nameService.createNameEntity(new NameEntity(null,"asdasd"));

        Assertions.assertThat(createName).isEqualTo("Name is not Saved");
    }

    @Test
    void deleteNameEntityById_createAndDeleteName_success() {
        nameService.createNameEntity(new NameEntity(2224,"Monica"));
        NameEntity nameEntity = nameService.getNameById(2224);
        Assertions.assertThat(nameEntity.getName()).isEqualTo("Monica");
        Assertions.assertThat(nameEntity).isNotNull();

        nameService.deleteNameEntityById(2224);

        NameEntity nameDeletedEntity = nameService.getNameById(2224);


        Assertions.assertThat(nameDeletedEntity).isNull();
    }

    @Test
    void updateName_createAndUpdate_success() {

        nameService.createNameEntity(new NameEntity(2225,"Sara"));
        NameEntity nameEntity = nameService.getNameById(2225);

        Assertions.assertThat(nameEntity.getName()).isEqualTo("Sara");

        String updateNameMessage=nameService.updateName(2225,"Marie");
        Assertions.assertThat(updateNameMessage).isEqualTo("The name is updated");

        NameEntity getUpdatedNameEntity = nameService.getNameById(2225);
        Assertions.assertThat(getUpdatedNameEntity.getName()).isEqualTo("Marie");

        nameService.deleteNameEntityById(2225);
    }

    @Test
    void updateName_idNotExist_massage(){
        nameService.createNameEntity(new NameEntity(2226,"Nicol"));
        NameEntity nameEntity = nameService.getNameById(2226);
        Assertions.assertThat(nameEntity.getName()).isEqualTo("Nicol");

        String updateNameMessage = nameService.updateName(2226,null);

        Assertions.assertThat(updateNameMessage).isEqualTo("The name cant be updated...");

        nameService.deleteNameEntityById(2226);
    }

    @Test
    void getNameByName_emptyListOfNames_success() {

        List<NameEntity> name = nameService.getNameByName("Mark Zuckerberg");

        Assertions.assertThat(name).isNotNull();
        Assertions.assertThat(name).isEmpty();
    }

    @Test
    void getNameByName_nullParameter_null(){
        List<NameEntity> name = nameService.getNameByName(null);

        Assertions.assertThat(name).isNull();
    }

    @Test
    void getNameById_nameNotEmptyOrNull() {
        nameService.createNameEntity(new NameEntity(2222,"Lanesra"));

        NameEntity getNameById = nameService.getNameById(2222);
        Assertions.assertThat(getNameById).isNotNull();
        Assertions.assertThat(getNameById.getName()).isNotEmpty();

        nameService.deleteNameEntityById(2222);
    }

    @Test
    void getNameById_idNotExist_null(){
        NameEntity getNameById = nameService.getNameById(2223);

        Assertions.assertThat(getNameById).isNull();
    }

    @Test
    void getNameById_idNull_null(){
        NameEntity nameEntity = nameService.getNameById(null);

        Assertions.assertThat(nameEntity).isNull();
    }

    @Test
    void getAllNames() {
        List<NameEntity> getAllNames = nameService.getAllNames();

        Assertions.assertThat(getAllNames).isNotNull();


    }
}