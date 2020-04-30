package com.zhiyu.pm_modbus.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiyu.pm_modbus.netty.PMClient;
import com.zhiyu.pm_modbus.service.InterfaceService;
import com.zhiyu.pm_modbus.util.DeviceNoMapUtil;
import com.zhiyu.pm_modbus.util.HttpClientUtil;
import com.zhiyu.pm_modbus.util.JSONUtil;
import com.zhiyu.pm_modbus.vo.InterfaceJsonResultVO;
import com.zhiyu.pm_modbus.vo.ModBusDeviceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时从modbus获取数据
 */
@Component
public class PMTask {
    private PMClient pmClient;
    private InterfaceService interfaceService;
    private final static Logger log = LoggerFactory.getLogger(PMTask.class);

    public PMTask(PMClient pmClient,
                  InterfaceService interfaceService) {
        this.pmClient = pmClient;
        this.interfaceService = interfaceService;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void getPmData() {
        /*
         * 1、获取所有的modbus设备信息（ip，port）
         * 2、将设备号和ip端口放入map（便于后续通过ip端口获取设备号）
         * 2、获取modbus设备数据
         */
        List<ModBusDeviceVO> devices = interfaceService.getDevices();
        if (devices.size() > 0) {
            if (DeviceNoMapUtil.size() > 3 * devices.size()) {
                /*
                 * 如果缓存的设备过多则清空重新缓存
                 */
                DeviceNoMapUtil.clear();
            }
            DeviceNoMapUtil.mapper(devices);
            pmClient.run(devices);
        }
    }

}
