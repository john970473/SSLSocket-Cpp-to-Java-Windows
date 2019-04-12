package ssl;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.net.ssl.SSLSocket;

public class SSLClientTest {
	public static void main(String[] args) throws Exception {
		SSLClient client = new SSLClient();
		client.init("127.0.0.1", 1234, "../../certs/ca.keystore", "../../certs/client.keystore", "111111");
		System.out.println("SSLClient initialized.");
		
		SSLSocket ssl = client.connect();
		
		String hello = "hello server!";
		
		DataOutputStream out = new DataOutputStream(ssl.getOutputStream());
		out.writeUTF(hello);
		System.out.println("Send : " + hello);
		DataInputStream in = new DataInputStream(ssl.getInputStream());
		String recv;
		recv = in.readUTF();
		
		System.out.println("Receive : " + recv);
		
		int num = 123456;
		
		out.writeInt(num);
		
		System.out.println("Send Int : " + num);
		
		int recvnum = in.readInt();
		
		System.out.println("Receive Int : " + recvnum);
		
		ssl.close();
	}
}
