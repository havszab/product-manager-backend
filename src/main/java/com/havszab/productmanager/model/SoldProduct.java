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
    private Long profit;


    public SoldProduct() {

    }

    public SoldProduct(Date sellingDate, Long sellingPrice, Long profit) {
        this.sellingDate = sellingDate;
        this.sellingPrice = sellingPrice;
        this.profit = profit;
    }

    public SoldProduct(ProductCategory productCategory, UnitCategory unitCategory, Long quantity, Long itemPrice, Date sellingDate, Long sellingPrice, Long profit) {
        super(productCategory, unitCategory, quantity, itemPrice);
        this.sellingDate = sellingDate;
        this.sellingPrice = sellingPrice;
        this.profit = profit;
    }

    public SoldProduct(ProductCategory productCategory, UnitCategory unitCategory, Long quantity, Long itemPrice, String description, Date sellingDate, Long sellingPrice, Long profit) {
        super(productCategory, unitCategory, quantity, itemPrice, description);
        this.sellingDate = sellingDate;
        this.sellingPrice = sellingPrice;
        this.profit = profit;
    }
}
