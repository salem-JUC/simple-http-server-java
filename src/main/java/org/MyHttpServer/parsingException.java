package org.MyHttpServer;

public class parsingException extends Exception{

    private HttpStatusCode code; // which is error code apparently

    public parsingException(HttpStatusCode code) {
        super(code.MESSAGE);
        this.code = code;
    }

}
