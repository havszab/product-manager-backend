package com.havszab.productmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private ProductCategory productCategory;

    @ManyToOne
    private UnitCategory unitCategory;

    @Column
    private Long quantity;

    @Column
    private Long itemPrice;

    @Transient
    private double unitPrice;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    public Product() {
    }

    public Product(ProductCategory productCategory, UnitCategory unitCategory, Long quantity, Long itemPrice) {
        this.productCategory = productCategory;
        this.unitCategory = unitCategory;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.status = Status.NEW;
    }

    public Product(ProductCategory productCategory, UnitCategory unitCategory, Long quantity, Long itemPrice, String description) {
        this.productCategory = productCategory;
        this.unitCategory = unitCategory;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.description = description;
        this.status = Status.NEW;
    }

    public double getUnitPrice() {
        return (double) this.itemPrice / this.quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productCategory=" + productCategory +
                ", unitCategory=" + unitCategory +
                ", quantity=" + quantity +
                ", itemPrice=" + itemPrice +
                ", unitPrice=" + unitPrice +
                ", description='" + description + '\'' +
                '}';
    }
}
