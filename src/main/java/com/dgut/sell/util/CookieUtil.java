package com.dgut.sell.util;


import org.springframework.boot.autoconfigure.web.ServerProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YS
 * @data 2019/12/17 9:21
 */
public class CookieUtil {


    /**
    * @Description:
    * @Param:
    * @return:
    * @Author: ys
    * @Date: 2019/12/17
    */
    public static void set(HttpServletResponse httpServletResponse, String name, String value, Integer maxAge){
        Cookie cookie=new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        httpServletResponse.addCookie(cookie);
    }

    public static Cookie get(HttpServletRequest request , String name){

        Map<String, Cookie> cookieMap = readCookie(request);
        if(cookieMap.containsKey(name)){
            return  cookieMap.get(name);
        }
        else {
            return null;
        }
    }

    private  static Map<String, Cookie> readCookie(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return  cookieMap;
    }
}
