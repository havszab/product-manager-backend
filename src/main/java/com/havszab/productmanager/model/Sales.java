package com.havszab.productmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Sales {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Set<SoldProduct> products;

    @OneToOne
    private User owner;

    public Sales(Set<SoldProduct> products, User owner) {
        this.products = products;
        this.owner = owner;
    }

    public Sales(User owner) {
        this.owner = owner;
    }

    public Sales() {

    }
}
