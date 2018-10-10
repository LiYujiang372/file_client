package com.demo.file_client.net.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * 处理文件传输协议
 * @author tyjw
 *
 */
@Sharable
@Component
public class FileHandler extends ChannelOutboundHandlerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(FileHandler.class);
	
	/**
	 * write事件
	 */
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		//logger.info("一个写事件");
	}


	/**
	 * 冲刷数据出站事件
	 */
	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		//logger.info("一个消息出站事件");
	}

}
