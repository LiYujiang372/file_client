package com.demo.file_client.gui.component;

import javax.annotation.PostConstruct;
import javax.swing.JLabel;

import org.springframework.stereotype.Component;

@Component
public class NetStateLabel extends JLabel{

	private static final long serialVersionUID = 7251549602721986541L;
	
	/**
	 * 初始化设置
	 */
	@PostConstruct
	public void init() {
		this.setBounds(50, 20, 200, 50);
		this.setText("正在连接至服务器...");
	}
	
	/**
	 * 更新设置
	 */
	public void updateText(String text) {
		this.setText(text);
	}

}
