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
    private final int port;
    private final String keystorePassword;

    public static final String KEYSTORE_PATH = "src/main/resources/ftpserver.jks";
    public static final String USER_PROPERTIES_PATH = "src/main/resources/user.properties";
    public static final int DEFAULT_PORT = 2221;
    public static final String DEFAULT_PASS = "password";

    // TODO: make a constructor with the connection-mode (implicite/explicite)

    public FtpsServer() {
        this(DEFAULT_PORT, DEFAULT_PASS);
    }

    public FtpsServer(int port, String keystorePassword) {
        this.port = port;
        this.keystorePassword = keystorePassword;
    }


    public void start() throws FtpException {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();

        // set the port of the listener
        factory.setPort(port);

        // define SSL configuration
        SslConfigurationFactory ssl = new SslConfigurationFactory();
        ssl.setKeystoreFile(new File(KEYSTORE_PATH));
        ssl.setKeystorePassword(keystorePassword);

        // set the SSL configuration for the listener
        factory.setSslConfiguration(ssl.createSslConfiguration());
        factory.setImplicitSsl(false);

        // replace the default listener
        serverFactory.addListener("default", factory.createListener());
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File(USER_PROPERTIES_PATH));
        serverFactory.setUserManager(userManagerFactory.createUserManager());

        // start the server
        FtpServer server = serverFactory.createServer();
        server.start();
    }

    public void stop() {
        server.stop();
    }

}
