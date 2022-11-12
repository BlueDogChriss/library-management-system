package ro.ase.ppoo.functii;

import ro.ase.ppoo.entitati.Carte;
import ro.ase.ppoo.entitati.ColectieCarti;
import ro.ase.ppoo.entitati.GenCarte;
import ro.ase.ppoo.exceptii.InvalidInputEnum;
import ro.ase.ppoo.exceptii.InvalidInputNumber;
import ro.ase.ppoo.exceptii.InvalidInputString;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class Functii {
    public static void afisareBiblioteca(Map<String, ColectieCarti> biblioteca) {
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            System.out.println("\nColectia de carti: " + entry.getKey());
            entry.getValue().afisare();
        }
    }

    public static void createTextFile(File file) throws IOException {
        if (file.isFile()) {
            return;
        } else {
            file.createNewFile();
        }
    }

    public static void readTextFile(File file, Map<String, ColectieCarti> biblioteca) {
        boolean ctn = true;
        try {
            FileInputStream fileInput = new FileInputStream(file);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            while (ctn) {
                Carte carte = (Carte) objectInput.readObject();
                if (carte != null) {
                    switch (carte.getTipCarte()) {
                        case AVENTURA:
                            biblioteca.get(GenCarte.AVENTURA.toString()).colectie.add(carte);
                            break;
                        case COMEDIE:
                            biblioteca.get(GenCarte.COMEDIE.toString()).colectie.add(carte);
                            break;
                        case ROMANCE:
                            biblioteca.get(GenCarte.ROMANCE.toString()).colectie.add(carte);
                            break;
                        case DRAMA:
                            biblioteca.get(GenCarte.DRAMA.toString()).colectie.add(carte);
                            break;
                        case EDUCATIONAL:
                            biblioteca.get(GenCarte.EDUCATIONAL.toString()).colectie.add(carte);
                            break;
                        case POEZIE:
                            biblioteca.get(GenCarte.POEZIE.toString()).colectie.add(carte);
                            break;
                        case TRAGEDIE:
                            biblioteca.get(GenCarte.TRAGEDIE.toString()).colectie.add(carte);
                            break;
                    }
                } else {
                    ctn = false;
                }
            }
        } catch (EOFException e) {
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void saveToTextFile(File file, Map<String, ColectieCarti> biblioteca) throws IOException {
        file.createNewFile();
        FileOutputStream fileOutput = new FileOutputStream(file);
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (!entry.getValue().colectie.isEmpty()) {
                for (Carte carte : entry.getValue().colectie) {
                    objectOutput.writeObject(carte);
                }
            }
        }
        objectOutput.close();
    }

    public static boolean afiseazaCarteDupaNume(String nume, Map<String, ColectieCarti> biblioteca) {
        System.out.println(nume);
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (!entry.getValue().colectie.isEmpty()) {
                for (Carte carte : entry.getValue().colectie) {
                    if (carte.getNume().equalsIgnoreCase(nume)) {
                        System.out.println(carte);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String citesteString(Scanner consoleInput, String msg) {
        String searchBar;
        System.out.println("Introduceti " + msg + ":");
        searchBar = consoleInput.nextLine();
        try {
            if (searchBar.length() < 5 || searchBar.length() > 30) {
                throw new InvalidInputString();
            }
        } catch (InvalidInputString e) {
            System.out.println("Id prea scurt! Trebuie sa fie intre 5 si 30 de caractere");
            searchBar = citesteString(consoleInput, msg);
        }

        return searchBar;
    }

    public static int citesteInt(Scanner consoleInput, String msg) {
        int searchBar;
        System.out.println("Introduceti " + msg + ":");
        searchBar = consoleInput.nextInt();
        try {
            if (searchBar < 0 || searchBar > 500) {
                throw new InvalidInputNumber();
            }
        } catch (InvalidInputNumber e) {
            System.out.println("Numar invalid, se accepta numere intre 0 si 500.");
            searchBar = citesteInt(consoleInput, msg);
        }

        return searchBar;
    }

    public static String citesteGenCarte(Scanner consoleInput, String msg) {
        String searchBar;
        System.out.println("Alegeti unul dintre urmatoarele:");
        System.out.println("Aventura Comedie Dragoste Drama Educational Poezie Tragedie");
        System.out.println("Introduceti " + msg + ":");
        searchBar = consoleInput.nextLine();
        try {
            if (!matchStringWithEnum(searchBar)) {
                throw new InvalidInputEnum();
            }
        } catch (InvalidInputEnum e) {
            System.out.println("Genul cartii este invalid!");
            searchBar = citesteGenCarte(consoleInput, msg);
        }

        return searchBar;
    }

    public static boolean matchStringWithEnum(String input) {
        if (input.equalsIgnoreCase(GenCarte.AVENTURA.toString())) {
            return true;
        }
        if (input.equalsIgnoreCase(GenCarte.COMEDIE.toString())) {
            return true;
        }
        if (input.equalsIgnoreCase(GenCarte.ROMANCE.toString())) {
            return true;
        }
        if (input.equalsIgnoreCase(GenCarte.DRAMA.toString())) {
            return true;
        }
        if (input.equalsIgnoreCase(GenCarte.EDUCATIONAL.toString())) {
            return true;
        }
        if (input.equalsIgnoreCase(GenCarte.POEZIE.toString())) {
            return true;
        }
        return input.equalsIgnoreCase(GenCarte.TRAGEDIE.toString());
    }

    public static void stergeCarte(String nume, Map<String, ColectieCarti> biblioteca) {
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (!entry.getValue().colectie.isEmpty()) {
                for (Carte carte : entry.getValue().colectie) {
                    if (carte.getNume().equalsIgnoreCase(nume)) {
                        entry.getValue().colectie.remove(carte);
                        System.out.println("Cartea a fost stearsa cu succes");
                        return;
                    }
                }
            }
        }
        System.out.println("Cartea nu exista");
    }

    public static void imprumutaCarte(String nume, Map<String, ColectieCarti> biblioteca) {
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (!entry.getValue().colectie.isEmpty()) {
                for (Carte carte : entry.getValue().colectie) {
                    if (carte.getNume().equalsIgnoreCase(nume)) {
                        if (carte.getNrExemplareActual() > 0) {
                            carte.setNrExemplareActual(carte.getNrExemplareActual() - 1);
                            System.out.println("Cartea a fost imprumutata!");
                            return;
                        } else {
                            System.out.println("Aceasta carte nu mai este disponibila");
                            return;
                        }
                    }
                }
            }
        }
        System.out.println("Cartea nu exista");
    }

    public static void returneazaCarte(String nume, Map<String, ColectieCarti> biblioteca) {
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (!entry.getValue().colectie.isEmpty()) {
                for (Carte carte : entry.getValue().colectie) {
                    if (carte.getNume().equalsIgnoreCase(nume)) {
                        if (carte.getNrExemplareActual() < carte.getNrExemplareTotale()) {
                            carte.setNrExemplareActual(carte.getNrExemplareActual() + 1);
                            System.out.println("Cartea a fost returnata!");
                            return;
                        } else {
                            System.out.println("Toate cartile au fost returnate");
                            return;
                        }
                    }
                }
            }
        }
        System.out.println("Cartea nu exista");
    }

    public static void afiseazaColectie(String nume, Map<String, ColectieCarti> biblioteca) {
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(nume)) {
                entry.getValue().afisare();
                return;
            }
        }
        System.out.println("Colectia nu exista");
    }

    public static void calculeazaNumarCartiImprumutate(Map<String, ColectieCarti> biblioteca) {
        int[] vectorOccurence = new int[7];
        int index = 0;
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (!entry.getValue().colectie.isEmpty()) {
                for (Carte carte : entry.getValue().colectie) {
                    vectorOccurence[index] += (carte.getNrExemplareTotale() - carte.getNrExemplareActual());
                }
            } else {
                vectorOccurence[index] = 0;
            }
            System.out.println("Colectia " + entry.getKey() + " are " + vectorOccurence[index] + " carti imprumutate");
            index++;
        }

        int suma = 0;
        for (int i = 0; i < vectorOccurence.length; i++) {
            suma += vectorOccurence[i];
        }
        System.out.println("In total au fost imprumutate " + suma + " carti din biblioteca");
    }

    public static float calculareNotaMedieColectie(String nume, Map<String, ColectieCarti> biblioteca) {
        float medie = 0;
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(nume)) {
                if (!entry.getValue().colectie.isEmpty()) {
                    for (Carte carte : entry.getValue().colectie) {
                        medie += carte.getNota();
                    }
                    return medie / entry.getValue().colectie.size();
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }

    public static float calculareNotaMedieTotal(Map<String, ColectieCarti> biblioteca) {
        float medie = 0;
        int counter = 0;
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (!entry.getValue().colectie.isEmpty()) {
                for (Carte carte : entry.getValue().colectie) {
                    medie += carte.getNota();
                    counter++;
                }
            }
        }
        if (medie != 0) {
            return medie / counter;
        } else {
            return 0;
        }
    }

    public static int calculNumarCarti(Map<String, ColectieCarti> biblioteca) {
        int suma = 0;
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (!entry.getValue().colectie.isEmpty()) {
                suma += entry.getValue().colectie.size();
            }
        }
        return suma;
    }

    public static float calculeNumarCartiMediuPeColectie(Map<String, ColectieCarti> biblioteca) {
        String[] vectorOccurence = new String[7];
        int counter = 0;
        float medie = 0;
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            vectorOccurence[counter] = String.valueOf(entry.getValue().colectie.size());
            counter++;
        }
        for (int i = 0; i < vectorOccurence.length; i++) {
            medie += Integer.parseInt(vectorOccurence[i]);
        }
        if (medie != 0) {
            return medie / counter;
        } else {
            return 0;
        }
    }

    public static void generareRaportText(Map<String, ColectieCarti> biblioteca) {
        try {
            File file = new File("raport.txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("-------------------------Raport text despre biblioteca------------------------\n\n");
            writer.write("Biblioteca are " + calculNumarCarti(biblioteca) + " carti\n");
            writer.write("Fiecare colectie are in medie " + calculeNumarCartiMediuPeColectie(biblioteca) + " carti\n");
            writer.write("Fiecare carte din biblioteca are in medie Nota" + calculareNotaMedieTotal(biblioteca)
                    + "\n");
            for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
                float medie = calculareNotaMedieColectie(entry.getKey(), biblioteca);
                writer.write("Fiecare carte din colectia " + entry.getKey() + " are in medie Nota:" + medie + "\n");
            }
            writer.close();

            System.out.println("Raportul a fost generat cu succes!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
