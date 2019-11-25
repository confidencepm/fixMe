package za.co.fixme.router;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RouterServer {

    private static final Logger logger = LogManager.getLogger(RouterServer.class.getName());

    public static void main(String[] args) {
        Thread brokerThread = new Thread();
        brokerThread.start();

        Thread marketThread = new Thread();
        marketThread.start();
    }
}

class Brokers implements Runnable {
    private static final Logger logger = LogManager.getLogger(Brokers.class.getName());

    private Brokers(Socket accept) {
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            logger.info("Connections to RouterServer can now be established...");
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Brokers(serverSocket.accept()));
            }

        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
