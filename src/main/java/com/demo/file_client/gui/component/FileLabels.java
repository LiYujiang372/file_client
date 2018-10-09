package com.demo.file_client.gui.component;

import java.awt.Color;
import java.io.File;
import javax.swing.JLabel;
import com.demo.file_client.gui.pattern.UIPatterns;

public class FileLabels {

	private JLabel nameLable;
	
	private JLabel processLabel;
	
	public FileLabels(int number, File file) {
		this.nameLable = initNameLabel(number, file);
		this.processLabel = initProcessLabel(number);
	}

	/**
	 * 获取文件名标签
	 */
	public JLabel initNameLabel(int number, File file) {
		JLabel label = new JLabel();
		label.setBounds(10, 10 + (50 * number), 500, 50);
		label.setOpaque(true);
		label.setBackground(Color.cyan);
		label.setBorder(UIPatterns.getBorder());
		label.setText(file.getName());
		return label;
	}
	
	/**
	 * 获取进度条标签
	 */
	public JLabel initProcessLabel(int number) {
		JLabel processLabel = new JLabel();
		processLabel.setBounds(550, 10 + (50 * number), 250, 50);
		processLabel.setOpaque(true);
		processLabel.setBackground(Color.cyan);
		processLabel.setBorder(UIPatterns.getBorder());
		return processLabel;
	}

	public JLabel getNameLable() {
		return nameLable;
	}

	public void setNameLable(JLabel nameLable) {
		this.nameLable = nameLable;
	}

	public JLabel getProcessLabel() {
		return processLabel;
	}

	public void setProcessLabel(JLabel processLabel) {
		this.processLabel = processLabel;
	}
	
}
