package FTP;

import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;

/**
 *  this class implements a FTP server using the Apache embedded FTP Server
 */
public class FtpServer {

    public static final String USER = "dummyUser";
    public static final String PASS = "dummyPass";
    public static final String HOME = "/tmp";
    public static final int PORT = 2221;
    public static final String DEFAULT_LISTENER = "default";

    private org.apache.ftpserver.FtpServer server;

    public void start() throws FtpException {
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();

        UserManager userManager = userManagerFactory.createUserManager();
        BaseUser user = new BaseUser();
        user.setName(USER);
        user.setPassword(PASS);
        user.setHomeDirectory(HOME);
        userManager.save(user);

        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(PORT);

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
