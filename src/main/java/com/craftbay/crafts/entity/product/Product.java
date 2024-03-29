package com.craftbay.crafts.entity.product;


import com.craftbay.crafts.util.enums.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_tbl")
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int remainingQuantity;

    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum category;

    private LocalDate createdDate;
    private LocalDate updateDate;
    @Lob
    private String image;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductBuyingPriceDetails> productBuyingPriceDetails;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductSellingPriceDetails> productSellingPriceDetails;

    private int rate;

    private int noOfRatings;


//    public int getId()
//    {
//        return id;
//    }
}
