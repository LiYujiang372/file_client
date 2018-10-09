package com.demo.file_client.gui;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.file_client.gui.component.ChooseButton;
import com.demo.file_client.gui.component.FileListPanel;
import com.demo.file_client.gui.component.NetStateLabel;
import com.demo.file_client.gui.component.UploadButton;

@Component
public class MainFrame {
	
	@Autowired
	private NetStateLabel netStateLabel;
	
	@Autowired
	private FileListPanel fileListPanel;
	
	@Autowired
	private ChooseButton chooseButton;
	
	@Autowired
	private UploadButton uploadButton;
	
	private static Logger logger = LoggerFactory.getLogger(MainFrame.class);
	
	/**
	 * 创建图形化界面
	 */
	public void createFrame() {
        JFrame frame = new JFrame("文件上传客户端");
        
        //设置布局和大小
        frame.setLayout(null);
        frame.setSize(1000, 680);
        
        //关闭窗口时也退出应用程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /*
         * 向主界面中添加组件
         */
        //网络状态显示标签
        frame.add(netStateLabel);
       
        //文件列表显示面板
        frame.add(fileListPanel);

        //文件选择按钮
        frame.add(chooseButton);
        
        //上传按钮
        frame.add(uploadButton);

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
