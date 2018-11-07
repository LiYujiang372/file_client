package com.demo.file_client.net;


import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.controller.GUIController;
import com.demo.file_client.net.handler.oauth.OauthInHandler;
import com.demo.file_client.net.handler.oauth.OauthOutHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

@Component
public class TcpClient {
	
	private Bootstrap bootstrap = new Bootstrap();

	private EventLoopGroup group = new NioEventLoopGroup();
	
	private Channel channel;
	
	@Autowired
	private GUIController guiController;
	
	@Autowired
	private OauthInHandler oauthInHandler;
	
	@Autowired
	private OauthOutHandler oauthOutHandler;
	
	//服务器地址
	private final static String SERVER_IP = "10.0.0.182";
	
	//服务器端口号
	private final static int SERVER_PORT = 12345;
	
	//本地端口号
	private final static int LOCAL_PORT = 9991;
	
	private static Logger logger = LoggerFactory.getLogger(TcpClient.class);
	
	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.localAddress(LOCAL_PORT)
			.remoteAddress(SERVER_IP, SERVER_PORT)
			.handler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(oauthOutHandler);
					ch.pipeline().addLast(oauthInHandler);
				}
			});
	}
	
	/**
	 * 和指定服务器建立连接
	 * @throws InterruptedException 
	 */
	public void doConn() {
		ChannelFuture future = bootstrap.connect();
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					logger.info("已经连接至远程服务器, 开始鉴权");
					channel = future.channel();
				}else {
					Throwable cause = future.cause();
					logger.error("连接失败,错误详情：[{}]",cause.getMessage(), cause);
					guiController.netState(0);
					doConn();
				}
			}
		});
	}
	
	/**
	 * 发送数据
	 */
	public void sendBuf(ByteBuf buf) {
		if (checkChannel(channel)) {
			ChannelFuture future = channel.writeAndFlush(buf);
			future.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						logger.info("已经向服务器发送文件元数据");
					}
				}
			});
		}
	}
	
	/**
	 * 检查channel是否活跃
	 * @param channel
	 */
	public boolean checkChannel(Channel channel) {
		if (channel.isActive()) {
			logger.info("连接活跃,可以发送数据");
			return true;
		}else {
			logger.error("连接断开,请检查网络");
			guiController.netState(0);
			doConn();
			return false; 
		}
	}
}
