package com.havszab.productmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class CostPayment {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Cost cost;

    @Column
    private Date date;

    @OneToOne
    private User owner;

    public CostPayment() {
    }

}
