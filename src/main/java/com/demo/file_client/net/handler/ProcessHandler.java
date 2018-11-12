package com.demo.file_client.net.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.demo.file_client.controller.GUIController;
import com.demo.file_client.controller.NetController;
import com.demo.file_client.gui.component.FileLabelPair;
import com.demo.file_client.gui.component.FileListPanel;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
@Component
public class ProcessHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	private static Logger logger = LoggerFactory.getLogger(ProcessHandler.class);
	
	@Autowired
	private GUIController guiController;
	
	@Autowired
	private NetController netController;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ProcessHandler.channelActive()");
	}

	/**
	 * 接受服务器响应的进度消息
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
		System.out.println("ProcessHandler.channelRead0()");
		byte head = buf.readByte();//包头
		System.err.println("包头:" + head);
		if (head == 0x22) {
			//文件元数据包,更新文件id
			int localId = buf.readInt();//文件本地id
			int fileId = buf.readInt();//文件id
			FileLabelPair pair = FileListPanel.pairMap.get(localId);
			pair.setFileId(fileId);
			FileListPanel.pairMap.put(fileId, pair);
			FileListPanel.pairMap.remove(localId);
			/*
			 * 更新完成后启动对文件数据的发送
			 */
			netController.sendFileData(fileId);
		}else if (head == 0x32) {
			//文件数据包,更新进度
			int fileId = buf.readInt();//文件id
			int save_size = buf.readInt();//已存储大小
			guiController.showProcess(fileId, save_size);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("ProcessHandler.exceptionCaught()");
		logger.error("异常信息为:[{}]", cause.getMessage(), cause);
	}
	
}
