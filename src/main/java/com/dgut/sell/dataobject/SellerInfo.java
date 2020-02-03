package com.dgut.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SellerInfo {

    @Id
    private  String sellerId;

    private  String password;
    private String username;
    private String openid;

}
