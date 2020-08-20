package FTP;

import org.junit.After;
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
        List<String> filenameList = ftpClient.listFilenames("/home/FE73");
        System.out.println(filenameList);
//        Assert.assertTrue(filenameList.contains(FTP_FILENAME));
    }
}
