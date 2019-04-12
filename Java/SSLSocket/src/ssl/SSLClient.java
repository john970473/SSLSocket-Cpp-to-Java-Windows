package ssl;

import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class SSLClient {
	private SSLSocket sslSocket;
	
	//Client will use ca-trust.keystore and client.keystore
	public void init(String host, int port, String trustKeystorePath, String keystorePath, String keystorePassword) throws Exception {
		SSLContext context = SSLContext.getInstance("TLS");
		//client cert
		KeyStore clientKeystore = KeyStore.getInstance("pkcs12");
		FileInputStream keystoreFis = new FileInputStream(keystorePath);
		clientKeystore.load(keystoreFis, keystorePassword.toCharArray());
		//trust ca
		KeyStore trustKeystore = KeyStore.getInstance("jks");
		FileInputStream trustKeystoreFis = new FileInputStream(trustKeystorePath);
		trustKeystore.load(trustKeystoreFis, keystorePassword.toCharArray());
		
		//private key
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
		kmf.init(clientKeystore, keystorePassword.toCharArray());

		//trust keystore
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
		tmf.init(trustKeystore);
		
		//initialize SSL context
		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		
		sslSocket = (SSLSocket)context.getSocketFactory().createSocket(host, port);
	}
	
	public SSLSocket connect() throws Exception {
		return sslSocket;
	}
}