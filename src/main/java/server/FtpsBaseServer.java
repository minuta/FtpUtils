package server;

import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

import java.io.File;

/**
 *  An example implementation of a FTPS server using the Apache embedded FTP Server
 *  The FTPS server is using a self-signed certificate stored in the KEYSTORE_PATH
 */
public class FtpsBaseServer extends FtpBaseServer{

    private final String keystorePassword;
    private final String keyStorePath;
    public boolean isImplicitMode;


//    public FtpsBaseServer(String user, String pass, String homeDir, int port) {
//        super(user, pass, homeDir, port);
//    }

    /**
     *   constructor for creating an FTPS server with a single user
     */
    public FtpsBaseServer(int port, String keyStorePath, String keystorePassword, String user, String userPass, String userHomeDir, boolean isImplicitMode) {
        super(user, userPass, userHomeDir, port);
        this.keystorePassword = keystorePassword;
        this.isImplicitMode = isImplicitMode;
        this.keyStorePath = keyStorePath;
    }

    @Override
    public void init() throws FtpException {
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
        createUser(userManager, user, pass, homeDir);


        serverFactory.setUserManager(userManager);

        server = serverFactory.createServer();
//        server.start();
    }
}
