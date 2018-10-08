package com.demo.file_client.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.file_client.context.annotation.LogExecuteTime;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 处理文件的读取
 * @author tyjw
 *
 */
@Component
public class FileReader {
	
	private static Logger logger = LoggerFactory.getLogger(FileReader.class);
	
	/**
	 * 读取文件流
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	@LogExecuteTime
	public byte[] getAllFileBytes(File file) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		return bytes;
	}
	
	/**
	 * 读取文件流
	 * @param file
	 * @param number 一次读取的字节数
	 * @return
	 * @throws IOException 
	 */
	@LogExecuteTime
	public List<byte[]> getFileBytes(File file, int number) throws IOException {
		Path path = Paths.get(file.getAbsolutePath());
		InputStream is = Files.newInputStream(path);
		List<byte[]> list = new ArrayList<>();
		//一包数据2000个字节
		byte[] bytes = new byte[number];
		int count = is.read(bytes);
		while(count != -1) {
			if (count == number) {
				list.add(bytes);
			}else {
				//到达文件末尾.没有读满number字节
				byte[] bytes2 = new byte[count];
				for (int i = 0; i < bytes2.length; i++) {
					bytes[i] = bytes[i];
				}
				list.add(bytes2);
			}
			count = is.read(bytes);
		}
		return list;
	}
	
	
//	public static void main(String[] args) throws IOException {
//		FileReader reader = new FileReader();
//		Path path = Paths.get("C:\\Users\\tyjw\\Desktop\\test\\ceshi.jpg");
//		System.err.println(path.toFile().length());
//		List<byte[]> list = reader.getFileBytes(path.toFile(), 30000);
//		
//		ByteBuf buf = Unpooled.buffer();
//		for (byte[] bs : list) {
//			buf.writeBytes(bs);
//		}
//		
//		Path path2 = Paths.get("E:\\file_server\\ceshi.jpg");
//		OutputStream os = Files.newOutputStream(path2, StandardOpenOption.CREATE);
//	
//		byte[] fileBytes = new byte[buf.readableBytes()];
//		buf.readBytes(fileBytes);
//		os.write(fileBytes);
//		os.close();
//	}
	
	public static void main(String[] args) throws IOException {
		FileReader reader = new FileReader();
		Path path = Paths.get("C:\\Users\\tyjw\\Desktop\\test\\ceshi.jpg");
		byte[] bytes = reader.getAllFileBytes(path.toFile());
		Path path2 = Paths.get("E:\\file_server\\ceshi.jpg");
		OutputStream os = Files.newOutputStream(path2, StandardOpenOption.CREATE);
		os.write(bytes);
		for (byte b : bytes) {
			os.write(b);
		}
	}

}
