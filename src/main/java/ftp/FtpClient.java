package ftp;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 *  An example implementation of a FTP client via Apache Commons Net Library
 */
public class FtpClient {

    protected String server;
    protected int port;
    protected String user;
    protected String password;
    private FTPClient ftp;

    public FtpClient(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public void open() throws IOException {
        ftp = new FTPClient();

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        ftp.login(user, password);
    }

    public void close() throws IOException {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            ftp.disconnect();
        }
    }

    public List<String> listFilenames (String path) throws IOException {
        return Arrays.asList(ftp.listNames(path));
    }

    public FTPFile[] listPath(String pathname) throws IOException {
        return ftp.listFiles(pathname);
    }

    public void downloadFile(String source, String destination) throws IOException {
        FileOutputStream out = new FileOutputStream(destination);
        ftp.retrieveFile(source, out);
    }

    public boolean isClientConnected() {
        return ftp.isConnected();
    }
}