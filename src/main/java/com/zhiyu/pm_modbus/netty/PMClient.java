package com.zhiyu.pm_modbus.netty;

import com.zhiyu.pm_modbus.vo.ModBusDeviceVO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("pmClient")
public class PMClient {

    private PMInitializer pmInitializer;
    private static final Logger log = LoggerFactory.getLogger(PMClient.class);

    public PMClient(PMInitializer pmInitializer) {
        this.pmInitializer = pmInitializer;
    }

    public void run(List<ModBusDeviceVO> list){
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
        for (ModBusDeviceVO vo : list) {
            try {
                ChannelFuture f = b.connect(vo.getDeviceIp(), vo.getPort()).sync();
                f.channel().closeFuture().sync();
            } catch (Exception e) {
                log.error("error : ", e);
            }
        }
    }

}
