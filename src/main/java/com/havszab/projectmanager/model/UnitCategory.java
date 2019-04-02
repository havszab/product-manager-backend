package com.havszab.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class UnitCategory {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String unitName;

    @JsonIgnore
    @OneToMany(mappedBy = "unitCategory")
    private List<Product> products;

    public UnitCategory(String unitName) {
        this.unitName = unitName;
    }

    public UnitCategory() {
    }
}
