import java.io.*;
import java.net.*;

public class TransmiterJava {
	final private static int PORT = 5005; // arbitrarily assigned port - same as server
//TODO: man könnte noch die anzahl der gesendenten parkete regelen in dem wir eine eingabe über die args machen.
	public static void main(String args[]) throws IOException {
		DatagramSocket socket = new DatagramSocket(); // open new socket
		for (int x = 0; x < 100; x++) {
			String host = "localhost";
			byte message[] = new byte[256];
			String msgString = "Message" + x + " sent";
			message = msgString.getBytes(); // put String in buffer

			InetAddress address = InetAddress.getByName(host);
			System.out.println("Sending to: " + address);
			DatagramPacket packet = new DatagramPacket(message, message.length, address, PORT); // creates new packet with message, message.length, addr and port);
			socket.send(packet); // send packet
			System.out.println("Message" + x + " sent");
		}
		socket.close();
	}
}