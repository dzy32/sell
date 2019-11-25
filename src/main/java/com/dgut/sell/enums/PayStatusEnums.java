package com.dgut.sell.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnums {

    WAIT(0,"未支付"),
    SUCCESS(1,"成功"),
    CANCEL(2,"取消"),
    ;
    private  Integer code;
    private  String msg;
    PayStatusEnums(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
