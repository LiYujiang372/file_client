package com.demo.file_client.net.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.context.FileReader;
import com.demo.file_client.controller.GUIController;
import com.demo.file_client.controller.NetController;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.ReferenceCountUtil;

@Sharable
@Component
public class ProcessHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	private static Logger logger = LoggerFactory.getLogger(ProcessHandler.class);
	
	@Autowired
	private GUIController guiController;
	
	@Autowired
	private FileReader fileReader;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ProcessHandler.channelActive()");
	}

	/**
	 * 接受服务器响应的进度消息
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
		System.out.println("ProcessHandler.channelRead0()");
		long fileId = buf.readLong();
		int save_size = buf.readInt();
		logger.info("收到消息, file_id:{}, save_size:{}", fileId, save_size);
		
		guiController.showProcess(fileId, save_size);
		
		//发送下一包数据
		ByteBuf outBuf = fileReader.getFileFrame(fileId, save_size);
		
		if (outBuf != null) {
			ChannelFuture future = ctx.writeAndFlush(outBuf);
			future.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						logger.info("数据包发送成功,buf:{}, outBuf:{}", ReferenceCountUtil.refCnt(buf), ReferenceCountUtil.refCnt(outBuf));
					}
				}
			});
		}else {
			if (NetController.bufs.size() > 0) {
				ctx.writeAndFlush(NetController.bufs.poll());
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("ProcessHandler.exceptionCaught()");
		logger.error("异常信息为:[{}]", cause.getMessage(), cause);
	}
	
}
