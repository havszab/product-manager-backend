package com.havszab.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
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
