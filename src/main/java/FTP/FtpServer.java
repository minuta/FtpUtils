package FTP;

import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;

/**
 *  An example implementation of a FTP server using the Apache embedded FTP Server
 */
public class FtpServer {

    private org.apache.ftpserver.FtpServer server;

    private final String user;
    private final String pass;
    private final String homeDir;
    private final int port;

    public static final String USER = "dummyUser";
    public static final String PASS = "dummyPass";
    public static final String FTP_HOME = "/tmp";
    public static final int DEFAULT_PORT = 2221;
    public static final String DEFAULT_LISTENER = "default";


    public FtpServer() {
        this(USER, PASS, FTP_HOME, DEFAULT_PORT);
    }

    public FtpServer(String user, String pass, String homeDir, int port) {
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
        userManager.save(user);
    }

    public void start() throws FtpException {
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        UserManager userManager = userManagerFactory.createUserManager();
        createUser(userManager, user, pass, homeDir);

        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(port);

        FtpServerFactory factory = new FtpServerFactory();
        factory.setUserManager(userManager);
        factory.addListener(DEFAULT_LISTENER, listenerFactory.createListener());

        server = factory.createServer();
        server.start();
    }

    public void stop() {
        server.stop();
    }


}
