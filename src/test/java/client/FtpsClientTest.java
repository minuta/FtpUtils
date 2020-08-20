package client;

import FTPS.FtpsServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *  testing the FTPS client with the Apache embedded FTP server
 *
 *  TODO: not ready yet!
 */
public class FtpsClientTest {

    public static final String USER = "admin";
    public static final String PASS = "admin";
    public static int PORT = 2221;

    static private FtpsServer ftpsServer;
    static private FtpsClient ftpsClient;

    public static final String DIR_FILE = ".keep";
    public static final String ROOT_FILE1 = "file1.txt";
    public static final String ROOT_FILE2 = "file2.txt";

    public static final String FTP_RESOURCES_PATH = new File("src/test/resources/FTP_HOME").getPath();  // for a Linux/Win compatibility
    public static final String FTPS_SERVER_SETTINGS = new File("src/test/resources/FTPS_SERVER_SETTINGS").getPath();

    @BeforeClass
    public static void setup() throws FtpException {
//        ftpsServer = new FtpsServer(2221,
//                FTPS_SERVER_SETTINGS + File.separator + "ftpserver.jks",
//                FtpsServer.DEFAULT_PASS,
//                FTPS_SERVER_SETTINGS + File.separator + "user.properties",
//                false);
//        ftpsServer.start();
        ftpsServer = new FtpsServer(PORT,
                FTPS_SERVER_SETTINGS + File.separator + "ftpserver.jks",
                FtpsServer.DEFAULT_PASS,
                USER,
                PASS,
                FTP_RESOURCES_PATH,
                false);
        ftpsServer.startWithSingleUser();
    }

//    @After
//    public void teardown() throws IOException {
//        ftpsClient.close();
//        ftpsServer.stop();           // this method throws a NullPointerException
//    }


    @Test
    public void dummy() {
    }

    @Test
    public void connectionTest() throws IOException {
        ftpsClient = new FtpsClient("localhost", PORT, USER, PASS);
        ftpsClient.open();
        ftpsClient.close();
    }

    @Test
    public void listFilenamesTest() throws IOException {
        ftpsClient = new FtpsClient("localhost", PORT, USER, PASS);
        ftpsClient.open();

        List<String> filenameList = ftpsClient.listFilenames("");
        Assert.assertTrue(filenameList.contains(ROOT_FILE1));
        Assert.assertTrue(filenameList.contains(ROOT_FILE2));
        Assert.assertFalse(filenameList.contains(DIR_FILE));

        filenameList = ftpsClient.listFilenames("DIR1");
        Assert.assertTrue(filenameList.contains(DIR_FILE));

        ftpsClient.close();
//        ftpsServer.stop();
    }
}
