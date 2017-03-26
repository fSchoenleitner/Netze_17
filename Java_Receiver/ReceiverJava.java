import java.io.*;
import java.net.*;
import java.util.*;

public class ReceiverJava {
	public static void main(String args[]) throws IOException {
		int port=Integer.parseInt(args[0]);
		int counter=0;
		byte[] buffer;
		String adrStr;
		DatagramSocket socket = new DatagramSocket(port);
		while (true) {
			buffer = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, 256); 
			socket.receive(packet);
			String clientBMsg = new String(packet.getData());
			clientBMsg=clientBMsg.trim();
			if(clientBMsg.equals("EndOfTransmission")) break;
			counter++;
			
			InetAddress address = packet.getAddress();
			adrStr=address.getHostAddress();
			System.out.println("Received Message: \""+clientBMsg+"\" from: "+adrStr+":"+Integer.toString(port));
			System.out.println();
		}
		socket.close();
		System.out.println(counter+" packets received");
	}
	
}