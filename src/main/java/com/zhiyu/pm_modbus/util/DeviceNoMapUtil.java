package com.zhiyu.pm_modbus.util;

import com.zhiyu.pm_modbus.vo.InterfaceJsonResultVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceNoMapUtil {

    private static final Map<String, String> DEVICE_MAP = new HashMap<>();
    public static void mapper(List<InterfaceJsonResultVO> list) {
        //重复出现的ip端口
        List<InterfaceJsonResultVO> repetitiveList = new ArrayList<>();
        list.forEach(vo -> {
            //排除重复数据
            if (DEVICE_MAP.containsKey(vo.getDeviceIp().trim() + ":" + vo.getDevicePort())) {
                repetitiveList.add(vo);
            }
            if (vo.getDeviceKey() != null && !"".equals(vo.getDeviceKey())){
                DEVICE_MAP.put(vo.getDeviceIp().trim() + ":" + vo.getDevicePort(),vo.getDeviceKey());
            } else {//如果设备key不存在则使用ip作为key
                DEVICE_MAP.put(vo.getDeviceIp().trim() + ":" + vo.getDevicePort(),vo.getDeviceIp());
            }
        });
        //排除重复数据
        if (repetitiveList.size() > 0) {
            list.removeAll(repetitiveList);
        }
    }
    public static void clear() {
        DEVICE_MAP.clear();
    }

    public static String getDeviceNo(String address) {
        return DEVICE_MAP.get(address);
    }
}
