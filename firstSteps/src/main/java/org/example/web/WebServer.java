package org.example.web;

import com.sun.net.httpserver.HttpServer;
import org.example.ServerConfiguration;

import java.io.IOException;
import java.io.OutputStream;

public class WebServer {


    public void startServer() throws IOException {
        HttpServer httpServer = HttpServer.create(ServerConfiguration.getInstance().getInetSocketAddress(),0);
        httpServer.createContext("/greeting").setHandler(exchange -> {
            String response= ServerConfiguration.getInstance().getGreetingMessage();

            exchange.sendResponseHeaders(200,response.length());
            OutputStream responseBody= exchange.getResponseBody();
            responseBody.write(response.getBytes());
            responseBody.flush();
            responseBody.close();
        });

        System.out.println(String.format("Starting server on address %s:%d",ServerConfiguration.getInstance().getInetSocketAddress().getHostName(),
                ServerConfiguration.getInstance().getInetSocketAddress().getPort()));

        httpServer.start();
    }
}
