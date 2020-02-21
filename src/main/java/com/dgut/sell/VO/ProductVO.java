package com.dgut.sell.VO;

import com.dgut.sell.dataobject.ProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductVO  implements Serializable {


    private static final long serialVersionUID = -221247305428804539L;
    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
