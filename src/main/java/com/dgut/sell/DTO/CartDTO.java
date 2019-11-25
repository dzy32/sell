package com.dgut.sell.DTO;


import lombok.Data;

@Data
public class CartDTO {
    private  String productId;
    private Integer productStock;


    public CartDTO(String productId, Integer productStock) {
        this.productId = productId;
        this.productStock = productStock;
    }
}
