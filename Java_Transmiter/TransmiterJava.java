import java.io.*;
import java.net.*;

public class TransmiterJava {
	public static void main(String args[]) throws IOException {
		System.out.println("Eingabe ist: PORT | ANZAHL DER PAKETE |  Host = localhost");
		final int PORT = Integer.parseInt(args[0]); 
		String host = args[2]; //"localhost"
		InetAddress address = InetAddress.getByName(host);
		byte[] message;
		String num;
		long startTime, endTime, duration;
		for (int x = 0; x < Integer.parseInt(args[1]); x++) {
			DatagramSocket socket = new DatagramSocket(); // open new socket
			System.out.println("Sending to: " + address);
			num=Integer.toString(x);
			startTime = System.nanoTime();
			String msgString = "Message " + num + " sent at " + startTime;				
			message = msgString.getBytes();
			DatagramPacket packet = new DatagramPacket(message, message.length, address, PORT);
			socket.send(packet);
			endTime = System.nanoTime();
			duration=endTime-startTime;
			System.out.println(msgString);
			socket.close();
		}
		sendTerminationString(address, PORT);
	}
	
	private static void sendTerminationString(InetAddress address, int port) throws IOException{
		DatagramSocket socket = new DatagramSocket(); 
		String msgString = "This is the last packet";
		byte[] message = msgString.getBytes();
		DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
		socket.send(packet);
		socket.close();
	}
}