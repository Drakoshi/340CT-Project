package com.kiviliut;

public class Main {

    public static void main(String[] args) {
        // Starting point of program
        new Thread(new EventHandler(),"EventHandlerThread").start();
    }
}
