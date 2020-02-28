keytool -genkeypair -keystore uuiServer.jks -alias uuiServer -keypass Aa123456 -storepass Aa123456  -keyalg RSA -keysize 2048  -validity 3650 -dname "CN=Usecaseui Server, OU=Development, O=ChinaMobile, L=Beijing, C=cn"


3650 – 10 years validity
Development – Organization unit
ChinaMobile – Organization
Beijing- City
cn – Country code


uuiServer.jks – name of keystore
Aa123456 - password
