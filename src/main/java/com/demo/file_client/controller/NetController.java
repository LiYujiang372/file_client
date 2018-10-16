package com.demo.file_client.controller;


import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.demo.file_client.context.FileReader;
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
	private FileReader fileReader;
	
	public static ConcurrentLinkedQueue<ByteBuf> bufs = new ConcurrentLinkedQueue<>();

	/**
	 * 多文件并行传输
	 */
	@Async(value = "taskExecutor")
	public void sendFiles(Collection<FileLabelPair> fileLabelPair) {
		fileLabelPair.stream().forEach(p -> {
			bufs.add(fileReader.getFileMetaBuf(p));
		});
		client.sendBuf(bufs.poll());
	}

}
