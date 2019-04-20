package com.havszab.productmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private double salary;

    @Column
    private String phone;

    @Column
    private String position;

    @OneToOne
    private User owner;

    public Employee() {
    }


    public Employee(String firstName, String lastName, String email, double salary, String phone, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.phone = phone;
        this.position = position;
    }

    public Employee(String firstName, String lastName, String email, double salary, String phone, String position, User owner) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.phone = phone;
        this.position = position;
        this.owner = owner;
    }
}
