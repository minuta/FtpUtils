package ftps;

import ftp.FtpClient;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.IOException;
import java.io.PrintWriter;

public class FtpsClient extends FtpClient {

    private final String server;
    private final int port;
    private final String user;
    private final String password;
    private FTPSClient ftp;

    public FtpsClient(String server, int port, String user, String password) {
        super(server, port, user, password);
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
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

    public boolean isClientConnected() {
        return ftp.isConnected();
    }
}
