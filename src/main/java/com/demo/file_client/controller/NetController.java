package com.demo.file_client.controller;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.demo.file_client.context.FileReader;
import com.demo.file_client.gui.component.FileLabelPair;
import com.demo.file_client.gui.component.FileListPanel;
import com.demo.file_client.net.TcpClient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.ReferenceCountUtil;

/**
 * 客户端上传文件服务
 * @author tyjw
 *
 */
@EnableAsync
@Component
public class NetController {

	@Autowired
	private TcpClient client;
	
	@Autowired
	private GUIController gUIController;
	
	@Autowired
	private FileReader fileReader;
	
	/**
	 * 多文件并行传输
	 */
	@Async(value = "taskExecutor")
	public void sendFiles(Collection<FileLabelPair> fileLabelPair) {
		if (FileListPanel.bufs.size() > 0) {
			client.sendBuf(FileListPanel.bufs.poll());
			gUIController.disableButton();
		}
	}
	
	/**
	 * 发送文件数据
	 */
	public void sendFileData(int localId, int off) {
//		ByteBuf outBuf = fileReader.getFileFrame(localId, save_size);
//		if (outBuf != null) {
//			ChannelFuture future = ctx.writeAndFlush(outBuf);
//			future.addListener(new ChannelFutureListener() {
//				@Override
//				public void operationComplete(ChannelFuture future) throws Exception {
//					if (future.isSuccess()) {
//						logger.info("数据包发送成功,buf:{}, outBuf:{}", ReferenceCountUtil.refCnt(buf), ReferenceCountUtil.refCnt(outBuf));
//					}
//				}
//			});
//		}
	}

}
