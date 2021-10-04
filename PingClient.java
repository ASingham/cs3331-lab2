import java.io.*;
import java.net.*;
import java.util.*;

public class PingClient {
    
    public static boolean ping(String ip) throws Exception {
        InetAddress address = InetAddress.getByName(ip);
        if (address.isReachable(600)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {

        String ip = args[0];
        InetAddress host = InetAddress.getByName(ip);
        int port = Integer.parseInt(args[1]);
        DatagramSocket socket = new DatagramSocket(port);

        for (int i = 0; i < 15; i++) {

            byte[] buf = {};
            DatagramPacket reply = new DatagramPacket(buf, buf.length, host, port);

            boolean sent = false;
            long startTime = System.nanoTime();
            socket.send(reply);
            long endTime = System.nanoTime();

            long rtt_time = (endTime - startTime)/1000000;
            if (rtt_time < 600) {
                sent = true;
            }

            int sequence_number = 3331 + i;
            String rtt = "time out";
            if (sent) {
                rtt = Long.toString(rtt_time);
            }
            System.out.println("ping to " + ip + ", seq = " + sequence_number + ", rtt = " + rtt + " ms");
        }
    }
}
