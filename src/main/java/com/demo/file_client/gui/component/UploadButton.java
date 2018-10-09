package com.demo.file_client.gui.component;

import javax.annotation.PostConstruct;
import javax.swing.JButton;

import org.springframework.stereotype.Component;

/**
 * 文件上传按钮
 * @author liyujiang
 *
 */
@Component
public class UploadButton extends JButton {

	private static final long serialVersionUID = 6597829233951049176L;
	
	@PostConstruct
	public void init() {
		this.setText("点击上传");
        this.setBounds(400, 550, 200, 30);
	}

}
