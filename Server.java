package q2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Server extends JFrame {
	
	private JTextArea area;
	private JButton send;
	private DatagramSocket socket;
	private String group = "230.0.0.1";
	private int port = 6666;
	
	public Server() {
		
		// connect to socket
		try {
			socket = new DatagramSocket();
			System.out.println("multiCast server ready");
		} catch (SocketException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		
		// create gui
		setLayout(null);
		area = new JTextArea();
		send = new JButton("send");
		area.setBounds(10, 20, 375, 270);
		send.setBounds(150, 310, 90, 40);
		add(area);
		add(send);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,400);
		setVisible(true);
		
		// when click on send button, send message from server with multicast udp to all clients who listening
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DatagramPacket packet;
				try {
					InetAddress address = InetAddress.getByName(group);
					byte[] buf = new byte[256];
					String msg = area.getText(); // get text from jtextarea
					buf = msg.getBytes();
					packet = new DatagramPacket(buf,buf.length,address,port);
					socket.send(packet);
					
				} catch (IOException e1) {
					e1.printStackTrace();
					System.exit(1);
				}	
			}
		});
	}
	
	public static void main(String[] args) {
		new Server();
	}
}
