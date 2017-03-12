import java.io.*;
import java.net.*;
import java.util.*;

public class ReceiverJava {
	final private static int PORT = 1234; // arbitrarily assigned port to use

	public static void main(String args[]) throws IOException {
		DatagramSocket socket = new DatagramSocket(PORT);
		for (int x = 0; x < 100; x++) {
			byte buffer[] = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length); 
			socket.receive(packet);
			String clientBMsg = new String(packet.getData());
			System.out.println(clientBMsg);
			InetAddress address = packet.getAddress();
			buffer = null;
			String msgString = "Clinet A";
			buffer = msgString.getBytes();
		}
		socket.close();
	}
}