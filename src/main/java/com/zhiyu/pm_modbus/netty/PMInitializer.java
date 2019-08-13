package com.zhiyu.pm_modbus.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

@Component("pmInitializer")
public class PMInitializer extends ChannelInitializer<SocketChannel> {

    private PMHandler pmHandler;

    public PMInitializer(PMHandler pmHandler) {
        this.pmHandler = pmHandler;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel){
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(pmHandler);
    }
}

