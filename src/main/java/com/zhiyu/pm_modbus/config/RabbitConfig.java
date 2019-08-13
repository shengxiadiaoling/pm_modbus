package com.zhiyu.pm_modbus.config;

import com.zhiyu.pm_modbus.constants.QueueConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue queue() {
        return new Queue(QueueConstants.NETTY,true);
    }
}
