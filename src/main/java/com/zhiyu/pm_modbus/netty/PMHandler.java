package com.zhiyu.pm_modbus.netty;

import com.zhiyu.pm_modbus.rabbitmq.RabbitmqProducer;
import com.zhiyu.pm_modbus.util.DeviceNoMapUtil;
import com.zhiyu.pm_modbus.util.RegexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;
import java.util.Date;

@Component("pmHandler")
@ChannelHandler.Sharable
public class PMHandler  extends SimpleChannelInboundHandler<String> {
    //发送的验证
    private byte[] cmd_read = {0x00, 0x00, 0x00, 0x00, 0x00, 0x06, 0x01, 0x03, 0x00, 0x3C, 0x00, 0x07};
//    private byte[] cmd_write = {0x00, 0x00, 0x00, 0x00, 0x00, 0x77, 0x01, 0x10, 0x00, 0x01, 0x00, 0x77, 0x77};
    private static final int DATA_LENGTH = 7;
    private static final String PATTERN = "\\d+\\.\\d+\\.\\d+\\.\\d+:\\d+";
    private RabbitmqProducer rabbitmqProducer;

    public PMHandler(RabbitmqProducer rabbitmqProducer) {
        this.rabbitmqProducer = rabbitmqProducer;
    }


    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg){
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        String remoteAddress = RegexUtil.match(socketAddress.toString(),PATTERN);
        System.out.println(remoteAddress);
//        System.out.println(msg);
        messageHandle(msg,remoteAddress);
        ctx.close();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf buf = Unpooled.buffer(cmd_read.length);
        buf.writeBytes(cmd_read);
        System.out.println("connect successfully!" + new Date());
        ctx.writeAndFlush(buf);
    }

    /**
     * 处理从设备接收的数据
     * @param msg 接收的数据
     */
    private void messageHandle(String msg, String remoteAddress) {
        byte[] tmp = msg.getBytes();
        if (tmp.length != 23) {
            System.out.println("message error!");
            return;
        }
        int[] data = new int[DATA_LENGTH];
        int j = 9;
        for (int i = 0; i < data.length; i++) {
            data[i] = (short) (((tmp[j++] & 0xFF) << 8) | (tmp[j++] & 0xFF));
        }
        String re = buildMessage(data,remoteAddress);
        System.out.println(re);
        rabbitmqProducer.send(re);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 将数据数组解析为可被服务器解析的字符串
     */
    private String buildMessage(int[] data,String remoteAddress) {
        //STX05801|HN|V0.1|123456|+29.6|50.0|N|57.00|N|55.30|0.00|0|123456
        String deviceNo = DeviceNoMapUtil.getDeviceNo(remoteAddress);
        return "STX00001|DT|V1.0|" + deviceNo + "|" +//终端序列号
                data[2] / 10 + "|" +//温度值
                data[3] / 10 + "|" +//湿度值
                data[0] + "|" +//PM2.5值
                data[1] + "|" +//PM10值
                "N" + "|" +//雨量值
                data[6] / 10 + "|" +//噪声
                data[4] / 10 + "|" +//风速值
                getWindDirection(data[5]) + "|" +//风向
                deviceNo;//设备唯一的编号
    }

    /**
     * 风向数据解析
     */
    private String getWindDirection(int a) {
        String str;
        switch (a) {
            case 1:
                str = "N";
                break;
            case 2:
                str = "NE";
                break;
            case 3:
                str = "E";
                break;
            case 4:
                str = "SE";
                break;
            case 5:
                str = "S";
                break;
            case 6:
                str = "SW";
                break;
            case 7:
                str = "W";
                break;
            case 8:
                str = "NW";
                break;
            default:
                str = "ESWN";
                break;
        }
        return str;
    }
}
