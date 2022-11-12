package ro.ase.ppoo.entitati;

import java.util.ArrayList;

public class ColectieCarti {

    public ArrayList<Carte> colectie;

    public ColectieCarti() {
        super();
        this.colectie = new ArrayList<Carte>();
    }

    public void afisare() {
        if (this.colectie.isEmpty()) {
            System.out.println("Colectia nu contine nici o carte");
        } else {
            for (Carte carte : colectie) {
                System.out.println(carte.toString());
            }
        }

    }


}
