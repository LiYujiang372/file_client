package com.demo.file_client.net.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.controller.FileController;
import com.demo.file_client.controller.GUIController;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
@Component
public class ProcessHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	private static Logger logger = LoggerFactory.getLogger(ProcessHandler.class);
	
	@Autowired
	private GUIController guiController;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}

	/**
	 * 接受服务器响应的进度消息
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
		long fileId = buf.readLong();
		byte process = buf.readByte();
		guiController.showProcess(fileId, process);
		
		if (FileController.queue.size() > 0) {
			ctx.writeAndFlush(FileController.queue.poll());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("异常信息为:[{}]", cause.getMessage(), cause);
	}
	
}
