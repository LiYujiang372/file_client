package com.demo.file_client.gui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.swing.JButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.controller.FileController;
import com.demo.file_client.controller.GUIController;

/**
 * 文件上传按钮
 * @author liyujiang
 *
 */
@Component
public class UploadButton extends JButton {
	
	@Autowired
	private FileController fileController;
	
	@Autowired
	private GUIController guiController;

	private static final long serialVersionUID = 6597829233951049176L;
	
	@PostConstruct
	public void init() {
		this.setText("点击上传");
        this.setBounds(400, 550, 200, 30);
        this.setEnabled(false);
        this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Collection<FileLabelPair> pairs = FileListPanel.pairMap.values();
				fileController.sendFiles(pairs);
				guiController.disableButton();
			}
		});
	}

}
