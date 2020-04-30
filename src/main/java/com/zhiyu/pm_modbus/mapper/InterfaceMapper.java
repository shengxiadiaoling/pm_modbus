package com.zhiyu.pm_modbus.mapper;

import com.zhiyu.pm_modbus.vo.ModBusDeviceVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InterfaceMapper {
    @Select("SELECT url FROM pm_external_interface WHERE type = #{type} and status = 1")
    @Deprecated
    List<String> getInterfaceUrlByType(String type);

    List<ModBusDeviceVO> getDevices();
}
