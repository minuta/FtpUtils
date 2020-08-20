package AWE;

import client.FtpsClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AweFtpsTest {
    public static final String USER = "FE73";
    public static final String PASS = "P4r4kl3t";
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
