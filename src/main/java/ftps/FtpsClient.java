package ftps;

import ftp.FtpClient;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *  An example implementation of a FTPS client via Apache Commons Net Library
 */
public class FtpsClient extends FtpClient {

    private FTPSClient ftp;

    public FtpsClient(String server, int port, String user, String password) {
        super(server, port, user, password);
    }

    public void open() throws IOException {
        ftp = new FTPSClient();

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

    public boolean isClientConnected() {
        return ftp.isConnected();
    }
}
