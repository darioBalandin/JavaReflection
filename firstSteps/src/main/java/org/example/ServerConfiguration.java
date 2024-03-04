package org.example;

import java.net.InetSocketAddress;

public class ServerConfiguration {
    private static ServerConfiguration serverConfigurationInstance;


    private final InetSocketAddress inetSocketAddress;
    private final String greetingMessage;

    private ServerConfiguration(int port, String greetingMessage) {
        this.inetSocketAddress = new InetSocketAddress("localhost", port);
        this.greetingMessage = greetingMessage;
        if (serverConfigurationInstance == null) {
            serverConfigurationInstance = this;
        }
    }

    public static ServerConfiguration getInstance(){
        return serverConfigurationInstance;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public String getGreetingMessage() {
        return greetingMessage;
    }
}
