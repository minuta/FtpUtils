package FTPS;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;

import java.io.File;

/**
 *  An example implementation of a FTPS server using the Apache embedded FTP Server
 *  The FTPS server is using a self-signed certificate stored in the KEYSTORE_PATH
 */
public class FtpsServer {

    private org.apache.ftpserver.FtpServer server;
    private final int port;
    private final String keystorePassword;
//    private final String ftpHomeDir;
    private final String keyStorePath;
//    private final String userPropertiesPath;
    public boolean isImplicitMode;

    private final String user;
    private final String userPass;
    private final String userHomeDir;
//    private final int port;

    public static final String KEYSTORE_PATH = "FTPS_SERVER_SETTINGS/ftpserver.jks";
    public static final String USER_PROPERTIES_PATH = "FTPS_SERVER_SETTINGS/user.properties";
    public static final int DEFAULT_PORT = 2221;
    public static final String DEFAULT_PASS = "password";
    public static final String DEFAULT_LISTENER = "default";

//    public FtpsServer() {
//        this(DEFAULT_PORT, DEFAULT_PASS, false);
//    }

    public FtpsServer(int port, String keyStorePath, String keystorePassword, String user, String userPass, String userHomeDir, boolean isImplicitMode) {
        this.port = port;
        this.keystorePassword = keystorePassword;
        this.isImplicitMode = isImplicitMode;
        this.keyStorePath = keyStorePath;
//        this.userPropertiesPath = userPropertiesPath;
        this.user = user;
        this.userPass = userPass;
        this.userHomeDir = userHomeDir;
    }

    public void createUser(UserManager userManager, String userName, String userPass, String userHomeDir) throws FtpException {
        BaseUser user = new BaseUser();
        user.setName(userName);
        user.setPassword(userPass);
        user.setHomeDirectory(userHomeDir);
        user.setEnabled(true);
        userManager.save(user);
    }



    public void start() throws FtpException {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();

        // set the port of the listener
        factory.setPort(port);

        // define SSL configuration
        SslConfigurationFactory ssl = new SslConfigurationFactory();
        ssl.setKeystoreFile(new File(keyStorePath));
        ssl.setKeystorePassword(keystorePassword);

        // set the SSL configuration for the listener
        factory.setSslConfiguration(ssl.createSslConfiguration());
        factory.setImplicitSsl(false);

        // replace the default listener
        serverFactory.addListener(DEFAULT_LISTENER, factory.createListener());

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        UserManager userManager = userManagerFactory.createUserManager();
        createUser(userManager, user, userPass, userHomeDir);


//        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
//        userManagerFactory.setFile(new File(userPropertiesPath));
//        serverFactory.setUserManager(userManagerFactory.createUserManager());

//        FtpServerFactory factory = new FtpServerFactory();
        serverFactory.setUserManager(userManager);

//        server = factory.createServer();
//        server.start();

        // start the server
        FtpServer server = serverFactory.createServer();
        server.start();
    }

    public void stop() {
        server.stop();
    }

}
