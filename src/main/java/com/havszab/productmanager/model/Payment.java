package com.havszab.productmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private double amount;

    @Column
    private Date date;

    @OneToOne
    private User owner;

    public Payment() {
    }

}
