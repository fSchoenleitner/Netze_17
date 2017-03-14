import java.io.*;
import java.net.*;
import java.util.*;

public class ReceiverJava {
	public static void main(String args[]) throws IOException {
		int port=Integer.parseInt(args[0]);
		int counter=0;
		DatagramSocket socket = new DatagramSocket(port);
		BufferedWriter bf=initWriter(args[1]);
		while (true) {
			DatagramPacket packet=receive(socket, bf);
			String clientBMsg = new String(packet.getData());
			clientBMsg=clientBMsg.trim();
			if(clientBMsg.equals("EndOfTransmission")) break;
			counter++;
			System.out.println(clientBMsg);
			InetAddress address = packet.getAddress();
		}
		socket.close();
		bf.close();
		System.out.println(counter+" packets received");
	}
	
	private static DatagramPacket receive(DatagramSocket socket, BufferedWriter bf) throws IOException{
		byte[] buffer = new byte[256];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length); 
		long startTime = System.nanoTime();
		socket.receive(packet);
		long endTime = System.nanoTime();
		long duration=endTime-startTime;
		String durStr=Long.toString(duration);
		bf.write(durStr, 0, durStr.length());
		bf.newLine();
		return packet;
	}
	
	private static BufferedWriter initWriter(String filename) throws IOException{
		File f=new File(filename);
		FileWriter osw=new FileWriter(f);
		BufferedWriter bf=new BufferedWriter(osw);
		bf.write("Receiving duration [ns]", 0, 23);
		bf.newLine();
		return bf;
	}
}