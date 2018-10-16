package com.demo.file_client.net.handler.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.controller.GUIController;
import com.demo.file_client.net.handler.ProcessHandler;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@Sharable
@Component
public class OauthInHandler extends ChannelInboundHandlerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(OauthInHandler.class);
	
	@Autowired
	private GUIController guiController;
	
	@Autowired
	private ProcessHandler processHandler;
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("OauthInHandler.channelRegistered()");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("OauthInHandler.channelUnregistered()");
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("OauthInHandler.channelActive()");
		super.channelActive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("OauthInHandler.channelReadComplete()");
		super.channelReadComplete(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		System.out.println("OauthInHandler.userEventTriggered()");
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		System.out.println("OauthInHandler.channelWritabilityChanged()");
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("OauthInHandler.exceptionCaught()");
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("OauthInHandler.channelInactive()");
		logger.info("鉴权失败,服务器主动关闭了连接");
		guiController.netState(0);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("OauthInHandler.channelRead()");
		ByteBuf buf = (ByteBuf) msg;
		logger.info("收到服务器返回的消息, 鉴权成功!");
		
		//解析buf数据,更新业务逻辑
		//parse buf
		
		//更新视图层
		guiController.netState(1);
		
		//更新handler
		ctx.pipeline().remove(OauthInHandler.class);
		ctx.pipeline().remove(OauthOutHandler.class);
		ctx.pipeline().addLast(processHandler);
	}
}
