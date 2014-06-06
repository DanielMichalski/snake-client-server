package pl.bestsoft.snake.rmi.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.bestsoft.snake.rmi.common.IRmiServerConnection;

/**
 * Author: Daniel
 */
public class RmiClientConnection {
    private IRmiServerConnection service;

    public void setService(IRmiServerConnection service) {
        this.service = service;
    }

    public boolean isConnected() {
        return service.isConnectionWithServer();
    }

    public static boolean isConnectionToServer() {
        try {
            ApplicationContext clientContext =
                    new ClassPathXmlApplicationContext("rmi_client_context.xml");

            RmiClientConnection clientConnection =
                    clientContext.getBean("clientConnection", RmiClientConnection.class);

            return clientConnection.isConnected();

        } catch (Exception e) {
            return false;
        }
    }
}
