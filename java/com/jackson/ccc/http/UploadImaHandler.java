package com.jackson.ccc.http;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by LXP on 17-6-5.
 */

public class UploadImaHandler implements IResoureUriHandler {
    private String acceptPrefix = "/upload_image/";

    @Override
    public boolean accept(String url) {
        return url.startsWith(acceptPrefix);
    }

    @Override
    public void handle(String url, HttpContext context) throws IOException {
        String tmpPath = "/sdcard/test_upload.jpg";
        long totalLength = Long.parseLong(context.getRequestHeaderValue("Content-Length"));
        FileOutputStream fos= new FileOutputStream(tmpPath);
        InputStream is = context.getUnderlySocket().getInputStream();
        byte[] buffer = new byte[10240];
        int nReaded = 0;
        long nLeftLength = totalLength;
        while ((nReaded = is.read(buffer))>0 && nLeftLength>0){
            fos.write(buffer,0,nReaded);
            nLeftLength-=nReaded;
        }
        fos.close();

        OutputStream out= context.getUnderlySocket().getOutputStream();
        PrintStream printer = new PrintStream(out);
        printer.println("HTTP/1.1 200 ok");
        printer.println();
        onImageLoaded(tmpPath);


        /*OutputStream out= null;
        try {
            out = context.getUnderlySocket().getOutputStream();

            PrintWriter writer = new PrintWriter(out);
            writer.println("HTTP/1.1 200 ok");
            writer.println();
            writer.println("from upload img handler");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }

    public void onImageLoaded(String tmpPath) {

    }
}
