package com.jackson.ccc.http;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by LXP on 17-6-5.
 */

public class ResourceInAssetHandler implements IResoureUriHandler {

    private String acceptPrefix = "/static/";
    private Context ct;

    @Override
    public boolean accept(String url) {
        return url.startsWith(acceptPrefix);
    }

    public ResourceInAssetHandler(Context ct) {
        this.ct = ct;
    }

    @Override
    public void handle(String url, HttpContext context) throws IOException {
        int startIndex = acceptPrefix.length();
        String assetPath = url.substring(startIndex);
        InputStream is = ct.getAssets().open(assetPath);
        byte[] raw = StreamToolkit.readRawFromStream(is);
        is.close();
        OutputStream out = context.getUnderlySocket().getOutputStream();
        PrintStream printer = new PrintStream(out);
        printer.println("HTTP/1.1 200 ok");
        printer.println("content-length:" + raw.length);
        if(assetPath.endsWith(".html")){
            printer.println("content-Type:text/html");
        }else if(assetPath.endsWith(".css")){
            printer.println("content-Type:text/css");
        }else if(assetPath.endsWith(".jpg")){
            printer.println("content-Type:text/jpg");
        }else if(assetPath.endsWith(".png")){
            printer.println("content-Type:text/png");
        }
        printer.println();
        printer.write(raw);


/*        OutputStream out= null;
        try {
            out = context.getUnderlySocket().getOutputStream();

            PrintWriter writer = new PrintWriter(out);
            writer.println("HTTP/1.1 200 ok");
            writer.println();
            writer.println("from resource in assets handler");
            writer.println("手机服务端已经实现！");
            writer.flush();
    } catch (IOException e) {
        e.printStackTrace();
        }*/
}
}
