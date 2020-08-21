package AWE;

import ftp.FtpClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 *  this class tests the FTP client on the AWE2 HOST FTP (HOST:=Z/OS)
 */
public class AweFtpTest {

    public static final String USER = "";
    public static final String PASS = "";
    public static final String FTP_SERVER = "AWE2";
    public static final int PORT = 21;

    public static final String FTP_FILENAME = "/home/FE73/file1";
    public static final String FTP_PATH = "/home/FE73";

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
    public void connectionTest() {
        Assert.assertTrue(!ftpClient.isClientConnected());
    }

    @Test
    public void listFilesTest() throws IOException {
//        ftpClient.open();
        List<String> filenameList = ftpClient.listFilenames(FTP_PATH);
        System.out.println("* Listing Home dir: " + filenameList);
        Assert.assertTrue(filenameList.contains(FTP_FILENAME));
    }

}
