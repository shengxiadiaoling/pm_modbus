package com.zhiyu.pm_modbus.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiyu.pm_modbus.netty.PMClient;
import com.zhiyu.pm_modbus.service.InterfaceService;
import com.zhiyu.pm_modbus.util.DeviceNoMapUtil;
import com.zhiyu.pm_modbus.util.HttpClientUtil;
import com.zhiyu.pm_modbus.util.JSONUtil;
import com.zhiyu.pm_modbus.vo.InterfaceJsonResultVO;
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
    private HttpClientUtil httpClientUtil;
    private InterfaceService interfaceService;

    public PMTask(PMClient pmClient,
                  HttpClientUtil httpClientUtil,
                  InterfaceService interfaceService) {
        this.pmClient = pmClient;
        this.httpClientUtil = httpClientUtil;
        this.interfaceService = interfaceService;
    }
    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void getPMData() {
        System.out.println("Task that gets pm data via modbus starts" + new Date());
        /*
         * 1、获取蓝天卫士接口地址列表
         * 2、请求蓝天卫士接口获得modbus设备接口和ip列表
         * 3、请求接口获取host port 数据
         * 4、将host和端口封装成map
         */
        List<String> modbus = interfaceService.getInterfaceUrlByType("modbus");
        List<InterfaceJsonResultVO> list = new ArrayList<>();
        modbus.forEach(url -> {
            String result = httpClientUtil.doRequest(url, null, "post");
            List<InterfaceJsonResultVO> results = JSONUtil.basicParse(result, "success", InterfaceJsonResultVO.class);
            if (results != null) {
                list.addAll(results);
            }
        });
        //临时存储每个设备与设备号的对应map
        DeviceNoMapUtil.clear();
        DeviceNoMapUtil.mapper(list);
        if (list.size() > 0 ) {
            pmClient.run(list);
        }
        System.out.println("end!");
        //释放临时存储的map资源
        /*List<InterfaceJsonResultVO> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceJsonResultVO vo1 = new InterfaceJsonResultVO();
            vo1.setDevicePort(9163);
            vo1.setDeviceIp("127.0.0.1");
            list.add(vo1);
        }
        pmClient.run(list);*/
    }

}
