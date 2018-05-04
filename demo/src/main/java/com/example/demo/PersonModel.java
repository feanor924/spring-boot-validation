package com.example.demo;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Created by canerbakar on 27.04.2018.
 */

@Entity
@Table(name = "person")
public class PersonModel {

    @Id @GeneratedValue
    @JsonView(View.Summary.class)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min=2, message="Name should have at least 2 characters")
    @Size(max = 20,message = "Name should not be larger than 20 characters")
    @Column(name = "name")
    @JsonView(View.Summary.class)
    private String name;

    @NotNull
    @NotEmpty
    @Email(message="{register.email.invalid}")
    @Column(name = "email")
    @JsonView(View.Summary.class)
    private String email;

    public PersonModel(){}

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public PersonModel(String name, String email){
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format(
                "Person[id=%d, firstName='%s']",
                id, name);
    }





}
