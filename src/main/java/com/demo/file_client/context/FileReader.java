package com.demo.file_client.context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.demo.file_client.context.annotation.LogExecuteTime;
import com.demo.file_client.gui.component.FileLabelPair;
import com.demo.file_client.gui.component.FileListPanel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@Component
public class FileReader {
	
	@Autowired
	private TcpData tcpData;
	
	//文件帧数据区大小
	public static final int FRAME_SIZE = 20480;
	
	private static Logger logger = LoggerFactory.getLogger(FileReader.class);
	
	/**
	 * 读取文件所有字节,返回buf
	 * @return
	 */
	public ByteBuf getFileBuf(File file) {
		ByteBuf buf = null;
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			buf = Unpooled.copiedBuffer(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buf;
	}
	
	/**
	 * 读取文件所有字节
	 * @return
	 */
	public byte[] readAllBytes(File file) {
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 读取文件特定区间的字节数
	 */
	public byte[] readBytes(File file, int offset, int length) {	
		try {
			int filesize = (int) file.length();
			int diff = filesize - offset;
			if (diff <= 0) {
				return null;
			}
			//根据剩余字节数更新diff
			length = diff < length ? diff : length;
			byte[] bytes = new byte[length];
			InputStream is = Files.newInputStream(Paths.get(file.getAbsolutePath()));
			is.skip(offset);
			is.read(bytes);
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取字节数组特定区间的字节
	 */
	public byte[] readBytes(byte[] bytes, int offset, int length) {	
		int diff = bytes.length - offset;
		if (diff <= 0) {
			return null;
		}
		//根据剩余字节数更新diff
		length = diff < length ? diff : length;
		byte[] temp = new byte[length];
		int start = offset;
		int end = offset + length - 1;
		int index = 0;
		while(start <= end) {
			temp[index] = bytes[start];
			start++;
			index++;
		}
		return temp;
	}
	
	/**
	 * 获取一帧文件数据
	 */
	@LogExecuteTime
	public ByteBuf getFileFrame(long fileId, int offset) {
		FileLabelPair pair = FileListPanel.pairMap.get(fileId);
		if (pair == null) {
			return null;
		}
		File file = pair.getFile();
		byte[] bytes = readBytes(pair.getBytes(), offset, FRAME_SIZE);
		if (bytes == null) {
			return null;
		}
		ByteBuf buf = tcpData.getFileDataBuf((int) file.length(), fileId, bytes);
		return buf;
	}
	
	/**
	 * 获取文件元数据帧
	 */
	public ByteBuf getFileMetaBuf(FileLabelPair pair) {
		byte[] md5 = DigestUtils.md5Digest(pair.getBytes());
		ByteBuf metaBuf = tcpData.getFileMetaBuf(pair.getFile(), pair.getFileId(), md5);
		return metaBuf;
	}

}
