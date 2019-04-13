package com.havszab.productmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "product_categories")
public class ProductCategory {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String productName;

    @JsonIgnore
    @OneToMany(mappedBy = "productCategory")
    private List<Product> products;

    public ProductCategory(String productName) {
        this.productName = productName;
    }

    public ProductCategory() {
    }
}
