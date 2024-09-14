package org.MyHttpServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
//
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(711);
            LOGGER.info("server is running .....");
            LOGGER.info("Listening to port 711 ....");
            while (!serverSocket.isClosed() & serverSocket.isBound()){
                // when recvinig a tcp connection create thread to handle each connection simultaneously
                HttpConnectionHandler handler = new HttpConnectionHandler(serverSocket.accept());
                handler.start();

            }
        } catch (IOException e) {
            LOGGER.error("Failure to set up the Server" + e.getMessage());
        }finally {
            if (serverSocket!=null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}