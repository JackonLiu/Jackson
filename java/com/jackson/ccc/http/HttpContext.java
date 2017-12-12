package com.jackson.ccc.http;

import java.net.Socket;
import java.nio.channels.Pipe;
import java.util.HashMap;

/**
 * Created by LXP on 17-6-5.
 */

public class HttpContext {
    private final HashMap<String, String> requestHeaders;
    private Socket underlySocket;

    public HttpContext() {
        requestHeaders =new HashMap<String,String>();
    }

    public Socket getUnderlySocket() {
        return underlySocket;
    }

    public void setUnderlySocket(Socket underlySocket) {
        this.underlySocket = underlySocket;
    }

    public void addRequestHeader(String headerName,String headerValue){
        requestHeaders.put(headerName,headerValue);
    }

    public String getRequestHeaderValue(String headerName){
        return requestHeaders.get(headerName);
    }



}
