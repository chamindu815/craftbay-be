package com.craftbay.crafts.dto.product;

import com.craftbay.crafts.util.enums.ProductCategoryEnum;
import lombok.Data;

import javax.persistence.Lob;
import java.util.List;

@Data
public class AdminProductResponseDto {
    private int id;
    private String name;
    private String description;
    private ProductCategoryEnum category;
    private int remainingQuantity;

    private List<AdminProductBuyingPriceDetailsDto> adminProductBuyingPriceDetailsDtos;
    private List<AdminProductSellingPriceDetailsDto> adminProductSellingPriceDetailsDtos;

    private int rate;
    private int noOfRatings;

    @Lob
    private String image;
    public int getId()
    {
        return id;
    }


}
