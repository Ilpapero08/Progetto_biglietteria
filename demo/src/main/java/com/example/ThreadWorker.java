package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadWorker extends Thread{
    private static ArrayList<String> lista_urs = new ArrayList();

    private Socket s; 
    private Biglietti bi;
    private boolean logged;
    private BufferedReader in;
    private PrintWriter out;

    public ThreadWorker(Socket socket, Biglietti b){
        s = socket;
        bi = b;
        this.logged = false;
    }

    String operazione;
    String[] stringaUnitaInput;
    String tipo;
    Integer quantita;

    @Override 
    public void run(){

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            this.in = in;
            this.out = out;

            out.println("WELCOME!");
            

            do {

                try {
                    stringaUnitaInput = in.readLine().split(" ", 2);
                    operazione = stringaUnitaInput[0];
                } catch (Exception e) {
                    System.out.println("ERR NOT_A_STRING");
                    continue;
                }

                switch (operazione) {

                    case "LOGIN":
                        if(stringaUnitaInput.length < 2){
                            out.println("ERR_MISSING_USERNAME");
                        } else{
                            if(!this.login(stringaUnitaInput[1])){
                                out.println("USERNAME_ALREADY_TAKEN");
                            } else{
                                out.println("LOGGED!");
                            }
                        }
                        break;

                    case "BUY":
                        if(this.logged){
                            try {
                                String[] stringaAcquisto = in.readLine().split(" ", 2);
                                tipo = stringaAcquisto[0];
                                quantita = Integer.parseInt(stringaAcquisto[1]);
                            } catch (Exception e) {
                                System.out.println("ERR INCORRECT_DATA");
                                continue;            
                            }
                            try {
                                if(this.bi.acquisto(tipo, quantita)){
                                    out.println("OK");
                                } else{
                                    out.println("KO");
                                }
                            } catch (Exception e) {
                                System.out.println("ERR UKNNOWN TYPE");
                            }
                        } else{
                            out.println("ERR LOGIN REQUIRED");
                        }
                        break;

                    case "N":
                        out.println(this.bi.tornaBigliettiRimasti());
                        break;

                    case "QUIT":
                        out.println("BYEE");
                        s.close();
                        return;
                
                    default:
                        out.println("ERR CMD NOT VALID");
                        break;
                }
            } while (true);

        } catch(IOException e){
            System.out.println("Errore: " + e.getMessage());
        }
    }


    public synchronized boolean login(String username){

        if(this.logged){
            out.println("Sei già loggato!");
            return true;
        }
        
        if(!lista_urs.contains(username)){
            lista_urs.add(username);
            this.logged = true;
            return true;
        } else{
            return false;
        }

    }
}