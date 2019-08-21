package com.zhiyu.pm_modbus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
@MapperScan("com.zhiyu.pm_modbus.mapper")
public class PmModbusApplication {

    public static void main(String[] args) {
        SpringApplication.run(PmModbusApplication.class, args);
    }

}
