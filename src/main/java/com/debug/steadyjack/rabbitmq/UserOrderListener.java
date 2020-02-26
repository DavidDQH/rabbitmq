package com.debug.steadyjack.rabbitmq;

import com.debug.steadyjack.dto.UserOrderDto;
import com.debug.steadyjack.entity.UserOrder;
import com.debug.steadyjack.mapper.UserOrderMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/30.
 */
@Component("userOrderListener")
public class UserOrderListener implements ChannelAwareMessageListener{

    private static final Logger log= LoggerFactory.getLogger(UserOrderListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag=message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body=message.getBody();
            UserOrderDto entity=objectMapper.readValue(body, UserOrderDto.class);
            log.info("用户商城下单监听到消息： {} ",entity);

            UserOrder userOrder=new UserOrder();
            BeanUtils.copyProperties(entity,userOrder);
            userOrder.setStatus(1);
            userOrder.setCreateTime(new Date());
            userOrderMapper.insertSelective(userOrder);

            channel.basicAck(tag,true);
        }catch (Exception e){
            log.error("用户商城下单 发生异常：",e.fillInStackTrace());
            channel.basicReject(tag,false);
        }
    }
}




























