package com.github.vole.message.listener;

import com.github.vole.common.constants.MqQueueConstant;
import com.github.vole.message.handler.DingTalkMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听服务状态改变发送请求
 */
@Slf4j
@Component
public class DingTalkServiceChangeReceiveListener {
    @Autowired
    private DingTalkMessageHandler dingTalkMessageHandler;

    @RabbitListener(queues = MqQueueConstant.DINGTALK_SERVICE_STATUS_CHANGE)
    public void receive(String text) {
        long startTime = System.currentTimeMillis();
        log.info("消息中心接收到钉钉发送请求-> 内容：{} ", text);
        dingTalkMessageHandler.process(text);
        long useTime = System.currentTimeMillis() - startTime;
        log.info("调用 钉钉网关处理完毕，耗时 {}毫秒", useTime);
    }
}
