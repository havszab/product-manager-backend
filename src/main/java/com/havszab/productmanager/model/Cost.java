package com.havszab.productmanager.model;

import com.havszab.productmanager.model.enums.CostType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    @Column
    private double cost;

    @Column
    private Date payedLastDate;

    @OneToOne
    private User owner;

    public Cost(String name, CostType type, Date payedLastDate) {
        this.name = name;
        this.type = type;
        this.payedLastDate = payedLastDate;
    }

    public Cost() {
    }

    public Cost(String name, CostType type, double cost, Date payedLastDate, User owner) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.payedLastDate = payedLastDate;
        this.owner = owner;
    }

    public Cost(String name, CostType type, double cost, User owner) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.owner = owner;
    }
}
