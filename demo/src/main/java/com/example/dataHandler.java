package com.example;

import java.util.HashMap;

public class dataHandler {
     public static HashMap<String, Integer> disponibilita = new HashMap<>();

    static {
        disponibilita.put("Gold", 10);
        disponibilita.put("Pit", 30);
        disponibilita.put("Parterre", 60);
    }
    

}
