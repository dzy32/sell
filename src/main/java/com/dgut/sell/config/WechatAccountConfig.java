package com.dgut.sell.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    private String mpAppId;

    private String mpAppSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 支付完成后的异步通知地址.
     */
    private String notifyUrl;
    /**
     * 扫码登录的openId
     */
    private  String openAppId;
    /**
     * 扫码登录的openAppSecret
     */
    private String openAppSecret;
    /**
     * 模板消息Id
     */
    private Map<String,String> templateId;
}
