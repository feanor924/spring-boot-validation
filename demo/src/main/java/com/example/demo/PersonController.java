package com.example.demo;


import com.fasterxml.jackson.annotation.JsonView;
import org.aspectj.weaver.Iterators;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by canerbakar on 27.04.2018.
 */
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @JsonView(View.Summary.class)
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<PersonModel> findAll_(){

        Iterator<PersonModel> person = personRepository.findAll().iterator();

        List<PersonModel> list = new ArrayList<>();
        person.forEachRemaining(list::add);

        if ( list.size() == 0)
            throw new ResourceError("Persons are not found");

        return list;

    }

    @JsonView(View.Summary.class)
    @RequestMapping(value = "/findbyid/{id}", method = RequestMethod.GET)
    public Optional<PersonModel> findById(@PathVariable(value = "id") Long id){

        Optional<PersonModel> temp = personRepository.findById(id);

        if ( !temp.isPresent())
            throw new ResourceError("Person not found");

        return temp;
    }

    @JsonView(View.Summary.class)
    @RequestMapping(value = "/findbyname/{name}",method = RequestMethod.GET)
    public List<PersonModel> fetchDataByName(@PathVariable(value = "name") String name){

        List<PersonModel> temp = personRepository.findByName(name);

        if ( temp.size() == 0 ){
            throw new ResourceError("Person not found");
        }

        return temp;

    }

    @JsonView(View.Summary.class)
    @RequestMapping(value = "/findbyemail/{email}",method = RequestMethod.GET)
    public List<PersonModel> fetchDataByEmail(@PathVariable(value = "email") String email){

        List<PersonModel> temp = personRepository.findByEmail(email);

        if ( temp.size() == 0 ){
            throw new ResourceError("Person not found");
        }

        return temp;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map<String,Object> process(@Valid @RequestBody PersonModel model, BindingResult result){

        Map<String, Object> fieldError = new HashMap<>();

        List<FieldError> lists = result.getFieldErrors();

        if ( lists.size() != 0) {
            for (FieldError f1 : lists) {
                fieldError.put(f1.getField(), f1.getDefaultMessage());
            }
        }
        else{
            personRepository.save(model);
        }


        return fieldError;
    }




}
