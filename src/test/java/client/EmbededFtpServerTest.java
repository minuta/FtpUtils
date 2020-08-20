package client;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.ftpserver.ftplet.FtpException;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.FtpBaseServer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *  testing the FTP client via the Apache MINA embedded FTP Server
 */
public class EmbededFtpServerTest {

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
        ftpServer = new FtpBaseServer(USER, PASS, FTP_RESOURCES_PATH, FTP.FtpServer.DEFAULT_PORT);
        ftpServer.init();
        ftpServer.start();

        ftpClient = new FtpClient("localhost", FTP.FtpServer.DEFAULT_PORT, USER, PASS);
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
    public void listFiles2Test() throws IOException {
        ftpClient.open();
        List<String> filenameList = ftpClient.listFilenames("DIR1");
        System.out.println(filenameList);
//        Assert.assertTrue(filenameList.contains(FTP_FILENAME));
    }


    @Test
    public void listPathTest() throws IOException {
        FTPFile[] fileList = ftpClient.listPath("");
        Boolean foundFilename = Arrays.stream(fileList).anyMatch(entry -> entry.getName().contains("file1.txt"));
        Assert.assertTrue(foundFilename);
    }


    @Test
    public void listFiles() throws IOException {
        FTPFile[] fileList = ftpClient.listPath("DIR1");
        for (FTPFile file: fileList ) {
            System.out.println(file.getName());
            System.out.println("RAW String: " + file.getRawListing());
        }
    }
}
