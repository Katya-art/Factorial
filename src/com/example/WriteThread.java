package com.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class WriteThread implements Runnable{

    private StringBuffer result;

    public WriteThread() {
        this.result = new StringBuffer();
    }

    synchronized void writeData(String data) {
        result.append(data).append("\n");
    }

    @Override
    public void run() {
        try {
            Files.write(Paths.get("result.txt"), Collections.singleton(String.valueOf(result)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
