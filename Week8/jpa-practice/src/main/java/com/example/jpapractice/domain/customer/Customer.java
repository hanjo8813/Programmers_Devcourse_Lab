package com.example.jpapractice.domain.customer;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer{
    @Id
    private long id;
    private String firstName;
    private String lastName;
}
