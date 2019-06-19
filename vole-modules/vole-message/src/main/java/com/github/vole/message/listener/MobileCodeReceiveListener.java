package com.github.vole.message.listener;

import com.github.vole.common.constants.MqQueueConstant;
import com.github.vole.common.fs.sms.MobileMsgTemplate;
import com.github.vole.message.handler.SmsMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 监听短信发送请求
 */
@Slf4j
@Component
public class MobileCodeReceiveListener {
    @Autowired
    private Map<String, SmsMessageHandler> messageHandlerMap;

    @RabbitListener(queues = MqQueueConstant.MOBILE_CODE_QUEUE)
    public void receive(MobileMsgTemplate mobileMsgTemplate) {
        long startTime = System.currentTimeMillis();
        log.info("消息中心接收到短信发送请求-> 手机号：{} -> 验证码: {} ", mobileMsgTemplate.getMobile(), mobileMsgTemplate.getContext());
        String channel = mobileMsgTemplate.getChannel();
        SmsMessageHandler messageHandler = messageHandlerMap.get(channel);
        if (messageHandler == null) {
            log.error("没有找到指定的路由通道，不进行发送处理完毕！");
            return;
        }

        messageHandler.execute(mobileMsgTemplate);
        long useTime = System.currentTimeMillis() - startTime;
        log.info("调用 {} 短信网关处理完毕，耗时 {}毫秒", channel, useTime);
    }
}
