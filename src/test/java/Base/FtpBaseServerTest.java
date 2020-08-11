package Base;

import FTP.FtpClient;
import FTP.FtpServer;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.ftpserver.ftplet.FtpException;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FtpBaseServerTest {

    static private FtpBaseServer ftpServer;
    static private FtpClient ftpClient;

    public static final String USER = "dummyUser";
    public static final String PASS = "dummyPass";
    public static final String DIR_FILE = ".keep";
    public static final String ROOT_FILE1 = "file1.txt";
    public static final String ROOT_FILE2 = "file2.txt";

    public static final String FTP_RESOURCES_PATH = new File("src/test/resources/FTP_HOME").getPath();  // for a Linux/Win compatibility


    @BeforeClass
    public static void setup() throws FtpException, IOException {
        ftpServer = new FtpBaseServer(USER, PASS, FTP_RESOURCES_PATH, FtpServer.DEFAULT_PORT);
        ftpServer.init();
        ftpServer.start();

        ftpClient = new FtpClient("localhost", FtpServer.DEFAULT_PORT, USER, PASS);
        ftpClient.open();
    }

    @After
    public void teardown() throws IOException {
        ftpServer.stop();
        ftpClient.close();
    }


    @Test
    public void connectionTest() {
    }


    @Test
    public void listFilesTest() throws IOException {

        List<String> filenameList = ftpClient.listFilenames("");
        Assert.assertTrue(filenameList.contains(ROOT_FILE1));
        Assert.assertTrue(filenameList.contains(ROOT_FILE2));
        Assert.assertFalse(filenameList.contains(DIR_FILE));

        filenameList = ftpClient.listFilenames("DIR1");
        Assert.assertTrue(filenameList.contains(DIR_FILE));
    }

    @Test
    public void listPathTest() throws IOException {
        FTPFile[] fileList = ftpClient.listPath("");
        boolean foundFilename = Arrays.stream(fileList).anyMatch(entry -> entry.getName().contains("file1.txt"));
        Assert.assertTrue(foundFilename);
    }
}
