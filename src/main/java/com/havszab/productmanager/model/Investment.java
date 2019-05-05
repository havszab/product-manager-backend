package com.havszab.productmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Investment {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long value;

    @Column
    private String title;

    @Column
    private String description;

    @OneToOne
    private User owner;

    @Column
    private Date acquisitionDate;


    public Investment() {
    }

    public Investment(String title, Long value, String description, User owner, Date date) {
        this.title = title;
        this.value = value;
        this.description = description;
        this.owner = owner;
        this.acquisitionDate = date;
    }
}
