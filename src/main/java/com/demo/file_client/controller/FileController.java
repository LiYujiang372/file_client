package com.demo.file_client.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.demo.file_client.gui.component.FileLabelPair;
import com.demo.file_client.net.TcpClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;

/**
 * 客户端上传文件服务
 * @author tyjw
 *
 */
@EnableAsync
@Component
public class FileController {
	
	@Autowired
	private TcpClient client;
	
	@Autowired
	private Package filePackage;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	//文件帧数据区大小
	public static final int FRAME_SIZE = 100;
	
	//文件帧数据队列
	public static Queue<ByteBuf> queue = new ConcurrentLinkedQueue<>();
	
	private static Logger logger = LoggerFactory.getLogger(FileController.class);
	
	/**
	 * 读取文件所有字节
	 * @return
	 */
	public ByteBuf readAllBytes(File file) {
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
	 * 将文件帧数据加入队列
	 * @param fileLabelPair
	 * @param size 一帧数据大小
	 * @throws IOException 
	 */
	public void addQueue(Long fileId, File file) {
		
		//将全部文件数据读入buf中
		ByteBuf buf = readAllBytes(file);
		if (buf == null) {
			return;
		}
		int fileSize = buf.readableBytes();	//文件大小
		
		//buf拆分
		int restLength = buf.readableBytes();
		while (restLength > 0) {
			int frameSize = restLength >= FRAME_SIZE ? FRAME_SIZE : restLength;
			byte[] frameBytes = new byte[frameSize];
			buf.readBytes(frameBytes);
			ByteBuf frame = filePackage.constructaPackage(fileSize, fileId, frameBytes);
			queue.add(frame);//向队列中添加一帧数据
			restLength = buf.readableBytes();
		}
		//释放buf
		ReferenceCountUtil.release(buf);
	}
	
	/**
	 * 多文件并行传输
	 */
	@Async(value = "taskExecutor")
	public void sendFiles(Collection<FileLabelPair> fileLabelPair) {
		//启动加入文件帧队列队列
		for (FileLabelPair pair : fileLabelPair) {
			taskExecutor.execute(() -> {
				addQueue(pair.getFileId(), pair.getFile());
			});
		}
		//发送第一包数据
		while (true) {
			if (queue.size() > 0) {
				client.sendFileFrame();
				break;
			}
		}
	}
	
}
