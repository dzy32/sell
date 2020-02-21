package com.dgut.sell.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {


    private static final long serialVersionUID = -6419892721445030370L;
    private Integer code;

    private String msg = "";

    private T data;
}
