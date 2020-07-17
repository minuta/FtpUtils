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

    private org.apache.ftpserver.FtpServer server;

    public void start() throws FtpException {
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        UserManager userManager = userManagerFactory.createUserManager();
        BaseUser user = new BaseUser();
        user.setName("dummyUser");
        user.setPassword("dummyPass");
        user.setHomeDirectory("/tmp");
        userManager.save(user);

        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(2221);

        FtpServerFactory factory = new FtpServerFactory();
        factory.setUserManager(userManager);
        factory.addListener("default", listenerFactory.createListener());

        server = factory.createServer();
        server.start();
    }

    public void stop() {
        server.stop();
    }

    public static void main(String[] args) throws FtpException {
        new FtpServer().start();
    }

}
