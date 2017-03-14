import java.io.*;
import java.net.*;

public class TransmiterJava {
	public static void main(String args[]) throws IOException {
		System.out.println("Eingabe ist: PORT | ANZAHL DER PAKETE |  Host = localhost");
		final int PORT = Integer.parseInt(args[0]); 
		String host = args[2]; //"localhost"
		InetAddress address = InetAddress.getByName(host);
		byte[] message;
		String msgString;
		long startTime, endTime, duration;
		DatagramSocket socket = new DatagramSocket();
		for (int x = 0; x < Integer.parseInt(args[1]); x++) {
			System.out.println("Sending to: " + address);
			msgString="Message "+x+" sent at ";
			startTime = System.currentTimeMillis();
			msgString = msgString+startTime;			
			message = msgString.getBytes();
			DatagramPacket packet = new DatagramPacket(message, message.length, address, PORT);
			socket.send(packet);
			endTime = System.currentTimeMillis();
			duration=endTime-startTime;
			System.out.println(msgString);
		}
		sendTerminationString(address, PORT, socket);
	}
	
	private static void sendTerminationString(InetAddress address, int port, DatagramSocket socket) throws IOException{
		String msgString = "EndOfTransmission";
		byte[] message = msgString.getBytes();
		DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
		socket.send(packet);
		socket.close();
	}
}