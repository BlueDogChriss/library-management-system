package ro.ase.ppoo;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ro.ase.ppoo.entitati.Carte;
import ro.ase.ppoo.entitati.ColectieCarti;
import ro.ase.ppoo.entitati.GenCarte;
import ro.ase.ppoo.exceptii.InvalidInputEnum;
import ro.ase.ppoo.exceptii.InvalidInputNumber;
import ro.ase.ppoo.exceptii.InvalidInputString;

public class Main {

    // FUNCTIE IN CARE CALCULEZ NR DE CARTI IMPRUMUTATE DINTR-O COLECTIE INTR-UN
    // VECTOR;
    // FUNCTII DE IMPRUMUTA SAU ADU O CARTE INAPOI

    final static String welcome = "Bun venit in aplicatia de gestionare a colectiilor de carti a unei biblioteci!";

    final static String mainMenu = ("\nSelectati un numar din meniu: \n" + "1. Meniu carti \n" + "2. Meniu colectii \n"
            + "3. Meniu biblioteca \n" + "4. Iesire ");

    final static String booksMenu = ("\nSelectati un numar din meniu: \n" + "1. Afiseaza toate cartile \n"
            + "2. Afisare carte dupa nume \n" + "3. Adauga o carte \n" + "4. Sterge o carte dupa nume \n"
            + "5. Imprumuta o carte \n" + "6. Returneaza o carte \n" + "7. Iesire ");

    final static String colectionsMenu = ("\nSelectati un numar din meniu: \n" + "1. Afiseaza toate colectiile \n"
            + "2. Afisare colectie dupa nume \n" + "3. Calculeaza numarul de carti imprumutate \n" + "4. Iesire ");

    final static String libraryMenu = ("\nSelectati un numar din meniu: \n" + "1. Afiseaza toate cartile \n"
            + "2. Generare raport biblioteca \n" + "3. Iesire ");

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
                        case DRAGOSTE:
                            biblioteca.get(GenCarte.DRAGOSTE.toString()).colectie.add(carte);
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
                    if (carte.getNume().toLowerCase().equals(nume.toLowerCase())) {
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
        if (input.toLowerCase().equals(GenCarte.AVENTURA.toString().toLowerCase())) {
            return true;
        }
        if (input.toLowerCase().equals(GenCarte.COMEDIE.toString().toLowerCase())) {
            return true;
        }
        if (input.toLowerCase().equals(GenCarte.DRAGOSTE.toString().toLowerCase())) {
            return true;
        }
        if (input.toLowerCase().equals(GenCarte.DRAMA.toString().toLowerCase())) {
            return true;
        }
        if (input.toLowerCase().equals(GenCarte.EDUCATIONAL.toString().toLowerCase())) {
            return true;
        }
        if (input.toLowerCase().equals(GenCarte.POEZIE.toString().toLowerCase())) {
            return true;
        }
        if (input.toLowerCase().equals(GenCarte.TRAGEDIE.toString().toLowerCase())) {
            return true;
        }

        return false;
    }

    public static void stergeCarte(String nume, Map<String, ColectieCarti> biblioteca) {
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (!entry.getValue().colectie.isEmpty()) {
                for (Carte carte : entry.getValue().colectie) {
                    if (carte.getNume().toLowerCase().equals(nume.toLowerCase())) {
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
                    if (carte.getNume().toLowerCase().equals(nume.toLowerCase())) {
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
                    if (carte.getNume().toLowerCase().equals(nume.toLowerCase())) {
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
            if (entry.getKey().toLowerCase().equals(nume.toLowerCase())) {
                entry.getValue().afisare();
                return;
            }
        }
        System.out.println("Colectia nu exista");
    }

    public static void calculeazaNumarCartiImprumutate(Map<String, ColectieCarti> biblioteca) {
        int vectorFrecventa[] = new int[7];
        int index = 0;
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (!entry.getValue().colectie.isEmpty()) {
                for (Carte carte : entry.getValue().colectie) {
                    vectorFrecventa[index] += (carte.getNrExemplareTotale() - carte.getNrExemplareActual());
                }
            } else {
                vectorFrecventa[index] = 0;
            }
            System.out.println("Colectia " + entry.getKey() + " are " + vectorFrecventa[index] + " carti imprumutate");
            index++;
        }

        int suma = 0;
        for (int i = 0; i < vectorFrecventa.length; i++) {
            suma += vectorFrecventa[i];
        }
        System.out.println("In total au fost imprumutate " + suma + " carti din biblioteca");
    }

    public static float calculareNumarMediiPaginiColectie(String nume, Map<String, ColectieCarti> biblioteca) {
        float medie = 0;
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (entry.getKey().toLowerCase().equals(nume.toLowerCase())) {
                if (!entry.getValue().colectie.isEmpty()) {
                    for (Carte carte : entry.getValue().colectie) {
                        medie += carte.getNrPagini();
                    }
                    return medie / entry.getValue().colectie.size();
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }

    public static float calculareNumarMediiPaginiTotal(Map<String, ColectieCarti> biblioteca) {
        float medie = 0;
        int counter = 0;
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            if (!entry.getValue().colectie.isEmpty()) {
                for (Carte carte : entry.getValue().colectie) {
                    medie += carte.getNrPagini();
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
        String vectorFrecventa[] = new String[7];
        int counter = 0;
        float medie = 0;
        for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
            vectorFrecventa[counter] = String.valueOf(entry.getValue().colectie.size());
            counter++;
        }
        for (int i = 0; i < vectorFrecventa.length; i++) {
            medie += Integer.parseInt(vectorFrecventa[i]);
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
            writer.write("------------------------------------------------------------------------------\n");
            writer.write("-------------------------Raport text despre biblioteca------------------------\n\n");
            writer.write("Biblioteca are " + calculNumarCarti(biblioteca) + " carti\n");
            writer.write("Fiecare colectie are in medie " + calculeNumarCartiMediuPeColectie(biblioteca) + " carti\n");
            writer.write("Fiecare carte din biblioteca are in medie " + calculareNumarMediiPaginiTotal(biblioteca)
                    + " pagini\n");
            for (Map.Entry<String, ColectieCarti> entry : biblioteca.entrySet()) {
                float medie = calculareNumarMediiPaginiColectie(entry.getKey(), biblioteca);
                writer.write("Fiecare carte din colectia " + entry.getKey() + " are in medie " + medie + " pagini\n");
            }
            writer.close();

            System.out.println("Raportul a fost generat cu succes cu numele de " + file.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Scanner consoleInput = new Scanner(System.in);

        ColectieCarti colectieAventura = new ColectieCarti();
        ColectieCarti colectiePoezie = new ColectieCarti();
        ColectieCarti colectieDrama = new ColectieCarti();
        ColectieCarti colectieComedie = new ColectieCarti();
        ColectieCarti colectieTragedie = new ColectieCarti();
        ColectieCarti colectieDragoste = new ColectieCarti();
        ColectieCarti colectieEducational = new ColectieCarti();

		colectieAventura.colectie.add(new Carte("id-01-01", "Narnia", "C. S. Lewis", "Corint", 301, GenCarte.AVENTURA, 1, 4));
		colectieAventura.colectie.add(new Carte("id-01-02", "Harry Potter", "J. K. Rowling", "Corint", 241, GenCarte.AVENTURA, 5, 7));
		colectiePoezie.colectie.add(new Carte("id-02-01", "Poezii", "M. Eminescu", "Trei", 180, GenCarte.POEZIE, 9, 12));
		colectiePoezie.colectie.add(new Carte("id-02-02", "Cuvinte Potrivite", "T. Arghezi", "Tei", 172, GenCarte.POEZIE, 8, 8));
		colectieDrama.colectie.add(new Carte("id-03-01", "Romeo si Julieta", "W. Shakespeare", "Trei", 281, GenCarte.DRAMA, 5, 9));
		colectieDrama.colectie.add(new Carte("id-03-02", "MacBeth", "W. Shakespeare", "Corint", 241, GenCarte.DRAMA, 3, 3));
		colectieDrama.colectie.add(new Carte("id-03-03", "Hamlet", "W. Shakespeare", "Trei", 316, GenCarte.DRAMA, 8, 8));
		colectieComedie.colectie.add(new Carte("id-04-01", "Divina Comedie", "Dante Alighieri", "Aramis", 402, GenCarte.COMEDIE, 7, 7));
		colectieComedie.colectie.add(new Carte("id-04-02", "Jurnalul Unui Pusti", "Jeff Kinney", "Tei", 172, GenCarte.COMEDIE, 8, 9));
		colectieComedie.colectie.add(new Carte("id-04-03", "Jurnalul Unui Pusti 2", "Jeff Kinney", "Tei", 156, GenCarte.COMEDIE, 4, 6));
		colectieTragedie.colectie.add(new Carte("id-05-01", "Faust", "J. Goethe", "Trei", 327, GenCarte.TRAGEDIE, 7, 7));
		colectieTragedie.colectie.add(new Carte("id-05-02", "Iliada", "Homer", "Corint", 190, GenCarte.TRAGEDIE, 9, 9));
		colectieDragoste.colectie.add(new Carte("id-06-01", "Anna Karenina", "L. Tolstoy", "Corint", 281, GenCarte.DRAGOSTE, 5, 5));
		colectieDragoste.colectie.add(new Carte("id-06-02", "Ultima Noapte", "C. Petrescu", "Aramis", 312, GenCarte.DRAGOSTE, 1, 3));
		colectieDragoste.colectie.add(new Carte("id-06-03", "Mandrie si Prejudecata", "J. Austen", "Tei", 327, GenCarte.DRAGOSTE, 1, 6));
		colectieEducational.colectie.add(new Carte("id-07-01", "Manual Matematica Liceu", "Ministerul Educatiei", "Corint", 218, GenCarte.EDUCATIONAL, 7, 7));
		colectieDragoste.colectie.add(new Carte("id-06-04", "La rascruce de vanturi", "Emily Bronte", "Aramis", 372, GenCarte.DRAGOSTE, 2, 4));
		colectieEducational.colectie.add(new Carte("id-07-02", "Manual Fizica Liceu", "Ministerul Educatiei", "Corint", 190, GenCarte.EDUCATIONAL, 3, 5));
		colectieEducational.colectie.add(new Carte("id-07-03", "Manual Chimie Liceu", "Ministerul Educatiei", "Aramis", 267, GenCarte.EDUCATIONAL, 7, 13));
		colectieEducational.colectie.add(new Carte("id-07-04", "Manual Biologie Liceu", "Ministerul Educatiei", "Aramis", 252, GenCarte.EDUCATIONAL, 7, 9));
		colectieEducational.colectie.add(new Carte("id-07-05", "Manual Informatica Liceu", "Ministerul Educatiei", "Corint", 280, GenCarte.EDUCATIONAL, 10, 12));

        Map<String, ColectieCarti> biblioteca = new HashMap<String, ColectieCarti>();
        biblioteca.put(GenCarte.AVENTURA.toString(), colectieAventura);
        biblioteca.put(GenCarte.POEZIE.toString(), colectiePoezie);
        biblioteca.put(GenCarte.DRAMA.toString(), colectieDrama);
        biblioteca.put(GenCarte.COMEDIE.toString(), colectieComedie);
        biblioteca.put(GenCarte.TRAGEDIE.toString(), colectieTragedie);
        biblioteca.put(GenCarte.DRAGOSTE.toString(), colectieDragoste);
        biblioteca.put(GenCarte.EDUCATIONAL.toString(), colectieEducational);

        File file = new File("carti.txt");
        try {
            createTextFile(file);
            readTextFile(file, biblioteca);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(welcome);
        int menuSelection;
        int specificMenuSelection;

        String id;
        String nume;
        String autor;
        String editura;
        int nrPagini;
        GenCarte tipCarte;
        int nrExemplareTotale;

        do {
            System.out.println(mainMenu);
            menuSelection = consoleInput.nextInt();
            if (menuSelection < 1 || menuSelection > 5) {
                System.out.println("Eroare! Selectia dvs nu exista.");
                System.out.println(mainMenu);
                menuSelection = consoleInput.nextInt();
            }

            switch (menuSelection) {
                case 1:
                    do {
                        System.out.println(booksMenu);
                        specificMenuSelection = consoleInput.nextInt();
                        if (specificMenuSelection < 1 || specificMenuSelection > 7) {
                            System.out.println("Eroare! Selectia dvs nu exista.");
                            System.out.println(booksMenu);
                            specificMenuSelection = consoleInput.nextInt();
                        }
                        switch (specificMenuSelection) {
                            case 1:
                                afisareBiblioteca(biblioteca);
                                break;
                            case 2:
                                boolean isFound = false;
                                int counter = 0;
                                String searchBar;
                                int searchNumbersBar;
                                do {
                                    if (counter == 0) {
                                        searchBar = consoleInput.nextLine();
                                        System.out.println("Scrieti 'Stop' pentru a iesi");
                                        counter++;
                                    }
                                    System.out.println("Scrieti numele cartii:");
                                    searchBar = consoleInput.nextLine();
                                    if (searchBar.length() < 3) {
                                        System.out.println("Nume prea scurt!");
                                        System.out.println("Scrieti numele cartii:");
                                        searchBar = consoleInput.nextLine();
                                    }
                                    if (searchBar.toLowerCase().equals("stop")) {
                                        isFound = true;
                                    } else {
                                        isFound = afiseazaCarteDupaNume(searchBar, biblioteca);
                                        if (!isFound) {
                                            System.out.println("Cartea nu exista!");
                                        }
                                    }

                                } while (!isFound);
                                break;
                            case 3:
                                System.out.println("Adaugati o carte");
                                searchBar = consoleInput.nextLine();

                                searchBar = citesteString(consoleInput, "id-ul(seria)");
                                id = searchBar;

                                searchBar = citesteString(consoleInput, "numele cartii");
                                nume = searchBar;

                                searchBar = citesteString(consoleInput, "numele autorului");
                                autor = searchBar;

                                searchBar = citesteString(consoleInput, "numele editurii");
                                editura = searchBar;

                                searchNumbersBar = citesteInt(consoleInput, "numarul de pagini");
                                nrPagini = searchNumbersBar;

                                searchBar = consoleInput.nextLine();
                                searchBar = citesteGenCarte(consoleInput, "genul cartii");
                                tipCarte = GenCarte.valueOf(searchBar.toUpperCase());

                                searchNumbersBar = citesteInt(consoleInput, "numarul de exemplare primite de biblioteca");
                                nrExemplareTotale = searchNumbersBar;

                                Carte carte = new Carte(id, nume, autor, editura, nrPagini, tipCarte, nrExemplareTotale,
                                        nrExemplareTotale);
                                switch (carte.getTipCarte()) {
                                    case AVENTURA:
                                        biblioteca.get(GenCarte.AVENTURA.toString()).colectie.add(carte);
                                        break;
                                    case COMEDIE:
                                        biblioteca.get(GenCarte.COMEDIE.toString()).colectie.add(carte);
                                        break;
                                    case DRAGOSTE:
                                        biblioteca.get(GenCarte.DRAGOSTE.toString()).colectie.add(carte);
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
                                System.out.println("Carte adaugata cu succes");
                                break;
                            case 4:
                                searchBar = consoleInput.nextLine();
                                System.out.println("Introduceti numele cartii:");
                                searchBar = consoleInput.nextLine();
                                if (searchBar.length() < 4) {
                                    System.out.println("Numele introdus este prea scurt!");
                                    break;
                                }
                                stergeCarte(searchBar, biblioteca);
                                break;
                            case 5:
                                searchBar = consoleInput.nextLine();

                                System.out.println("Introduceti numele cartii:");
                                searchBar = consoleInput.nextLine();
                                if (searchBar.length() < 4) {
                                    System.out.println("Numele introdus este prea scurt!");
                                    break;
                                }
                                imprumutaCarte(searchBar, biblioteca);
                                break;
                            case 6:
                                searchBar = consoleInput.nextLine();

                                System.out.println("Introduceti numele cartii:");
                                searchBar = consoleInput.nextLine();
                                if (searchBar.length() < 4) {
                                    System.out.println("Numele introdus este prea scurt!");
                                    break;
                                }
                                returneazaCarte(searchBar, biblioteca);
                                break;
                            case 7:
                                break;
                        }
                    } while (specificMenuSelection != 7);
                    break;

                case 2:
                    String searchBar;
                    do {
                        System.out.println(colectionsMenu);
                        specificMenuSelection = consoleInput.nextInt();
                        if (specificMenuSelection < 1 || specificMenuSelection > 4) {
                            System.out.println("Eroare! Selectia dvs nu exista.");
                            System.out.println(colectionsMenu);
                            specificMenuSelection = consoleInput.nextInt();
                        }
                        switch (specificMenuSelection) {
                            case 1:
                                afisareBiblioteca(biblioteca);
                                break;
                            case 2:
                                searchBar = consoleInput.nextLine();
                                System.out.println("Introduceti numele colectiei:");
                                searchBar = consoleInput.nextLine();
                                if (searchBar.length() < 4) {
                                    System.out.println("Numele introdus este prea scurt!");
                                    break;
                                }
                                afiseazaColectie(searchBar, biblioteca);
                                break;
                            case 3:
                                calculeazaNumarCartiImprumutate(biblioteca);
                                break;
                            case 4:
                                break;
                        }
                    } while (specificMenuSelection != 4);
                    break;

                case 3:
                    do {
                        System.out.println(libraryMenu);
                        specificMenuSelection = consoleInput.nextInt();
                        if (specificMenuSelection < 1 || specificMenuSelection > 3) {
                            System.out.println("Eroare! Selectia dvs nu exista.");
                            System.out.println(libraryMenu);
                            specificMenuSelection = consoleInput.nextInt();
                        }
                        switch (specificMenuSelection) {
                            case 1:
                                afisareBiblioteca(biblioteca);
                                break;
                            case 2:
                                generareRaportText(biblioteca);
                                break;
                            case 3:
                                break;
                        }
                    } while (specificMenuSelection != 3);
                    break;

                case 4:
                    System.out.println("Multumim ca ati folosit aplicatia noastra!");

                    try {
                        saveToTextFile(file, biblioteca);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        } while (menuSelection != 4);

    }

}
