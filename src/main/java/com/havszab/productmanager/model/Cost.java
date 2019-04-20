package com.havszab.productmanager.model;

import com.havszab.productmanager.model.enums.CostType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Cost {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Enumerated(value = EnumType.STRING)
    private CostType type;

    @OneToOne
    private User owner;

    public Cost(String name, CostType type) {
        this.name = name;
        this.type = type;
    }

    public Cost() {
    }

}
