package com.demo.file_client.net.handler.oauth;

import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.file_client.context.BufWriter;

import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@Sharable
@Component
public class OauthOutHandler extends ChannelOutboundHandlerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(OauthOutHandler.class);
	
	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		System.out.println("OauthOutHandler.bind()");
		super.bind(ctx, localAddress, promise);
	}

	/**
	 * promise 和 future的区别
	 * promise 不可变
	 */
	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		System.out.println("OauthOutHandler.connect()");
		super.connect(ctx, remoteAddress, localAddress, promise);
		//等待连接操作完成, 连接完成后发送鉴权数据
		promise.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					logger.info("连接成功, 发送鉴权数据包");
					ByteBuf buf = BufWriter.getOauthPacket(ctx.channel(), 1, 2);
					ChannelFuture writeFuture = ctx.writeAndFlush(buf);
					writeFuture.addListener(new ChannelFutureListener() {
						@Override
						public void operationComplete(ChannelFuture future) throws Exception {
							if (future.isSuccess()) {
								logger.info("鉴权数据包发送成功!");
							}else {
								Throwable cause = future.cause();
								logger.error("鉴权数据包发送失败, 异常信息:[{}]", cause.getMessage(), cause);
							}
						}
					});
				}else {
					Throwable cause = future.cause();
					logger.error("连接失败, 异常信息:[{}]", cause.getMessage(), cause);
				}
			}
		});
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("OauthOutHandler.disconnect()");
		super.disconnect(ctx, promise);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("OauthOutHandler.close()");
		super.close(ctx, promise);
	}

	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("OauthOutHandler.deregister()");
		super.deregister(ctx, promise);
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		System.out.println("OauthOutHandler.read()");
		super.read(ctx);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println("OauthOutHandler.write()");
		super.write(ctx, msg, promise);
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		System.out.println("OauthOutHandler.flush()");
		super.flush(ctx);
	}

}
