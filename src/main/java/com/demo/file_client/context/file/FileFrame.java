package com.demo.file_client.context.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.demo.file_client.context.BufWriter;
import com.demo.file_client.context.annotation.LogExecuteTime;
import com.demo.file_client.gui.component.FileLabelPair;
import com.demo.file_client.gui.component.FileListPanel;

import io.netty.buffer.ByteBuf;

@Component
public class FileFrame {
	
	@Autowired
	private FileReader fileReader;
	
	//文件帧数据区大小
	public static final int FRAME_SIZE = 2048000;
	
	/**
	 * 获取一帧文件数据
	 */
	@LogExecuteTime
	public ByteBuf getFileFrame(int fileId, int offset) {
		FileLabelPair pair = FileListPanel.pairMap.get(fileId);
		if (pair == null) {
			return null;
		}
		byte[] bytes = fileReader.readBytes(pair.getBytes(), offset, FRAME_SIZE);
		if (bytes == null) {
			return null;
		}
		ByteBuf buf = BufWriter.getFileDataBuf((int) pair.getFile().length(), pair.getFileId(), bytes);
		return buf;
	}
	
	/**
	 * 获取文件元数据帧
	 */
	@LogExecuteTime
	public ByteBuf getFileMetaFrame(int localId) {
		FileLabelPair pair = FileListPanel.pairMap.get(localId);
		if (pair == null) {
			return null;
		}
		byte[] md5 = DigestUtils.md5Digest(pair.getBytes());
		ByteBuf metaBuf = BufWriter.getFileMetaBuf(pair.getFile(), pair.getLocalId(), md5);
		return metaBuf;
	}

}
