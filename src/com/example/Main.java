package com.example;

public class Main {

    public static void main(String[] args) {
	ReadThread readThread = new ReadThread(Integer.parseInt(args[0]));
    readThread.run();
    }
}
