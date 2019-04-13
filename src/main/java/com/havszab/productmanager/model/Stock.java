package com.havszab.productmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Stock {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User owner;

    @OneToMany
    private Set<Product> products;


    public Stock() {
    }

    public Stock(User owner, Set<Product> products) {
        this.owner = owner;
        this.products = products;
    }

    public Stock(User owner) {
        this.owner = owner;
    }
}
