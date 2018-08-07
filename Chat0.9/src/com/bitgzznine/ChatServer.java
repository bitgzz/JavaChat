package com.bitgzznine;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;

public class ChatServer {
	boolean start = false;
	ServerSocket serverSocket = null;

	public static void main(String[] args) {
		new ChatServer().started();
		
	}
	
	public void started() {

		//启动服务
		try {		
			 serverSocket = new ServerSocket(8888);
			 start = true;
		}catch (BindException e) {
		System.out.println("端口使用中.....！");
		System.out.println("请您关掉想关服务然后重新运行服务器。");
		}
		catch (IOException e) {
			e.printStackTrace();
	}
		
		
		try {
			//服务接受客户端的链接
			while(start) {				
		 
				 Socket socket = serverSocket.accept();
				 Client client = new Client(socket);
System.out.println("a client connected!");
				new Thread(client).start();
//				dataInputStream.close();				
			}
		}catch (IOException e) {
			e.printStackTrace();		
		
		}finally {	
			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
		
}
		

	class Client implements Runnable{	
		private Socket socket;
		private DataInputStream dataInputStream = null;
		private boolean bConnectde = false;
		public Client (Socket socket) {
			this.socket=socket;
			try {
				dataInputStream = new DataInputStream(socket.getInputStream());
				bConnectde = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		@Override
		public void run() {
			
			try {
				while(bConnectde) {
					String string = dataInputStream.readUTF();
					//输出来
					System.out.println(string);
				
			} 
		
			}catch (EOFException e) {
				System.out.println("client close！");
			}catch (IOException e) {
				e.printStackTrace();		
			
			}finally {	
				try {
					if (dataInputStream!= null) {
						dataInputStream.close();
					}
					if (socket != null) {
						socket.close();	
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
				
		}
	}
}

