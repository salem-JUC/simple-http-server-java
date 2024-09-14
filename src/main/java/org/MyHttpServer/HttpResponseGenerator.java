package org.MyHttpServer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HttpResponseGenerator {
    private HttpRequest request;

    private static final String webRoot = "webRoot";
    public HttpResponseGenerator(HttpRequest request) {
        this.request = request;
    }

    public HttpResponseGenerator() {
    }

    public HttpResponse responseWithParsingException(HttpStatusCode code){
        HttpResponse response = null;
        response.setCode(code);
        response.setBody("<html><head><title>my Http Server</title></head>" +
                "<body>" +
                "<h1>" + code.getSTATUS_CODE() + "</h1>" +
                "<h1>" + code.getMESSAGE() + "</h1>" +
                "</body>" +
                "</html>");
        response.setVersion("HTTP/1.1");

        return response;
    }

    private HttpResponse responseWithNotFound(){
        HttpResponse response = new HttpResponse();
        response.setCode(HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND);
        response.setBody("<html><head><title>my Http Server</title></head>" +
                "<body>" +
                "<h1>" + HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND.getSTATUS_CODE() + "</h1>" +
                "<h1>" + HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND.getMESSAGE() + "</h1>" +
                "</body>" +
                "</html>");
        response.setVersion("HTTP/1.1");

        return response;
    }

    public HttpResponse response(){
        HttpResponse response = new HttpResponse();
        String body = "";
        try {
            body = getBodyFromTarget(request.getTarget());
        } catch (FileNotFoundException e) {
            return responseWithNotFound();
        }catch (IOException e){ // TODO
            return responseWithNotFound();
        }

        response.setCode(HttpStatusCode.OK_200_OK);
        response.setBody(body);
        response.setVersion(request.getVersion());

        return response;
    }

    // get the target and read the html file from webRoot Dirctory
    private String getBodyFromTarget(String target) throws FileNotFoundException {
        if (target.equals("/"))
            target = "/index.html"; // this makes the indext.html is default if client doesnt specifi a target

        String body = "";
        Scanner scan = new Scanner(new File(webRoot+target));
        while (scan.hasNext()){
            body = body + scan.nextLine();
        }
        scan.close();
        return body;
    }
}
