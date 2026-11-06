package com.example;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int PORT = 3000;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nuova connessione effettuata da: " + socket.getInetAddress());

                threadPool.execute(new SocketHandler(socket));
            }
        } catch (IOException e) {
            System.err.println("Errore nel server: " + e.getMessage());
        } finally{
            threadPool.shutdown();
        }
    }
}