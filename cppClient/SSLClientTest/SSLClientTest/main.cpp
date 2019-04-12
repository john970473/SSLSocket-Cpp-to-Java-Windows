/*
 *  main.c
 *  OpenSSL
 *
 *  Created by Thirumal Venkat on 18/05/16.
 *  Copyright © 2016 Thirumal Venkat. All rights reserved.
 */
#include <iostream>
#include <string>
#include "sslClient.h"
 /*
  * Print the usage of the current command line tool
  */
void usage() {
	fprintf(stderr, "Usage: ./a.out "
		/* 2 */ "(<server_ip>:<server_port>) "
		/* 3 */ "<CAfile_pem> "
		/* 4 */ "<cert_pem> "
		/* 5 */ "<key_pem> "
		/* 6 */ "<cert_passwd>\n");
}

int main() {
	SSLClient client("172.16.66.102:1234", "../../../certs/ca.cer", "../../../certs/client.cer", "../../../certs/client-key.pem", "111111");
	client.start();
	client.writeUTF("hello server");
	std::cout << "send: " << "hello server" << std::endl;
	std::string msg("");
	client.readUTF(msg);
	std::cout << "recv: " << msg << std::endl;

	client.writeInt(123456);
	std::cout << "send: " << 123456 << std::endl;
	int revint;
	client.readInt(&revint);
	std::cout << "recv int: " << revint << std::endl;
	client.stop();

	system("pause");
}
