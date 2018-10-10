package com.demo.file_client.gui.component;

import java.awt.Color;
import java.io.File;
import javax.swing.JLabel;
import com.demo.file_client.gui.pattern.UIPatterns;
import com.demo.file_client.util.Utils;

public class FileLabelPair {
	
	//文件
	private File file;
	
	//文件唯一Id
	private long fileId;

	//文件名标签
	private JLabel nameLable;
	
	//文件上传进度标签
	private JLabel processLabel;
	
	public FileLabelPair(int number, File file) {
		this.file = file;
		this.fileId = Utils.getRandomLong();
		this.nameLable = initNameLabel(number, file);
		this.processLabel = initProcessLabel(number);
	}

	/**
	 * 获取文件名标签
	 */
	public JLabel initNameLabel(int number, File file) {
		JLabel label = new JLabel();
		label.setBounds(10, 10 + (50 * number), 500, 20);
		label.setOpaque(true);
		label.setText(file.getAbsolutePath());
		return label;
	}
	
	/**
	 * 获取进度条标签
	 */
	public JLabel initProcessLabel(int number) {
		JLabel processLabel = new JLabel();
		processLabel.setBounds(550, 10 + (50 * number), 0, 20);
		processLabel.setOpaque(true);
		processLabel.setBackground(Color.cyan);
		return processLabel;
	}
	
	/**
	 * 更新进度条
	 * @return
	 */
	public void updateProcess(byte process) {
		processLabel.setSize(3 * process, processLabel.getHeight());
		processLabel.setText(process + "%");
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	
}
