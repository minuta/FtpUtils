package FTPS;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

import java.io.File;

public class FtpsServer {

    private org.apache.ftpserver.FtpServer server;

    // TODO: make a constructor with the connection-mode (implicite/explicite)

    public void start() throws FtpException {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();

        // set the port of the listener
        factory.setPort(2221);

        // define SSL configuration
        SslConfigurationFactory ssl = new SslConfigurationFactory();
        ssl.setKeystoreFile(new File("src/main/resources/ftpserver.jks"));
        ssl.setKeystorePassword("password");

        // set the SSL configuration for the listener
        factory.setSslConfiguration(ssl.createSslConfiguration());
        factory.setImplicitSsl(false);

        // replace the default listener
        serverFactory.addListener("default", factory.createListener());
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File("src/main/resources/user.properties"));
        serverFactory.setUserManager(userManagerFactory.createUserManager());

        // start the server
        FtpServer server = serverFactory.createServer();
        server.start();
    }

    public void stop() {
        server.stop();
    }

}
