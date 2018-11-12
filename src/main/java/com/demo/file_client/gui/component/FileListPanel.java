package com.demo.file_client.gui.component;

import java.awt.Color;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.demo.file_client.gui.pattern.UIPatterns;
import com.demo.file_client.util.Utils;


/**
 * 文件列表标签
 * @author ASUS
 *
 */
@Component
public class FileListPanel extends JPanel {
	
	private static final long serialVersionUID = -6696011519189813884L;
	
	/**
	 * 全局变量,记录文件数量
	 */
	private static int number = 0;
	
	public static Map<Integer, FileLabelPair> pairMap = new ConcurrentHashMap<>();
	
	/* 
	 * 初始化设置
	 * 创建面板，面板类似于 HTML 的 div 标签
     * 我们可以创建多个面板并在 JFrame 中指定位置
     * 面板中我们可以添加文本字段，按钮及其他组件。
     */
	@PostConstruct
	public void init() {
		this.setBounds(50, 100, 900, 400);
		this.setBorder(UIPatterns.getBorder());
		this.setLayout(null);
		this.setOpaque(true);//想设置背景色必须将该标签设置为不透明
		this.setBackground(Color.lightGray);
	}
	
	/**
	 * 添加一些文件
	 */
	public void addFiles(File[] files) {
		if (!Utils.isBlank(files)) {
			for (File file : files) {
				addOneFile(file);
			}
		}
	}
	
	/**
	 * 添加一个文件
	 */
	private void addOneFile(File file) {
		FileLabelPair pair = new FileLabelPair(number, file);
		this.add(pair.getNameLable());
		this.add(pair.getProcessLabel());
		pairMap.put(pair.getLocalId(), pair);
		number++;
		this.repaint();
	}
	

}
