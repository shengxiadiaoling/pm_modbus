package com.zhiyu.pm_modbus.task;

import com.zhiyu.pm_modbus.netty.PMClient;
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
        pmClient.run("10.10.21.26",502);

    }

}
