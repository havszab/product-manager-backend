package com.havszab.productmanager.model;

import com.havszab.productmanager.model.enums.ActionColor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class Action {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private Date date;

    @OneToOne
    private User owner;

    @Enumerated(value = EnumType.STRING)
    private ActionColor color;

    public Action(String name, Date date, User owner) {
        this.name = name;
        this.date = date;
        this.owner = owner;
    }

    public Action() {
    }

    public Action(String name, Date date, User owner, ActionColor color) {
        this.name = name;
        this.date = date;
        this.owner = owner;
        this.color = color;
    }
}
