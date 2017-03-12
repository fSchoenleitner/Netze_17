import java.io.*;
import java.net.*;

public class TransmiterJava {
	public static void main(String args[]) throws IOException {
		System.out.println("Einage ist: PORT | ANZAHL DER PAKETE |  Host = localhost");
		final int PORT = Integer.parseInt(args[0]); 
		for (int x = 0; x < Integer.parseInt(args[1]); x++) {
			DatagramSocket socket = new DatagramSocket(); // open new socket
			String host = args[2]; //"localhost"
			byte message[] = new byte[256];
			long time = System.currentTimeMillis();
			String msgString = "Message" + x + " sent at " + time;
			if (x == Integer.parseInt(args[1])-1)
				msgString = "This is the last packet";
			message = msgString.getBytes();
			InetAddress address = InetAddress.getByName(host);
			System.out.println("Sending to: " + address);
			DatagramPacket packet = new DatagramPacket(message, message.length, address, PORT);
			socket.send(packet);
			System.out.println("Message" + x + " sent " + time);
			socket.close();
		}
	}
}