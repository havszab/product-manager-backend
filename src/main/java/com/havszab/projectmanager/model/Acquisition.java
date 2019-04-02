package com.havszab.projectmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Acquisition {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "acquisition")
    private List<Product> products;

    @OneToOne
    private User owner;
}
