package com.havszab.projectmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Acquisition {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Set<Product> products;

    @OneToOne
    private User owner;

    public Acquisition() {
    }

    public Acquisition(User owner) {
        this.owner = owner;
    }

}
