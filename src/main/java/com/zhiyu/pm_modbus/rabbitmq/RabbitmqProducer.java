package com.zhiyu.pm_modbus.rabbitmq;

import com.zhiyu.pm_modbus.constants.QueueConstants;
import com.zhiyu.pm_modbus.util.DateUtil;
import com.zhiyu.pmproject.vo.TransportEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RabbitmqProducer {

        private final RabbitTemplate rabbitTemplate;
        public RabbitmqProducer(RabbitTemplate rabbitTemplate) {
            this.rabbitTemplate = rabbitTemplate;
        }

        public void send(String msg) {
        TransportEntity transportEntity = new TransportEntity();
        transportEntity.setPmMessage(msg);
        String date = DateUtil.dateToString(new Date());
        transportEntity.setReceiveTime(date);
        this.rabbitTemplate.convertAndSend(QueueConstants.NETTY, transportEntity);
    }

}
