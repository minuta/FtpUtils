import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ExternalFtpsServerTest {
    public static final String USER = "admin";
    public static final String PASS = "admin";
    public static final String FTP_SERVER = "localhost";
    public static final int PORT = 2121;
    public static final String FTP_FILENAME = "README.txt";
    public static final String USER_HOME = "/";


    FtpsClient ftpsClient;

    @Before
    public void setup() throws IOException {
        ftpsClient = new FtpsClient(FTP_SERVER, PORT, USER, PASS);
        ftpsClient.open();
    }

    @After
    public void tearDown () throws IOException {
        ftpsClient.close();
    }

    @Test
    public void dummyTest() {

    }


    //    @Test
//    public void listFilesTest() throws IOException {
//        ftpsClient.open();
//        List<String> filenameList = ftpsClient.listFilenames(USER_HOME);
//        Assert.assertTrue(filenameList.contains(FTP_FILENAME));
//    }
}
