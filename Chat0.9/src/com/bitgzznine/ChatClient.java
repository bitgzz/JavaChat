package com.bitgzznine;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ChatClient extends Frame{
	
	Socket socket = null;
	DataOutputStream dataOutputStream = null;
	
	//定义TextField
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();

	public static void main(String[] args) {
		new ChatClient().launchFrame();
		

	}
	
	public void launchFrame() {
		setLocation(400,300);
		this.setSize(300,300);
		//在窗口中添加写入框
		add(tfTxt, BorderLayout.SOUTH);
		//在窗口中添加聊天框
		add(taContent,BorderLayout.NORTH);
		pack();
		//关闭窗口功能的实现
		this.addWindowListener(new WindowAdapter() {

			@Override
			//窗口关闭方法
			public void windowClosing(WindowEvent e) {
				
				// 调用清理资源的方法
				disconnect();
				//系统推出
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TFlistener());
		setVisible(true);	
		connect();
	}
	
	//连接方法
	public void connect() {
		try {
		 socket = new Socket("127.0.0.1", 8888);
		 //拿到流初始化保存好
		 dataOutputStream = new DataOutputStream(socket.getOutputStream());
System.err.println("connected!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
	
	//写一个清理自源的方法
	public void disconnect() {
		try {
			dataOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//创建文本区域
	private class TFlistener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//先拿tfTxt的内容,然后去掉空格
			String string = tfTxt.getText().trim();
			//然后在写去Content中
			taContent.setText(string);
			//当发送完之后去掉输入框中的内容
			tfTxt.setText("");
			//我们在窗口中往外发字符串
			try {
//				//拿到流
//				DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
				
				
				//往出写
				dataOutputStream.writeUTF(string);
				dataOutputStream.flush();
				
					
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}

}
