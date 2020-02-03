package com.dgut.sell.util;

import lombok.Data;

import java.util.Random;


// 生成主键（时间毫秒+随机六位）

public class KeyUtil {
    public static synchronized String genUniqueKey() {
        Random random = new Random();
//        System.currentTimeMillis();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
