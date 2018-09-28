package com.demo.file_client.data;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.net.TcpClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 客户端上传文件服务
 * @author tyjw
 *
 */
@Component
public class FileDao {
	
	@Autowired
	private TcpClient client;
	
	private List<File> files = new ArrayList<>();
	
	/**
	 * 添加一个文件
	 * @param file
	 */
	public void addFile(File file) {
		try {
			sendFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取文件字节数据
	 * @throws IOException 
	 */
	public ByteBuf getFileBytes(File file) throws IOException {
		ByteBuf buf = Unpooled.buffer();
		FileInputStream fis = new FileInputStream(file);
		int n = fis.read();
		buf.writeByte(n);
		while (n != -1) {
			n = fis.read();
			buf.writeByte(n);
		}
		return buf;
	}
	
	/**
	 * 传输文件数据
	 * @throws IOException 
	 */
	public void sendFile(File file) throws IOException {
		client.sendRequest(getFileBytes(file));
	}
}
