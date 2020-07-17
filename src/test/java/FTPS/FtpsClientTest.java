package FTPS;

import org.apache.ftpserver.ftplet.FtpException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 *  testing the FTPS client with the Apache embedded FTP server
 */
public class FtpsClientTest {

    public static final String USER = "dummyUser";
    public static final String PASS = "dummyPass";
    public static int PORT = 2221;

    static private FtpsServer ftpsServer;
    static private FtpsClient ftpsClient;

    @BeforeClass
    public static void setup() throws FtpException {
        ftpsServer = new FtpsServer();
        ftpsServer.start();
    }

    @After
    public void teardown() throws IOException {
        ftpsServer.stop();
        ftpsClient.close();
    }

    @Test
    public void ftpTest() throws IOException {
        ftpsClient = new FtpsClient("localhost", PORT, USER, PASS);
        ftpsClient.open();
    }
}
