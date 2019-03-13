package com.havszab.projectmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class UnitCategory {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

}
