package com.zhiyu.pm_modbus.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;

@Component("pmClient")
public class PMClient {

    private PMInitializer pmInitializer;

    public PMClient(PMInitializer pmInitializer) {
        this.pmInitializer = pmInitializer;
    }

    public void run(String host, int port) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,2000);
//            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(pmInitializer);

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println("time out!");
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
