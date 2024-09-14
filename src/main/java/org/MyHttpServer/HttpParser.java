package org.MyHttpServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    // constants
    private static final int SP = 0x20 , CR = 0x0D , LF = 0x0A;
    //                       space ,Carridge Return,LineField
    InputStream is;
    public HttpParser(InputStream is) {
        this.is = is;
    }

    public HttpRequest parseRequest() throws IOException, parsingException {
        HttpRequest request = new HttpRequest();
        InputStreamReader isr = new InputStreamReader(is , StandardCharsets.US_ASCII);

        parseRequestLine(isr , request);

//        parseHeaders(isr , request);
//        parseBody(isr , request);

        return request;

    }

    private void parseBody(InputStreamReader isr, HttpRequest request) {
    }

    private void parseHeaders(InputStreamReader isr, HttpRequest request) {
        
    }

    private void parseRequestLine(InputStreamReader isr, HttpRequest request) throws IOException, parsingException {
        StringBuilder buffer = new StringBuilder();
        boolean methodParsed = false , targetParsed = false;
        int byte_;
        while ( (byte_ = isr.read()) >= 0){
            if (byte_ == CR){
                byte_ = isr.read();
                if (byte_ == LF){
                    if (!methodParsed || !targetParsed){
                        //if either method and target not being parsed
                        throw new parsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    System.out.println("Finally Version parsed : " + buffer.toString());
                    request.setVersion(buffer.toString());
                    return; // this means the Request Line has been parsed (Hopefully)
                }else {
                    // CR withno LF next , this is bad requesto
                    throw new parsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
            }

            if (byte_ == SP){
                if (!methodParsed){
                    System.out.println("METHOD parsed : " + buffer.toString());
                    methodParsed = true;
                    request.setMethod(buffer.toString());
                }else if (!targetParsed){
                    System.out.println("TARGET parsed : " + buffer.toString());
                    targetParsed = true;
                    request.setTarget(buffer.toString());
                }else { // if method and target are parsed and there's space in request line
                        // then this means it's bad request obviously
                    throw new parsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                buffer.delete(0 , buffer.length()); // delete buffer contents, next request line element
            }else {
                buffer.append((char)byte_);
            }
        }
    }
}
