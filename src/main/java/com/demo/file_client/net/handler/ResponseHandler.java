package com.demo.file_client.net.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ResponseHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	private static Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, ByteBuf buf) throws Exception {
		int length = buf.readableBytes();
		byte[] bytes = new byte[length];
		buf.readBytes(bytes);
		logger.info("服务器响应:{}", new String(bytes, CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		logger.error("异常信息为:[{}]", cause.getMessage(), cause);
	}
	
}
