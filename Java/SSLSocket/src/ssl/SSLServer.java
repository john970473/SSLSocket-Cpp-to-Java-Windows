package ssl;

import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManagerFactory;

public class SSLServer {
	private SSLServerSocket sslServerSocket;

	//Server will use ca-trust.keystore and server.keystore
	public void init(int port, String trustKeystorePath, String keystorePath, String keystorePassword) throws Exception {
		//SSLContext context = SSLContext.getInstance("SSLv3");
		SSLContext context = SSLContext.getInstance("TLSv1.2");
		
		//client cert
		KeyStore keystore = KeyStore.getInstance("pkcs12");
		FileInputStream keystoreFis = new FileInputStream(keystorePath);
		keystore.load(keystoreFis, keystorePassword.toCharArray());
		//trust ca
		KeyStore trustKeystore = KeyStore.getInstance("jks");
		FileInputStream trustKeystoreFis = new FileInputStream(trustKeystorePath);
		trustKeystore.load(trustKeystoreFis, keystorePassword.toCharArray());
		
		//private key
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
		kmf.init(keystore, keystorePassword.toCharArray());

		//trust key
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
		tmf.init(trustKeystore);
		
		//initialize SSL context
		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		//initialize SSLSocket
		sslServerSocket = (SSLServerSocket)context.getServerSocketFactory().createServerSocket(port);
		//set SSLServerSocket only can be connected from ClientAuth
		sslServerSocket.setNeedClientAuth(true);
	}
	
	public Socket accept() throws Exception{
		return sslServerSocket.accept();
	}
	
	
}