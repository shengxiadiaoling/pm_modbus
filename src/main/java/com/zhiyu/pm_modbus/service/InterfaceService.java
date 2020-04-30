package com.zhiyu.pm_modbus.service;

import com.zhiyu.pm_modbus.vo.ModBusDeviceVO;

import java.util.List;

/**
 * 获取第三方pm数据接口
 */
public interface InterfaceService {
    @Deprecated
    List<String> getInterfaceUrlByType(String type);

    List<ModBusDeviceVO> getDevices();
}
