package org.MyHttpServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class HttpConnectionHandler extends Thread{
//
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionHandler.class);


    private Socket clientSocket;

    public HttpConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        LOGGER.info("connection is proccessing .." + clientSocket.getInetAddress());
        InputStream is = null;
        OutputStream os = null;

        try {
            boolean responseWithException = false;
            os = clientSocket.getOutputStream();
            is = clientSocket.getInputStream();
            HttpResponse response = new HttpResponse();
            HttpResponseGenerator hRGenerator = new HttpResponseGenerator();
            HttpParser parser = new HttpParser(is);
            try {
                hRGenerator = new HttpResponseGenerator(parser.parseRequest());
            } catch (parsingException e) {
                response = hRGenerator.responseWithParsingException(e.getCode());
            }

            if (!responseWithException){
                response = hRGenerator.response();
            }

//
//            String html = "<html><head><title>my Http Server</title></head><body>Hello" +
//                    "to My http Server</body></html>";
//
//            String response = "HTTP/1.1 200 OK\n\r"
//                    + "Content-Length: " + html.getBytes().length + "\n\r\n\r"
//                    + html + "\n\r\n\r";

            os.write(response.responseToWrite().getBytes());
        } catch (IOException e) {
            LOGGER.error("error handling connection " + e);
        }finally {
            try {
                if (os != null)
                    os.close();
                if(is!=null)
                    is.close();
                if (clientSocket!=null)
                    clientSocket.close();
                LOGGER.info("connection has been processed");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }
}
