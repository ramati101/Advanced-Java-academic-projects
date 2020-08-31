package q2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Client extends JFrame{
	
	private JTextArea area;
	private JButton sign, remove, clear;
	private MulticastSocket socket;
	private InetAddress group;
	private boolean reciving = true;
	private static String groupIP;
	private int port;
	
	public Client() {
		port = 6666;
		
		// connect multicast socket
		try {
			socket = new MulticastSocket(port);
			group = InetAddress.getByName(groupIP);
		} catch (IOException e2) {
			e2.printStackTrace();
			System.exit(1);
		}
	
		// create client gui
		setLayout(null);
		area = new JTextArea();
		sign = new JButton("sign up to server");
		remove = new JButton("remove from server");
		clear = new JButton("clear");
		area.setBounds(10, 50, 375, 250);
		sign.setBounds(20, 10, 160, 35);
		remove.setBounds(200, 10, 160, 35);
		clear.setBounds(155, 315, 85, 30);
		add(area);
		add(sign);
		add(remove);
		add(clear);
		
		// when client sign up to multicast server, join the group
		sign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					socket.joinGroup(group);
				} catch (IOException e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		});
		
		// remove client from group multicast server
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					socket.leaveGroup(group);
				} catch (IOException e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		});

		clear.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				area.setText("");
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,400);
		setVisible(true);
		
		getBroadcast();
			
	}
	
	// receivd messages from server, add them current date and time and print them to client
	private void getBroadcast() {
		String pattern = "dd/MM/yyyy HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		DatagramPacket packet;
		while(reciving) {
			byte[] buf = new byte[256];
			packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			buf = packet.getData();
			int len = packet.getLength();
			String received = (new String(buf)).substring(0, len);
			String date = simpleDateFormat.format(new Date());
			area.append(date+"  -  "+received+"\n");	
		}
	}

	// ask for server computer name and make a new client gui
	public static void main(String[] args) {
		System.out.println("please write the copmuter name of the server:");
		Scanner sc = new Scanner(System.in);
		groupIP = sc.next();
		new Client();
	}
}
