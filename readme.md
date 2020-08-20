# FTP/FTPS server/client implementation

### About
* FTP/FTPS Server is implemented via MINA FTP Server 1.1.x
* FTP/FTPS clients are implemented via Apache Commons Net 3.7
 * Integrationtests use besides the self-implemented FTP/FPTS servers also the MockFtpServer (http://mockftpserver.sourceforge.net/) for testing FTP functionality
and external FTP servers, e.g. the Apache FTP Server.

### Apache FTP Server

Running a basic stand-alone server (used in the project integration-tests) :

`$ bin/ftpd.bat res/conf/ftpd-typical.xml`


for details see: https://mina.apache.org/ftpserver-project/