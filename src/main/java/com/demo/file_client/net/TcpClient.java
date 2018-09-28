package com.demo.file_client.net;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.file_client.net.handler.FileHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

@Component
public class TcpClient {
	
	private Bootstrap bootstrap = new Bootstrap();

	private EventLoopGroup group = new NioEventLoopGroup();
	
	private Channel channel;
	
	//服务器地址
	private final static String SERVER_IP = "10.0.0.167";
	
	//服务器端口号
	private final static int SERVER_PORT = 2345;
	
	//本地端口号
	private final static int LOCAL_PORT = 999;
	
	private static Logger logger = LoggerFactory.getLogger(TcpClient.class);
	
	/**
	 * 初始化客户端
	 */
	public void init() {
		bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.localAddress(LOCAL_PORT)
			.remoteAddress(SERVER_IP, SERVER_PORT)
			.handler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new FileHandler());
				}
			});
	}
	
	/**
	 * 和指定服务器建立连接
	 * @throws InterruptedException 
	 */
	public void conn() throws InterruptedException {
		init();
		try {
			ChannelFuture future = bootstrap.connect().sync();
			channel = future.channel();
		} catch (InterruptedException e) {
			e.printStackTrace();
			group.shutdownGracefully().sync();
			logger.info("连接关闭了");
		}
	}
	
	/**
	 * 发送数据
	 */
	public void sendRequest(byte[] bytes) {
		ByteBuf buf = Unpooled.buffer();
		buf.writeBytes(bytes);
		sendRequest(buf);
	}
	
	/**
	 * 发送数据
	 */
	public void sendRequest(ByteBuf buf) {
		channel.writeAndFlush(buf);
		logger.info("已经向服务器发送文件数据");
	}

}
