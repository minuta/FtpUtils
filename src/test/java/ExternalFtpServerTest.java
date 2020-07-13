import org.junit.Test;

import java.io.IOException;

/**
 *  this class tests if it can open a connection to the running FTP server
 */
public class ExternalFtpServerTest {

    public static final String USER = "admin";
    public static final String PASS = "admin";
    public static final String FTP_SERVER = "localhost";
    public static final int PORT = 2121;

    @Test
    public void checkExternalFtpServer() throws IOException {
        FtpClient ftpClient = new FtpClient(FTP_SERVER, PORT, USER, PASS);
        ftpClient.open();

    }
}
