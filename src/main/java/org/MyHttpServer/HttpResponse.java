package org.MyHttpServer;

public class HttpResponse {

    private String version; // HTTP/1.1 usually
    private HttpStatusCode responseCode;
    private long contentLength; // body.getByte().getLength
    private String body;


    //    String response = "HTTP/1.1 200 OK\n\r"
//            + "Content-Length: " + html.getBytes().length + "\n\r\n\r"
//            + html + "\n\r\n\r";
    public String responseToWrite(){
        String response =
                version +" " + responseCode.getSTATUS_CODE() + " " + responseCode.MESSAGE +"\n\r"
                + "Content-Length: "+ body.getBytes().length +"\n\r\n\r"
                + body + "\n\r\n\r";

        return response;
    }
    public String getVersion() {
        return version;
    }


    public void setVersion(String version) {
        this.version = version;
    }


    public HttpStatusCode getCode() {
        return responseCode;
    }

    public void setCode(HttpStatusCode code) {
        this.responseCode = code;
    }

    public long getContentLength() {
        return contentLength;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }



    public HttpResponse() {
    }


}
