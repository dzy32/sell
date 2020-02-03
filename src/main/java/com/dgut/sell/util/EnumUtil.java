package com.dgut.sell.util;

import com.dgut.sell.enums.CodeEnum;

public class EnumUtil {
    public static <T extends CodeEnum> T gteByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
