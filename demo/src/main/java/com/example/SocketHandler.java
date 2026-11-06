package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class SocketHandler implements Runnable{

    private Socket s; 

    public SocketHandler(Socket socket){
        s = socket;
    }

    

    @Override 
    public void run(){

        ArrayList<String> listaUtenti = new ArrayList<>();

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            out.println("WELCOME!");

            boolean logged = false;
            String string = "";
            

            while(string != "QUIT"){

                string = in.readLine();
                String[] parti = string.split(" ", 2);

                try {
                    String comando = parti[0];
                } catch (Exception e) {
                    out.println("ERR COMMAND NOT STRING");
                }


                switch (parti[0]) {

                    case "LOGIN":
                        if(logged){
                            out.println("USER ALREADY LOGGED");
                        }
                        if(parti.length == 1){
                            out.println("ERR MISSING USER");
                            break;
                        }
                        if(listaUtenti.contains(parti[1])){
                            out.println("USERNAME ALREADY TAKEN");
                        } else{
                            listaUtenti.add(parti[1]);
                            out.println("OK");
                            logged = true;
                        }
                        break;
                        
                    case "BUY":
                        if(!logged){
                            out.println("ERR LOGIN REQUIRED");
                        }

                            String buyRequest = in.readLine();
                            String[] request = buyRequest.split(" ", 2);
                            int numberTicket = 0;
                            Integer quantity = 0;

                            try {
                                numberTicket = Integer.parseInt(request[1]);
                            } catch (NumberFormatException e) {
                                out.println("ERR NOT A NUMBER");
                                break;
                            }
                            
                            if(request[0] != "pit" || request[0] != "gold" || request[0] != "parterre"){
                                out.println("ERR TYPE INCORRECT");
                            } else{

                                if(request[0] == "pit"){
                                    quantity = dataHandler.disponibilita.get("Pit");
                                    if(quantity == 0){
                                        out.println("ERR TICKETS ARE SOLD OUT");
                                    } else{
                                        if(numberTicket > quantity){
                                            out.println("KO");
                                        } else{
                                            Integer newQuantity = quantity - numberTicket;
                                            dataHandler.disponibilita.put("Pit", newQuantity);
                                            out.println("OK");
                                        }
                                    }
                                }

                                if(request[0] == "gold"){
                                    quantity = dataHandler.disponibilita.get("Gold");
                                    if(quantity == 0){
                                        out.println("ERR TICKETS ARE SOLD OUT");
                                    } else{
                                        if(numberTicket > quantity){
                                            out.println("KO");
                                        } else{
                                            Integer newQuantity = quantity - numberTicket;
                                            dataHandler.disponibilita.put("Gold", newQuantity);
                                            out.println("OK");
                                        }
                                    }
                                }

                                if(request[0] == "parterre"){
                                    quantity = dataHandler.disponibilita.get("Parterre");
                                    if(quantity == 0){
                                        out.println("ERR TICKETS ARE SOLD OUT");
                                    } else{
                                        if(numberTicket > quantity){
                                            out.println("KO");
                                        } else{
                                            Integer newQuantity = quantity - numberTicket;
                                            dataHandler.disponibilita.put("Parterre", newQuantity);
                                            out.println("OK");
                                        }
                                    }        
                                }
                            }
                        break;
                    
                    case "N":
                        out.println("AVAILABLE TICKETS: " + "Gold:" + dataHandler.disponibilita.get("Gold") + " Pit:" + dataHandler.disponibilita.get("Pit") + " Parterre:" + dataHandler.disponibilita.get("Parterre"));
                        break;

                    case "QUIT":
                        break;

                    default:
                        out.println("ERR CMD NOT FOUND");
                        break;
                }

            }
                



            System.out.println("Client disconnesso");
        } catch(IOException e){
            System.out.println("Errore: " + e.getMessage());
        }
    }
}