package com.demo.file_client.controller;

import java.awt.Font;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.gui.component.NetStateLabel;

/**
 * 视图显示控制层
 * @author ASUS
 *
 */
@Component
public class GUIController {
	
	@Autowired
	private NetStateLabel label;
	
	/**
	 * 网络状态控制
	 * @param state 网络状态  1:连接成功  0：连接失败
	 */
	public void netState(int state) {
		String text = "";
		if (state == 1) {
			text = "连接成功";
		}else {
			text = "连接失败，请检查网络设置";
		}
		label.setText(text);
	}

}
