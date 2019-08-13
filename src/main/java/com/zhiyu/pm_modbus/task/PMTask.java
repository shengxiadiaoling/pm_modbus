package com.zhiyu.pm_modbus.task;

import com.zhiyu.pm_modbus.netty.PMClient;
import com.zhiyu.pm_modbus.util.DeviceNoMapUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时从modbus获取数据
 */
@Component
public class PMTask {
    private PMClient pmClient;

    public PMTask(PMClient pmClient) {
        this.pmClient = pmClient;
    }
    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void getPMData() {
        System.out.println("Task that gets pm data via modbus starts" + new Date());
        /*
         * 1、请求接口获取host port 数据
         * 2、将host和端口封装成map
         */

        pmClient.run("10.10.21.26",502);
        System.out.println("end!");
        DeviceNoMapUtil.DEVICE_MAP.clear();
    }

}
