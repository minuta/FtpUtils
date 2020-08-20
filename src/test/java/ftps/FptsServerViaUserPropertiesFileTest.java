package ftps;

import org.junit.Assert;
import org.junit.Test;
import server.FtpsBaseServer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *   Integrationtests with an embedded Apache Server.
 *
 *   TODO: setup() in the BeforeClass should be fixed! The implementation is not ready yet!
 */
public class FptsServerViaUserPropertiesFileTest {
    public static final String USER = "admin";
    public static final String PASS = "admin";
    public static int PORT = 2221;

    static private FtpsBaseServer ftpsServer;
    static private FtpsClient ftpsClient;

    public static final String DIR_FILE = ".keep";
    public static final String ROOT_FILE1 = "file1.txt";
    public static final String ROOT_FILE2 = "file2.txt";

    public static final String FTP_RESOURCES_PATH = new File("src/test/resources/FTP_HOME").getPath();  // for a Linux/Win compatibility
    public static final String FTPS_SERVER_SETTINGS = new File("src/test/resources/FTPS_SERVER_SETTINGS").getPath();

//    @BeforeClass
//    public static void setup() throws FtpException {
//        ftpsServer = new FtpsBaseServer(2221,
//                FTPS_SERVER_SETTINGS + File.separator + "ftpserver.jks",
//                FtpsServer.DEFAULT_PASS,
//                FTPS_SERVER_SETTINGS + File.separator + "user.properties",
//                false);
//        ftpsServer.startWithUserPropertiesFile();
//
//    }

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

        System.out.println("Working Directory = " + System.getProperty("user.dir"));


        List<String> filenameList = ftpsClient.listFilenames("FTP_HOME");
        System.out.println(filenameList);
        Assert.assertTrue(filenameList.contains(ROOT_FILE1));
//        Assert.assertTrue(filenameList.contains(ROOT_FILE2));
//        Assert.assertFalse(filenameList.contains(DIR_FILE));
//
//        filenameList = ftpsClient.listFilenames("DIR1");
//        Assert.assertTrue(filenameList.contains(DIR_FILE));

        ftpsClient.close();
//        ftpsServer.stop();
    }
}
