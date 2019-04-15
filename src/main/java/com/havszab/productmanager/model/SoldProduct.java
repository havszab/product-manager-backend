package com.havszab.productmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class SoldProduct extends Product{

    @Column
    private Date sellingDate;

    @Column
    private Long sellingPrice;

    @Column
    private double profit;


    public SoldProduct() {

    }

    public SoldProduct(Date sellingDate, Long sellingPrice, double profit) {
        this.sellingDate = sellingDate;
        this.sellingPrice = sellingPrice;
        this.profit = profit;
    }

    public SoldProduct(ProductCategory productCategory, UnitCategory unitCategory, Long quantity, double itemPrice, Date sellingDate, Long sellingPrice, double profit) {
        super(productCategory, unitCategory, quantity, itemPrice);
        this.sellingDate = sellingDate;
        this.sellingPrice = sellingPrice;
        this.profit = profit;
    }

    public SoldProduct(ProductCategory productCategory, UnitCategory unitCategory, Long quantity, double itemPrice, String description, Date sellingDate, Long sellingPrice, double profit) {
        super(productCategory, unitCategory, quantity, itemPrice, description);
        this.sellingDate = sellingDate;
        this.sellingPrice = sellingPrice;
        this.profit = profit;
    }
}
