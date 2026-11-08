package com.example;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3000);
        Biglietti biglietti = new Biglietti (10,50,100);
        do {
            Socket s = server.accept();
            System.out.println("Qualcuno si è collegato");
            ThreadWorker thread = new ThreadWorker(s, biglietti);
            thread.start();            
        } while (true);
    }
}