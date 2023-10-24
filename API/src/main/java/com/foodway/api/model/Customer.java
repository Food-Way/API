package com.foodway.api.model;

import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
@Table(name = "tbCostumer")
@Entity(name = "costumer")
@EqualsAndHashCode
public class Customer extends User{
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID idCostumer;
    @Column(length = 11, unique = true)
    private String cpf;
    @Column(length = 254)
    private String bio;

    public Customer() {}

    public Customer(RequestUserCustomer customer) {
        super(customer.name(), customer.email(), customer.password(), customer.typeUser(), customer.profilePhoto());
        this.cpf = customer.cpf();
        this.bio = customer.bio();
    }

    public Customer(String name, String email, String password, ETypeUser typeUser, String profilePhoto, String cpf, String bio) {
        super(name, email, password, typeUser, profilePhoto);
        this.cpf = cpf;
        this.bio = bio;
    }

    @Override
    public void update(@NotNull Optional<?> optional) {
        UpdateCustomerData c = (UpdateCustomerData) optional.get();
        System.out.println(c);
        this.setName(c.name());
        this.setEmail(c.email());
        this.setPassword(c.password());
        this.setProfilePhoto(c.profilePhoto());
        this.setCpf(c.cpf());
        this.setBio(c.bio());
    }

    @Override
    public void comment(UUID idUser) {}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    /*
    {
        "name": "string",
        "email": "string",
        "password": "string",
        "ETypeUser": "COSTUMER",
        "profilePhoto": "string",
        "cpf": "string",
        "bio": "string"
    }
    * */






}