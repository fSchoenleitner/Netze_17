import java.io.*;
import java.net.*;
import java.util.*;

public class ReceiverJava {
	public static void main(String args[]) throws IOException {
		int port=Integer.parseInt(args[0]);
		byte[] buffer;
		DatagramSocket socket = new DatagramSocket(port);
		while (true) {
			buffer = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length); 
			socket.receive(packet);
			String clientBMsg = new String(packet.getData());
			clientBMsg=clientBMsg.trim();
			if(clientBMsg.equals("EndOfTransmission")) break;
			System.out.println(clientBMsg);
			InetAddress address = packet.getAddress();
		}
		socket.close();
	}
}