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
    private final String keyStorePath;
    private String userPropertiesPath = null;
    public boolean isImplicitMode;

    private String user = null;
    private String userPass = null;
    private String userHomeDir = null;

    public static final String KEYSTORE_PATH = "FTPS_SERVER_SETTINGS/ftpserver.jks";
    public static final String USER_PROPERTIES_PATH = "FTPS_SERVER_SETTINGS/user.properties";
    public static final int DEFAULT_PORT = 2221;
    public static final String DEFAULT_PASS = "password";
    public static final String DEFAULT_LISTENER = "default";


    /**
     *   constructor for creating an FTPS server with a single user
     */
    public FtpsServer(int port, String keyStorePath, String keystorePassword, String user, String userPass, String userHomeDir, boolean isImplicitMode) {
        this.port = port;
        this.keystorePassword = keystorePassword;
        this.isImplicitMode = isImplicitMode;
        this.keyStorePath = keyStorePath;
        this.user = user;
        this.userPass = userPass;
        this.userHomeDir = userHomeDir;
    }

    /**
     *   constructor for creating an FTPS server via the file user.properties
     */
    public FtpsServer(int port, String keyStorePath, String keystorePassword, String userPropertiesPath, boolean isImplicitMode) {
        this.port = port;
        this.keystorePassword = keystorePassword;
        this.isImplicitMode = isImplicitMode;
        this.keyStorePath = keyStorePath;
        this.userPropertiesPath = userPropertiesPath;
    }

    public void createUser(UserManager userManager, String userName, String userPass, String userHomeDir) throws FtpException {
        BaseUser user = new BaseUser();
        user.setName(userName);
        user.setPassword(userPass);
        user.setHomeDirectory(userHomeDir);
        user.setEnabled(true);
        userManager.save(user);
    }

    public void startWithUserPropertiesFile() throws FtpException {
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
        factory.setImplicitSsl(false); // TODO: should be not hardcoded!

        // replace the default listener
        serverFactory.addListener(DEFAULT_LISTENER, factory.createListener());

//        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
//        UserManager userManager = userManagerFactory.createUserManager();
//        createUser(userManager, user, userPass, userHomeDir);


        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File(userPropertiesPath));
        serverFactory.setUserManager(userManagerFactory.createUserManager());

//        FtpServerFactory factory = new FtpServerFactory();
//        serverFactory.setUserManager(userManager);

//        server = factory.createServer();
//        server.start();

        // start the server
        FtpServer server = serverFactory.createServer();
        server.start();
    }

    public void startWithSingleUser() throws FtpException {
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
