package com.havszab.projectmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private ProductCategory productCategory;

    @Column
    private UnitCategory unitCategory;

    @Column
    private Long quantity;

    @Column
    private Long itemPrice;

    @Transient
    private double unitPrice;

    public Product() {
    }

    public Product(ProductCategory productCategory, UnitCategory unitCategory, Long quantity, Long itemPrice, double unitPrice) {
        this.productCategory = productCategory;
        this.unitCategory = unitCategory;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.unitPrice = unitPrice;
    }

}
