package com.dgut.sell.controller;

import com.dgut.sell.config.ProjectUrlConfig;
import com.dgut.sell.constant.CookieConstant;
import com.dgut.sell.constant.RedisConstant;
import com.dgut.sell.dataobject.SellerInfo;
import com.dgut.sell.enums.ResultEnums;
import com.dgut.sell.service.SellerService;
import com.dgut.sell.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author YS
 * @data 2019/12/16 21:37
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "openid") String openid, Map<String,Object> map, HttpServletResponse httpServletResponse){
//        根据openid 匹配用户
        SellerInfo sellerInfo=sellerService.findByOpenId(openid);

//        如果不成功 ，返回错误页面 if
        if(null ==sellerInfo){
            map.put("msg", ResultEnums.LOGIN_ERROR);
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

//        设置token至redis
        String token= UUID.randomUUID().toString();
        Integer expire= RedisConstant.EXPIRE;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);

//        设置token至cookie
        CookieUtil.set(httpServletResponse, CookieConstant.TOKEN,token,CookieConstant.EXPIRE);
        return new ModelAndView("redirect:" + projectUrlConfig.getSellUrl()+ "/sell/seller/order/list" );

    }


    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,Map<String,Object>map){

//        1. 查询cookie
        Cookie cookie = CookieUtil.get(httpServletRequest, CookieConstant.TOKEN);
        if(cookie != null){
            //        2.清除redis
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //        3. 清除cookie
            CookieUtil.set(httpServletResponse, CookieConstant.TOKEN, null, 0);
            map.put("msg", ResultEnums.LOGOUT_SUCCESS);
            map.put("url", "/sell/seller/order/list");

        }
        return new ModelAndView( "common/success", map);

    }


}
