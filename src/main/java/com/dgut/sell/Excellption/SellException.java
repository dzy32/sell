package com.dgut.sell.Excellption;

import com.dgut.sell.enums.ResultEnums;

public class SellException extends RuntimeException {
    private  Integer code;
    public  SellException(ResultEnums resultEnums){
        super(resultEnums.getMsg());
        this.code=resultEnums.getCode();
    }
    public SellException(Integer code,String  message){
        super(message);
        this.code=code;
    }
}
