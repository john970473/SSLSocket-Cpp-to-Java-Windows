#include <iostream>
#include <openssl/ssl.h>
class SSLClient {
public:
	SSLClient();
	SSLClient(std::string, std::string, std::string, std::string, std::string);

	//bool isConnected();
	bool start();
	void stop();					// Free the resouces

	bool writeUTF(std::string);		// Send message to server
	bool readUTF(std::string &ret);			// Receive message from server

	bool writeInt(int);				// Send Integer to serverB
	bool readInt(int *ret);					// Receive Integer from server

private:
	std::string conn_str;
	std::string ca_pem;
	std::string cert_pem;
	std::string key_pem;
	std::string passwd;

	SSL *ssl;
	bool transferData(char *, int, bool);
};
