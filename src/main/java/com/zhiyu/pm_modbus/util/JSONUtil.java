package com.zhiyu.pm_modbus.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JSONUtil {
    /**
     * 基本的json解析方法
     * @param string 待解析的json字符串
     * @param clazz 要解析返回的类型的Class
     * @param key json结果中用于判断是否成功的key
     * @param <T> 要解析返回的类型
     * @return 解析获得的List
     */
    public static <T> List<T> basicParse(String string, String key, Class<T> clazz) {
        JSONObject jsonObject = JSON.parseObject(string);
        String success = jsonObject.getString(key);
        List<T> results = new LinkedList<>();
        if ("true".equals(success)) {
            JSONArray data = null;
            T obj = null;
            try {
                data = jsonObject.getJSONArray("data");
            } catch (Exception e) {
                obj = jsonObject.getObject("data", clazz);
            }
            if (null != data) {
                for (int i = 0; i < data.size(); i++) {
                    T result = data.getObject(i,clazz);
                    results.add(result);
                }
            }
            if (null != obj)
                results.add(obj);
            return results;
        }
        return null;
    }
}
