package com.demo.file_client.gui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChooseButton extends JButton {

	private static final long serialVersionUID = -5588531078590985226L;
	
	private static Logger logger = LoggerFactory.getLogger(ChooseButton.class);
	
	@Autowired
	private FileListPanel filesPanel;
	
	/**
	 * 初始化文件选择按钮
	 */
	@PostConstruct
	public void init() {
		this.setText("选择文件");
        this.setBounds(600, 20, 170, 30);
        this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//只允许用户选择文件
				jfc.setMultiSelectionEnabled(true);
				jfc.showDialog(new JLabel(), "选择文件");
				File[] files = jfc.getSelectedFiles();
				filesPanel.addFiles(files);
			}
		});
	}

}
