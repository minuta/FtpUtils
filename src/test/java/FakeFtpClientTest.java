import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import java.io.IOException;

/**
 *  this class tests if the implemented FTP client can work with a FakeFtpServer
 */
public class FakeFtpClientTest {

    private FakeFtpServer fakeFtpServer;

    private FtpClient ftpClient;

    public static final String USER = "ftpUser";
    public static final String PASS = "ftpPass";
    public static final String FTP_SERVER = "localhost";
    public static final String USER_HOME = "/data";
    public static final String FTP_FILENAME = "reamde.txt";
    public static final String FTP_FILENAME_CONTENT = "some dummy content of the readme.txt ...";

    @Before
    public void setup() throws IOException {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount(USER, PASS, USER_HOME));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry(USER_HOME));
        fileSystem.add(new FileEntry(USER_HOME + "/" + FTP_FILENAME, FTP_FILENAME_CONTENT));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);
        fakeFtpServer.start();
    }

    @After
    public void teardown() throws IOException {
        ftpClient.close();
        fakeFtpServer.stop();
    }

    @Test
    public void dummyTest() throws IOException {
        ftpClient = new FtpClient(FTP_SERVER, fakeFtpServer.getServerControlPort(), USER, PASS);
        ftpClient.open();
    }
}
