package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatSever {

	ServerSocket ss = null;
	boolean start = false;
	
	List<Client> point = new ArrayList<Client>();
	DataOutputStream dos = null;
	
	Socket s = null;
	
	
	public static void main(String[] args) {
		ChatSever cs = new ChatSever();
		cs.start();
	}
	
	public void start (){
		try {
			ss = new ServerSocket(8887);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		start = true ;
		
		while (start)
		{
			try {
				s = ss.accept();
				System.out.println("connected");
				Client client = new Client(s);
				Thread thread = new Thread(client);
				thread.start();
				point.add(client);
				System.out.println("hell");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		
	}

	class Client implements Runnable {
		Socket s = null;
		DataInputStream dis = null;
		boolean isConnected = false;
		
		Client (Socket s){
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				isConnected = true;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		public void send (String str){
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public void run() {
		//	isConnected = true;
			while (isConnected)
			{
				try {
					String str = dis.readUTF();
					System.out.println(str);
				//	System.out.println("hello");
					
					for (int i = 0; i < point.size(); i++)
					{
						Client c = point.get(i);
						c.send(str);
						System.out.println(point.size());
						System.out.println("a message send");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
//				} finally {
//					
//						try {
//							if (dis != null) dis.close();
//							if (dos != null) dos.close();
//							if (s != null) s.close();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					
//				}
				
			}
		}
		
	}
}
