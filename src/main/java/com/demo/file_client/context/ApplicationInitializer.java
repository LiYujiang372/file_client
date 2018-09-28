package com.demo.file_client.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.demo.file_client.gui.MainFrame;
import com.demo.file_client.net.TcpClient;

@Component
public class ApplicationInitializer implements ApplicationRunner{
	
	@Autowired
	private MainFrame frame;
	
	@Autowired
	private TcpClient client;

	/**
	 * 项目初始化
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 显示应用 GUI
		frame.showFrame();
		//启动服务器
		client.conn();
	}

}
