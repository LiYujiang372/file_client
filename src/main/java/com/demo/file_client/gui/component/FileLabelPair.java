package com.demo.file_client.gui.component;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JLabel;

public class FileLabelPair {
	
	//本地文件id
	private static int index = 0;
	
	//文件
	private File file;
	
	//文件所有字节
	private byte[] bytes;
	
	//文件唯一Id, 初始值为0,服务端返回后再赋值
	private int fileId;
	
	//文件本地唯一id
	private int localId;

	//文件名标签
	private JLabel nameLable;
	
	//文件上传进度标签
	private JLabel processLabel;
	
	public FileLabelPair(int number, File file) {
		this.file = file;
		try {
			this.bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.localId = index;//初始的文件id为本地id
		this.nameLable = initNameLabel(number, file);
		this.processLabel = initProcessLabel(number);
		
		//本地id增加
		index ++;
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

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getLocalId() {
		return localId;
	}

	public void setLocalId(int localId) {
		this.localId = localId;
	}
	
}
