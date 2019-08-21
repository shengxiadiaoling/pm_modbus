package com.zhiyu.pm_modbus.netty;

import com.zhiyu.pm_modbus.vo.InterfaceJsonResultVO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("pmClient")
public class PMClient {

    private PMInitializer pmInitializer;

    public PMClient(PMInitializer pmInitializer) {
        this.pmInitializer = pmInitializer;
    }

    public void run(List<InterfaceJsonResultVO> list) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000);
//            b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(pmInitializer);

        // Start the client.
//            ChannelFuture f = b.connect(host, port).sync(); // (5)

        // Wait until the connection is closed.
//            f.channel().closeFuture().sync();
//            List<Channel> channels = new ArrayList<>();
        //访问所有的链接
        for (InterfaceJsonResultVO vo : list) {
            b.connect(vo.getDeviceIp(), vo.getDevicePort());
//              ChannelFuture connect = b.connect(vo.getDeviceIp(), vo.getDevicePort());
//              channels.add(connect.channel());
        }
    }

}
