package FTP;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 *  this class tests if it can open a connection to the locally running FTP server
 *  (using e.g. the the Apache FTP Server)
 */
public class ExternalFtpServerTest {

    public static final String USER = "admin";
    public static final String PASS = "admin";
    public static final String FTP_SERVER = "localhost";
    public static final int PORT = 2121;
    public static final String FTP_FILENAME = "README.txt";
    public static final String USER_HOME = "/";


    FtpClient ftpClient;

    @Before
    public void setup() throws IOException {
        ftpClient = new FtpClient(FTP_SERVER, PORT, USER, PASS);
        ftpClient.open();
    }

    @After
    public void tearDown () throws IOException {
        ftpClient.close();
    }


    @Test
    public void listFilesTest() throws IOException {
        ftpClient.open();
        List<String> filenameList = ftpClient.listFilenames(USER_HOME);
        Assert.assertTrue(filenameList.contains(FTP_FILENAME));
    }
}
