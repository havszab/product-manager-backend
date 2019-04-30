package com.havszab.productmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class ItemPayment {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Product item;

    @Column
    private Date date;

    @OneToOne
    private User user;


    public ItemPayment() {
    }


    public ItemPayment(Product item, Date date, User user) {
        this.item = item;
        this.date = date;
        this.user = user;
    }
}
