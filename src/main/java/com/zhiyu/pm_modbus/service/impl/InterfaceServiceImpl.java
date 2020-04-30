package com.zhiyu.pm_modbus.service.impl;

import com.zhiyu.pm_modbus.mapper.InterfaceMapper;
import com.zhiyu.pm_modbus.service.InterfaceService;
import com.zhiyu.pm_modbus.vo.ModBusDeviceVO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InterfaceServiceImpl implements InterfaceService {

    private InterfaceMapper interfaceMapper;

    public InterfaceServiceImpl(InterfaceMapper interfaceMapper) {
        this.interfaceMapper = interfaceMapper;
    }

    @Override
    @Deprecated
    public List<String> getInterfaceUrlByType(String type) {
        return interfaceMapper.getInterfaceUrlByType(type);
    }

    @Override
    public List<ModBusDeviceVO> getDevices() {
        return interfaceMapper.getDevices();
    }
}
