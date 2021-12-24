package com.example;

import java.math.BigInteger;

public class Task implements Runnable{
    private int number;
    private ReadThread readThread;
    private WriteThread writeThread;

    public Task(int number, ReadThread readThread, WriteThread writeThread) {
        this.number = number;
        this.readThread = readThread;
        this.writeThread = writeThread;
    }

    @Override
    public void run() {
        BigInteger result = BigInteger.valueOf(1);
        for (int i = 2; i <= number; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        writeThread.writeData(number + " = " + result);
        readThread.addThreadTask(Thread.currentThread().getId() - 11);
    }
}
