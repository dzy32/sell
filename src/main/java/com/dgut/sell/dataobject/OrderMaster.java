package com.dgut.sell.dataobject;

import com.dgut.sell.enums.OrderStatusEnums;
import com.dgut.sell.enums.PayStatusEnums;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
    private  String orderId;

    private  String buyerName;
    private  String buyerPhone;
    private  String buyerAddress;
    private  String buyerOpenid;
    private  BigDecimal orderAmount;
//    订单状态 默认0新订单
    private  Integer orderStatus= OrderStatusEnums.NEW.getCode();
//    支付状态 0未支付
    private  Integer payStatus= PayStatusEnums.WAIT.getCode();

    private Date createTime;
    private Date updateTime;
//    @Transient
//    private List<OrderDetail> orderDetailList;
}
