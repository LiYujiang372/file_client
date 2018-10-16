package com.demo.file_client.context;

import java.io.File;

import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;

/**
 * 构造用户鉴权数据包
 * @author tyjw
 *
 */
@Component
public class TcpData {
	
	/**
	 * 鉴权数据包头
	 */
	private static final int OAUTH_HEAD = 0x12;
	
	/**
	 * 
	 * @param channel
	 * @param tid
	 * @return
	 */
	public ByteBuf getOauthPacket(Channel channel, int uid, int tid) {
		//从channel中获取池化的buf
		ByteBuf buffer = null;
		if (channel != null) {
			ByteBufAllocator allocator = channel.alloc();
			buffer = allocator.buffer();
		}else {
			buffer = Unpooled.buffer();
		}
		
		//组包
		buffer.writeByte(OAUTH_HEAD);
		buffer.writeInt(uid);
		buffer.writeInt(tid);
		
		return buffer;
	}
	
	/**
	 * 文件数据数据包
	 * @param size 文件总大小
	 * @param id 文件唯一Id
	 * @param bytes 文件内容
	 * @return
	 */
	public ByteBuf getFileDataBuf(int size, long id, byte[] bytes) {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeByte(0x12);					//包类型表示符
		buffer.writeLong(id);					//文件唯一标识符
		buffer.writeInt(bytes.length);			//数据区长度
		buffer.writeBytes(bytes);				//数据区
		return buffer;
	}
	
	/**
	 * 文件元数据包
	 */
	public ByteBuf getFileMetaBuf(File file, long fileId, byte[] md5) {
		ByteBuf buf = Unpooled.buffer();
		buf.writeByte(0x22);//包类型标识符
		buf.writeLong(fileId);//文件id
		
		/*
		 * 构造数据区
		 */
		ByteBuf metaBuf = Unpooled.buffer();
		metaBuf.writeLong(fileId);//文件id
		metaBuf.writeByte(1);//文件类型
		metaBuf.writeInt((int) file.length());//文件总大小
		metaBuf.writeBytes(md5);//MD5校验码
		
		String fileName = file.getName();
		byte[] nameBytes = fileName.getBytes(CharsetUtil.UTF_8);
		metaBuf.writeInt(nameBytes.length);//文件名长度
		metaBuf.writeBytes(nameBytes);//文件名
		
		//数据区长度
		int length = metaBuf.readableBytes();
		buf.writeInt(length);
		buf.writeBytes(metaBuf);//该方法会改变metaBuf的readerIndex
		return buf;
	}

}
