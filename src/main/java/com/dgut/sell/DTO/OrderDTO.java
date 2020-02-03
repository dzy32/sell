package com.dgut.sell.DTO;

import com.dgut.sell.dataobject.OrderDetail;
import com.dgut.sell.enums.OrderStatusEnums;
import com.dgut.sell.enums.PayStatusEnums;
import com.dgut.sell.util.EnumUtil;
import com.dgut.sell.util.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@JsonSerialize(include =JsonSerialize.Inclusion.NON_NULL )
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private String orderId;

    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    //    订单状态 默认0新订单
    private Integer orderStatus = OrderStatusEnums.NEW.getCode();
    //    支付状态 0未支付
    private Integer payStatus = PayStatusEnums.WAIT.getCode();

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList = new ArrayList<>();

    @JsonIgnore
    public OrderStatusEnums getOrderStatusEnum() {
        return EnumUtil.gteByCode(orderStatus, OrderStatusEnums.class);

    }

    @JsonIgnore
    public PayStatusEnums getPayStatusEnum() {
        return EnumUtil.gteByCode(payStatus, PayStatusEnums.class);
    }
}
