# FTP/FTPS server/client implementations based on the Apache Commons Net

### About
* FTP server-client implementation
* FTPS server-client implementation
* Integrationtests use besides the self-implemented FTP/FPTS servers also the MockFtpServer (http://mockftpserver.sourceforge.net/) for testing FTP functionality
and external FTP servers, e.g. the Apache FTP Server.

### Apache FTP Server

Running a basic stand-alone server (used in the project integration-tests) :

`$ bin/ftpd.bat res/conf/ftpd-typical.xml`


for details see: https://mina.apache.org/ftpserver-project/