package com.demo.file_client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.controller.FileController;

@Component
public class MainFrame {
	
	@Autowired
	private FileController fileController;
	
	@Autowired
	private NetStateLabel netStateLabel;
	
	private static Logger logger = LoggerFactory.getLogger(MainFrame.class);
	
	/**
	 * 创建图形化界面
	 */
	public void createFrame() {
		// 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);

        // 创建及设置窗口
        JFrame frame = new JFrame("上传文件");
        frame.setSize(800, 500);
        
        //关闭窗口时也退出应用程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        panel.setLayout(null);
        // 添加面板
        frame.add(panel);

        // 添加网络状态标签
        panel.add(netStateLabel);
        
        //上传
        JButton chooseFileButton = new JButton("选择文件");
        chooseFileButton.setBounds(300, 100, 170, 30);
        chooseFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file != null) {
					if(file.isDirectory()) {
						logger.info("文件夹:{}", file.getAbsolutePath());
					} else if(file.isFile()) {
						logger.info("文件:{}", file.getAbsolutePath());
					}
					logger.info("载入文件...");
					fileController.addFile(file);
				}
			}
		});
        panel.add(chooseFileButton);
        
        // 按钮
        JButton button = new JButton("上传");
        button.setBounds(350, 350, 70, 30);
        panel.add(button);

        // 显示窗口
        frame.setVisible(true);
    }
	
	/**
	 * 显示文件上传界面
	 */
	public void showFrame() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	createFrame();
            }
        });
	}

}
