package com.demo.file_client.controller;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.demo.file_client.context.file.FileFrame;
import com.demo.file_client.gui.component.FileLabelPair;
import com.demo.file_client.net.TcpClient;

import io.netty.buffer.ByteBuf;

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
	private FileFrame fileFrame;
	
	/**
	 * 发送所有文件的元数据
	 */
	@Async(value = "taskExecutor")
	public void sendFiles(Collection<FileLabelPair> fileLabelPair) {
		for (FileLabelPair pair : fileLabelPair) {
			client.sendBuf(fileFrame.getFileMetaFrame(pair.getLocalId()));
		}
	}
	
	/**
	 * 接收到文件id后, 发送文件数据
	 */
	@Async(value = "taskExecutor")
	public void sendFileData(int fileId) {
		int save_size = 0;
		ByteBuf outBuf = fileFrame.getFileFrame(fileId, save_size);
		while (outBuf != null) {
			client.sendBuf(outBuf);
			save_size = (outBuf.readableBytes() - 9) + save_size;
			outBuf = fileFrame.getFileFrame(fileId, save_size);
		}
	}

}
