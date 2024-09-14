package org.MyHttpServer;

import java.net.http.HttpConnectTimeoutException;

public class HttpRequest {

    HttpMethod method;
    String target;
    String version;

    public HttpMethod getMethod() {
        return method;
    }

     public void setMethod(String string) throws HttpConnectTimeoutException {
        if (string.equals("GET"))
            this.method = HttpMethod.GET;
        else if(string.equals("HEAD"))
            this.method = HttpMethod.HEAD;
        else // if string (parsed method) no either GET or HEAD  , then it's not implemented
            throw new HttpConnectTimeoutException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED.MESSAGE);
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
