package com.jackson.ccc.http;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LXP on 17-6-5.
 */

public class SimpleHttpServer {
    private final ExecutorService threadPort;
    protected Boolean isEnable;
    private ServerSocket socket;
    private WebConfigration webconfig;
    private Set<IResoureUriHandler> resourceHandlers;

    public SimpleHttpServer(WebConfigration webConfig) {
        this.webconfig = webConfig;
        threadPort = Executors.newCachedThreadPool();
        resourceHandlers = new HashSet<IResoureUriHandler>();
    }

    //异步启动服务器
    public void startAsync(){
        isEnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProcSync();
            }
        }).start();
    }

    private void doProcSync() {
        try {
            InetSocketAddress sockrtAddr = new InetSocketAddress(webconfig.getPort());
            socket = new ServerSocket();
            socket.bind(sockrtAddr);

        while (isEnable){
            final Socket remotePeer = socket.accept();
            threadPort.submit(new Runnable() {
                @Override
                public void run() {
                    Log.e("222","spy A remote peer accepted"+remotePeer.getRemoteSocketAddress().toString());
                    onAcceptRmotePeer(remotePeer);

                }
            });
        }
        } catch (IOException e) {
        Log.e("222",e.toString());
    }
    }

    public void registerSourceHandler(IResoureUriHandler handler){
        resourceHandlers.add(handler);
    }

    private void onAcceptRmotePeer(Socket remotePeer) {
        try {
           //remotePeer.getOutputStream().write("congratulations,connected successful".getBytes());
            HttpContext context = new HttpContext();
            context.setUnderlySocket(remotePeer);
            InputStream is=remotePeer.getInputStream();
            String headline = null;
            String resourceUrl = headline=StreamToolkit.readLine(is).split(" ")[1];
            Log.e("222",resourceUrl.toString());
            while ((headline=StreamToolkit.readLine(is))!=null){
            if(headline.equals("\r\n")){
                break;
            }
            String[] pair = headline.split(": ");
                if (pair.length>1){
                    context.addRequestHeader(pair[0],pair[1]);
                }
            Log.e("222","headline line = "+ headline);
            }

            for(IResoureUriHandler handler : resourceHandlers){
                if(handler.accept(resourceUrl)){
                    continue;
                }
                handler.handle(resourceUrl,context);
            }

        } catch (IOException e) {
            Log.e("222",e.toString());
        }
    }

    //异步关闭服务器
    public void stopAsync() throws IOException {
        if(isEnable){
            return;
        }
        isEnable=false;
        socket.close();
        socket=null;
    }
}
