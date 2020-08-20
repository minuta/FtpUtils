package AWE;

import ftps.FtpsClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 *  Testing the FTPS client on the AWE2 FTP Server
 */
public class AweFtpsTest {
    public static final String USER = "";
    public static final String PASS = "";
    public static final String FTP_SERVER = "AWE2";
    public static final int PORT = 2221;

    FtpsClient ftpsClient;

    @Before
    public void setup() throws IOException {
        ftpsClient = new FtpsClient(FTP_SERVER, PORT, USER, PASS);
        ftpsClient.open();
    }

    @After
    public void tearDown () throws IOException {
    }

    @Test
    public void connectionTest() {
    }

}
