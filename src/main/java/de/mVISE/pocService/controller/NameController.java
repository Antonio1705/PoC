package de.mVISE.pocService.controller;

import de.mVISE.pocService.entity.NameEntity;
import de.mVISE.pocService.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class NameController {

    @Autowired
    NameService nameService;

    @GetMapping("/name/name/{name}")
    public ResponseEntity<List<NameEntity>> getNameByName(@PathVariable String name){
        return new ResponseEntity<>(nameService.getNameByName(name), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<NameEntity>> getAllNames(){
        return new ResponseEntity<>(nameService.getAllNames(), HttpStatus.OK);
    }

    @GetMapping("/name/id/{id}")
    public ResponseEntity<NameEntity> getNameById(@PathVariable Integer id){
        return new ResponseEntity<>(nameService.getNameById(id), HttpStatus.OK);
    }

    @PostMapping("/name")
    public ResponseEntity<String> createName(@RequestBody NameEntity nameEntity){
        return new ResponseEntity<>(nameService.createNameEntity(nameEntity),HttpStatus.OK);
    }

    @PutMapping("/name/id/{id}")
    public ResponseEntity<String> updateNameById(@PathVariable Integer id, @RequestBody NameEntity nameEntity){
        return new ResponseEntity<>(nameService.updateName(id,nameEntity.getName()), HttpStatus.OK);

    }

    @DeleteMapping("/name/id/{id}")
    public ResponseEntity<HttpStatus> deleteNameById(@PathVariable Integer id){
        nameService.deleteNameEntityById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
