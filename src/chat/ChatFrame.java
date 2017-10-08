package chat;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class ChatFrame extends JFrame {

	private Socket ss = null;
	private TextField tf = new TextField();
	private TextArea ta = new TextArea();
	private DataOutputStream dos = null;
	
	private DataInputStream dis = null;
	private boolean isConnected = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChatFrame().init();
	}
	
	public void init (){
	//	setSize(200, 200);
		setLocation(400, 250);
		setSize(400,  400);
	    setTitle("myChat");
	    add(tf, BorderLayout.SOUTH);
	    add(ta, BorderLayout.NORTH);
	    pack();
	    this.addWindowListener(new WindowAdapter() {
	    	public void windowClosing ()
	    	{
	    		disconnect();
	    		System.exit(0);
	    	}
		});
	    tf.addActionListener(new TfListener());
	//    setDefaultCloseOperation(EXIT_ON_CLOSE); 用上面的代替
	    setVisible(true);
	    connect();
	    
	    new Thread (new RecieveThread()).start();
	}
	
	public void connect (){
		try {
			 ss = new Socket("127.0.0.1", 8887);
			 dos = new DataOutputStream(ss.getOutputStream());
			 dis = new DataInputStream(ss.getInputStream());
			 isConnected = true;
			System.out.println("connected");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect (){
		try {
			dos.close();
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private class TfListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = tf.getText();
			ta.setText(s);
			tf.setText("");
			try {
			  //  dos = new DataOutputStream(ss.getOutputStream());
				dos.writeUTF(s);
				dos.flush();
		//		System.out.println("se");
//				dos.close();  // 这个一关会断了连接
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private class RecieveThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("jinrule");
			try {
				while (isConnected){
					System.out.println("hehela");
//					dis = new DataInputStream(ss.getInputStream());
					String str = dis.readUTF();
					System.out.println("dishanbula ");
					ta.setText(ta.getText() + str + '\n');
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		}
		
	}

}
