package com.demo.file_client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.gui.component.FileLabelPair;
import com.demo.file_client.gui.component.FileListPanel;
import com.demo.file_client.gui.component.NetStateLabel;
import com.demo.file_client.gui.component.UploadButton;

/**
 * 视图显示控制层
 * @author ASUS
 *
 */
@Component
public class GUIController {
	
	@Autowired
	private NetStateLabel label;
	
	@Autowired
	private UploadButton uploadButton;
	
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
	
	/**
	 * 更新文件传输进度
	 */
	public void showProcess(long fileId, byte process) {
		 FileLabelPair pair = FileListPanel.pairMap.get(fileId);
		 pair.updateProcess(process);
	}
	
	/**
	 * 文件上传按钮变为不可用
	 */
	public void disableButton() {
		uploadButton.setEnabled(false);
	}

}
