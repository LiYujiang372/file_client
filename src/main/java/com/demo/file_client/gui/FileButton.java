package com.demo.file_client.gui;

import javax.annotation.PostConstruct;
import javax.swing.JButton;

import org.springframework.stereotype.Component;

@Component
public class FileButton extends JButton {

	private static final long serialVersionUID = 3857306946531690307L;
	
	/**
	 * 初始化文件选择按钮
	 */
	@PostConstruct
	public void init() {
		
	}

}
