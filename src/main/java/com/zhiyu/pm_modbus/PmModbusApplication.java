package com.zhiyu.pm_modbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class PmModbusApplication {

    public static void main(String[] args) {
        SpringApplication.run(PmModbusApplication.class, args);
    }

}
