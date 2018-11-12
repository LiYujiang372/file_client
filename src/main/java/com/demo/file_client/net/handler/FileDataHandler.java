package com.demo.file_client.net.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.context.file.FileFrame;
import com.demo.file_client.net.TcpClient;

import io.netty.buffer.ByteBuf;

/**
 * 发送文件数据
 * @author tyjw
 *
 */
@Component
public class FileDataHandler {
	
	@Autowired
	private FileFrame fileFrame;
	
	@Autowired
	private TcpClient client;
	
	/**
	 * 发送文件数据
	 */
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
