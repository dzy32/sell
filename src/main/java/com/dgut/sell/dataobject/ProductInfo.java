package com.dgut.sell.dataobject;

import com.dgut.sell.enums.OrderStatusEnums;
import com.dgut.sell.enums.ProductStatusEnums;
import com.dgut.sell.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class ProductInfo implements Serializable {


    private static final long serialVersionUID = 5196472860764327159L;
    @Id
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock;
    private String productDescription;
    private String productIcon;
    private Integer productStatus=ProductStatusEnums.UP.getCode();
    private Integer categoryType;
    private Date createTime;
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnums getProductStatusEnum() {
        return EnumUtil.gteByCode(productStatus, ProductStatusEnums.class);

    }
}
