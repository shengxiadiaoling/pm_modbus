package com.zhiyu.pm_modbus.util;

import com.zhiyu.pm_modbus.vo.ModBusDeviceVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceNoMapUtil {

    private static final Map<String, String> DEVICE_MAP = new HashMap<>();
    public static void mapper(List<ModBusDeviceVO> list) {
        List<ModBusDeviceVO> rmList = new ArrayList<>();
        list.forEach(vo -> {
            if (vo.getDeviceNo() != null && !"".equals(vo.getDeviceNo())){
                DEVICE_MAP.put(vo.getDeviceIp().trim() + ":" + vo.getPort(),vo.getDeviceNo());
            } else {
                //如果设备号不存在则移出列表
                rmList.add(vo);
            }
        });
        if (rmList.size() > 0)
            list.removeAll(rmList);
    }
    public static void clear() {
        DEVICE_MAP.clear();
    }

    public static String getDeviceNo(String address) {
        return DEVICE_MAP.get(address);
    }

    public static Integer size() {
        return DEVICE_MAP.size();
    }
}
