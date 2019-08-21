package com.zhiyu.pm_modbus.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InterfaceMapper {
    @Select("SELECT url FROM pm_external_interface WHERE type = #{type}")
    List<String> getInterfaceUrlByType(String type);

}
