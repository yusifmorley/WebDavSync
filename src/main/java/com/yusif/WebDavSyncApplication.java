package com.yusif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

@EnableScheduling
@SpringBootApplication
public class WebDavSyncApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
//        Path path = Paths.get("res/windows-amd64-webdav/webdav.exe");
//
//        Runtime.getRuntime().exec("cmd /c  start " +
//                   path.toAbsolutePath().toString());

        SpringApplication.run(WebDavSyncApplication.class, args);

    }
}
