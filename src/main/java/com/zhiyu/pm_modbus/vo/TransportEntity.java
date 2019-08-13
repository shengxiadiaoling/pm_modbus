package com.zhiyu.pm_modbus.vo;


import java.io.Serializable;

public class TransportEntity implements Serializable {
    private String pmMessage; 	// pm发送的时间
    private String ip; 			// 发送者IP
    private String receiveTime; // 接收pm的时间

    public String getPmMessage() {
        return pmMessage;
    }

    public void setPmMessage(String pmMessage) {
        this.pmMessage = pmMessage;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }
}
