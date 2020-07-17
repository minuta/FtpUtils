package FTP;

import org.apache.ftpserver.ftplet.FtpException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 *  testing the FTP client via the Apache MINA embedded FTP Server
 */
public class FtpClientTest {

    static private FtpServer ftpServer;
    static private FtpClient ftpClient;

    public static final String USER = "dummyUser";
    public static final String PASS = "dummyPass";
    public static int PORT = 2221;

    @BeforeClass
    public static void setup() throws FtpException {
        ftpServer = new FtpServer();
        ftpServer.start();
    }

    @After
    public void teardown() throws IOException {
        ftpServer.stop();
        ftpClient.close();
    }

    @Test
    public void ftpTest() throws IOException {
        ftpClient = new FtpClient("localhost", PORT, USER, PASS);
        ftpClient.open();
    }
}
