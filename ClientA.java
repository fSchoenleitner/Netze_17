    import java.io.*;
    import java.net.*;
    import java.util.*;

    public class ClientA 
    {
    final private static int PORT = 5005; // arbitrarily assigned port to use

public static void main(String args[]) throws 
IOException
{
    DatagramSocket socket = new DatagramSocket(PORT); // create new connection on that port
    while (true) 
    {
        byte buffer[] = new byte[256]; // data buffer
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length); // new packet containing buffer

        socket.receive(packet);  // look for packet

        String clientBMsg = new String(packet.getData()); // get data from received packet
        InetAddress address = packet.getAddress(); // get address of received packet

        buffer = null;
        String msgString = "Clinet A";
        buffer = msgString.getBytes(); // put String in buffer


        int port = packet.getPort(); // get port of received packet
        packet = new DatagramPacket(buffer, buffer.length, address, port); // create new packet with this data
        socket.send(packet); // send packet back containing new buffer!
        System.out.println("Message Sent");
        socket.close();
       }
}
    }