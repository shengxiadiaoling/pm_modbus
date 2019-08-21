package com.zhiyu.pm_modbus.service;

import java.util.List;

/**
 * 获取第三方pm数据接口
 */
public interface InterfaceService {
    List<String> getInterfaceUrlByType(String type);
}
