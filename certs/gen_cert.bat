:: Choose the openssl.cfg path
set OPENSSL_CONF=C:\Program Files\OpenSSL-Win64\bin\openssl.cfg
:: Create CA
openssl rand -out .rand 1000
openssl genrsa -aes256 -out cakey.pem 4096
:: /C=TW/ST=myprovince/L=mycity/O=myorganization/OU=mygroup/CN=myname
openssl req -new -key cakey.pem -out ca.csr -subj "/C=TW/ST=Taiwan/L=Hsinchu/O=johntool/CN=CA"
openssl x509 -req -days 365 -sha1 -extensions v3_ca -signkey cakey.pem -in ca.csr -out ca.cer

:: Create server.cer
openssl genrsa -aes256 -out server-key.pem 4096
openssl req -new -key server-key.pem -out server.csr -subj "/C=TW/ST=Taiwan/L=Hsinchu/O=johntool/CN=Server"
openssl x509 -req -days 365 -sha1 -extensions v3_req -CA ca.cer -CAkey cakey.pem -CAserial ca.srl -CAcreateserial -in server.csr -out server.cer

:: Create client.cer
openssl genrsa -aes256 -out client-key.pem 4096
openssl req -new -key client-key.pem -out client.csr -subj "/C=TW/ST=Taiwan/L=Hsinchu/O=johntool/CN=Client"
openssl x509 -req -days 365 -sha1 -extensions v3_req -CA ca.cer -CAkey cakey.pem -CAserial ca.srl -CAcreateserial -in client.csr -out client.cer

:: Export server.keystore
openssl pkcs12 -export -clcerts -name server -inkey server-key.pem -in server.cer -out server.keystore
:: Export client.keystore
openssl pkcs12 -export -clcerts -name client -inkey client-key.pem -in client.cer -out client.keystore
:: Export ca.keystore
keytool -importcert -trustcacerts -alias www.johntool.com -file ca.cer -keystore ca.keystore