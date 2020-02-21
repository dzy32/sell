package com.dgut.sell.service.impl;

import com.dgut.sell.DTO.OrderDTO;
import com.dgut.sell.config.ProjectUrlConfig;
import com.dgut.sell.config.WechatAccountConfig;
import com.dgut.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author YS
 * @data 2020/2/5 23:01
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage wxMpTemplateMessage=new WxMpTemplateMessage();
        wxMpTemplateMessage.setToUser(orderDTO.getBuyerOpenid());
        wxMpTemplateMessage.setTemplateId(wechatAccountConfig.getTemplateId().get("orderStatus"));
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","微信订单消息测试"),
                new WxMpTemplateData("keyword1","12345678910"),
                new WxMpTemplateData("keyword2",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword3",orderDTO.getOrderStatusEnum().getMsg()),
                new WxMpTemplateData("keyword4","¥" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark","欢迎测试")

        );
        wxMpTemplateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        }catch (WxErrorException e){
            log.error("【微信模板消息】 发送失败，{}",e);
        }
    }
}
