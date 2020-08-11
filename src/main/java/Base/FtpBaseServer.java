package Base;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;

/**
 *  An example implementation of a FTP server using the Apache embedded FTP Server
 */
public class FtpBaseServer {

    protected FtpServer server;
    public static final String DEFAULT_LISTENER = "default";

    protected String user;
    protected String pass;
    protected String homeDir;
    protected int port;

    public FtpBaseServer(String user, String pass, String homeDir, int port) {
        this.user = user;
        this.pass = pass;
        this.homeDir = homeDir;
        this.port = port;
    }

    public void createUser(UserManager userManager, String userName, String userPass, String userHomeDir) throws FtpException {
        BaseUser user = new BaseUser();
        user.setName(userName);
        user.setPassword(userPass);
        user.setHomeDirectory(userHomeDir);
        user.setEnabled(true);
        userManager.save(user);
    }

    public void init() throws FtpException {
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        UserManager userManager = userManagerFactory.createUserManager();
        createUser(userManager, user, pass, homeDir);

        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(port);

        FtpServerFactory factory = new FtpServerFactory();
        factory.setUserManager(userManager);
        factory.addListener(DEFAULT_LISTENER, listenerFactory.createListener());

        server = factory.createServer();
    }

    public void start() throws FtpException {
        server.start();
    }

    public void stop() {
        server.stop();
    }

}
