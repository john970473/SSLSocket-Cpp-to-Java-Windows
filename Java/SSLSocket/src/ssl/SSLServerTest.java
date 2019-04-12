package ssl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SSLServerTest {
	public static void main(String[] args) throws Exception {
		SSLServer server = new SSLServer();
		server.init(1234,"../../certs/ca.keystore","../../certs/server.keystore","111111");
		System.out.println("SSLServer initialized.");

		String msg;
	
		while(true) {
			Socket socket = server.accept();
			msg = new String();
			DataInputStream in = new DataInputStream(socket.getInputStream());
			msg = in.readUTF();
			System.out.println("Received : " + msg );
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF("hello client");
			System.out.println("Send : hello client");
			int recvnum = in.readInt();
			System.out.println("Received Int : " + recvnum );
			int num = 654321;
			out.writeInt(num);
			System.out.println("Send Int : " + num );
		}
		
	}
}
