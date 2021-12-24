package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ReadThread implements Runnable{
    private int numberOfThreadsInPool;
    private int[] theadTasks;

    public ReadThread(int numberOfThreadsInPool) {
        this.numberOfThreadsInPool = numberOfThreadsInPool;
        this.theadTasks = new int[numberOfThreadsInPool];
        for (int i = 0; i < numberOfThreadsInPool; i++) {
            theadTasks[i] = 0;
        }
    }

    synchronized void addThreadTask(long index) {
        theadTasks[(int) index] ++;
    }

    @Override
    public void run() {
        try {
            Path filePath = Paths.get("test.txt");
            List<String> numbers = Files.readAllLines(filePath);
            WriteThread writeThread = new WriteThread();
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreadsInPool);
            for (String number : numbers) {
                try {
                    Task task = new Task(Integer.parseInt(number), this, writeThread);
                    threadPoolExecutor.execute(task);
                } catch (NumberFormatException numberFormatException) {
                    numberFormatException.printStackTrace();
                }
            }
            threadPoolExecutor.shutdown();
            try {
                threadPoolExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writeThread.run();
            for (int i = 0; i < theadTasks.length; i++) {
                System.out.println(String.format("Thread%s: %s", i + 1, theadTasks[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
