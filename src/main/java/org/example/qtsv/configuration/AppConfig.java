package org.example.qtsv.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${server.port}")
    private int port;

    public void printServerPort() {
        System.out.println("Server port is: " + port);
    }
}