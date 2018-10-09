package com.demo.file_client.controller;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.net.TcpClient;
import com.demo.file_client.util.Utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 客户端上传文件服务
 * @author tyjw
 *
 */
@Component
public class FileController {
	
	@Autowired
	private TcpClient client;
	
	@Autowired
	private FileReader reader;
	
	@Autowired
	private Package filePackage;
	
	private static Logger logger = LoggerFactory.getLogger(FileController.class);
	
	/**
	 * 添加一个文件
	 * @param file
	 */
	public void addFile(File file) {
		try {
			//sendData(file);
			//sendFile(file);
			sendData2(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 流式传输
	 * @throws IOException 
	 */
	public void sendData(File file) throws IOException {
		List<byte[]> list = reader.getFileBytes(file, 30000);
		logger.info("字节数据List大小:{}", list.size());
		for (byte[] bytes : list) {
			ByteBuf buf = filePackage.constructaPackage((int)file.length(), Utils.getRandomLong(), bytes);
			client.sendRequest(buf);
		}
	}
	
	/**
	 * 流式传输2
	 * @throws IOException 
	 */
	public void sendData2(File file) throws IOException {
		byte[] bytes = reader.getAllFileBytes(file);
		logger.info("file_length:{}, bytes_length:{}", file.length(), bytes.length);
		
		ByteBuf buf = Unpooled.copiedBuffer(bytes);
		Long fileId = Utils.getRandomLong();
		
		while (buf.readableBytes() >= 10240) {
			byte[] tempBytes = new byte[10240];
			buf.readBytes(tempBytes);
			ByteBuf packageBuf = filePackage.constructaPackage(bytes.length, fileId, tempBytes);
			client.sendRequest(packageBuf);
		}
		if (buf.readableBytes() > 0) {
			byte[] tempBytes = new byte[buf.readableBytes()];
			buf.readBytes(tempBytes);
			ByteBuf packageBuf = filePackage.constructaPackage(bytes.length, fileId, tempBytes);
			client.sendRequest(packageBuf);
		}
	}
	
	/**
	 * 文件传输
	 * @throws IOException 
	 */
	public void sendFile(File file) throws IOException {
		byte[] bytes = reader.getAllFileBytes(file);
		logger.info("file_length:{}, bytes_length:{}", file.length(), bytes.length);
		ByteBuf buf = filePackage.constructaPackage(bytes.length, Utils.getRandomLong(), bytes);
		client.sendRequest(buf);
	}
	
}
