package com.example;


public class Biglietti {
     
    private int gold;
    private int pit;
    private int parterre;


    public Biglietti(int g, int p, int par){
        this.gold = g;
        this.pit = p;
        this.parterre = par;
    }
    
    public String tornaBigliettiRimasti(){
        return "AVAILABLE TICKETS: " + " Gold: " + this.gold + " Pit: " + this.pit + " Parterre: " + this.parterre;
    }

    public synchronized boolean acquisto(String tipo_biglietto, int num_tickets) throws Exception{

        // String[] stringaDivisa = stringAcquisto.split(" ", 2);

        if(tipo_biglietto.equalsIgnoreCase("gold")){
            if(this.gold >= num_tickets ){
                this.gold -= num_tickets;
                return true;
            } else{
                return false;
            }
        } else if(tipo_biglietto.equalsIgnoreCase("pit")){
            if(this.pit >= num_tickets ){
                this.pit -= num_tickets;
                return true;
            } else{
                return false;
            }
        } else if(tipo_biglietto.equalsIgnoreCase("parterre")){
            if(this.parterre >= num_tickets ){
                this.parterre -= num_tickets;
                return true;
            } else{
                return false;
            }
        } else{
            throw new Exception();
        }
    }
}
