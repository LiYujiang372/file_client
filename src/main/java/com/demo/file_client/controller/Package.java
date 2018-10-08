package com.demo.file_client.controller;


import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 数据包
 * @author tyjw
 *
 */
@Component
public class Package {
	
	/**
	 * 构造一个数据包, 一个数据包头部固定大小为19字节
	 * @param size 文件总大小
	 * @param id 文件唯一Id
	 * @param bytes 文件内容
	 * @return
	 */
	public ByteBuf constructaPackage(int size, long id, byte[] bytes) {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeInt(21 + bytes.length);		//包长度标识符
		buffer.writeByte(1);					//包类型表示符
		buffer.writeInt(size);					//文件总大小
		buffer.writeLong(id);					//文件唯一标识符
		buffer.writeInt(bytes.length);			//数据区长度
		buffer.writeBytes(bytes);				//数据区
		return buffer;
	}

}
