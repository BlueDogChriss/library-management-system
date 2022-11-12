package ro.ase.ppoo;

import ro.ase.ppoo.entitati.Carte;
import ro.ase.ppoo.entitati.ColectieCarti;
import ro.ase.ppoo.entitati.GenCarte;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static ro.ase.ppoo.entitati.Statics.*;
import static ro.ase.ppoo.functii.Functii.*;


public class Main {


    public static void main(String[] args) {

        Scanner consoleInput = new Scanner(System.in);

        ColectieCarti colectieAventura = new ColectieCarti();
        ColectieCarti colectiePoezie = new ColectieCarti();
        ColectieCarti colectieDrama = new ColectieCarti();
        ColectieCarti colectieComedie = new ColectieCarti();
        ColectieCarti colectieTragedie = new ColectieCarti();
        ColectieCarti colectieRomance = new ColectieCarti();
        ColectieCarti colectieEducational = new ColectieCarti();

        Map<String, ColectieCarti> biblioteca = new HashMap<String, ColectieCarti>();
        biblioteca.put(GenCarte.AVENTURA.toString(), colectieAventura);
        biblioteca.put(GenCarte.POEZIE.toString(), colectiePoezie);
        biblioteca.put(GenCarte.DRAMA.toString(), colectieDrama);
        biblioteca.put(GenCarte.COMEDIE.toString(), colectieComedie);
        biblioteca.put(GenCarte.TRAGEDIE.toString(), colectieTragedie);
        biblioteca.put(GenCarte.ROMANCE.toString(), colectieRomance);
        biblioteca.put(GenCarte.EDUCATIONAL.toString(), colectieEducational);

        File file = new File("cartiDB.txt");
        try {
            createTextFile(file);
            readTextFile(file, biblioteca);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(getWelcome());
        int menuSelection;
        int specificMenuSelection;

        String id;
        String nume;
        String autor;
        String editura;
        int nota;
        GenCarte tipCarte;
        int nrExemplareTotale;

        do {
            System.out.println(getMainMenu());
            menuSelection = consoleInput.nextInt();
            if (menuSelection < 1 || menuSelection > 5) {
                System.out.println("Eroare! Selectia dvs nu exista.");
                System.out.println(getMainMenu());
                menuSelection = consoleInput.nextInt();
            }

            switch (menuSelection) {
                case 1:
                    do {
                        System.out.println(getBooksMenu());
                        specificMenuSelection = consoleInput.nextInt();
                        if (specificMenuSelection < 1 || specificMenuSelection > 7) {
                            System.out.println("Eroare! Selectia dvs nu exista.");
                            System.out.println(getBooksMenu());
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
                                        System.out.println("Scrieti 'Exit' pentru a iesi");
                                        counter++;
                                    }
                                    System.out.println("Scrieti numele cartii:");
                                    searchBar = consoleInput.nextLine();
                                    if (searchBar.length() < 3) {
                                        System.out.println("Nume prea scurt!");
                                        System.out.println("Scrieti numele cartii:");
                                        searchBar = consoleInput.nextLine();
                                    }
                                    if (searchBar.equalsIgnoreCase("exit")) {
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

                                searchBar = citesteString(consoleInput, "id-ul (ex: id-idColectie-idCarte)");
                                id = searchBar;

                                searchBar = citesteString(consoleInput, "Numele Cartii:");
                                nume = searchBar;

                                searchBar = citesteString(consoleInput, "Autorul:");
                                autor = searchBar;

                                searchBar = citesteString(consoleInput, "Numele Editurii:");
                                editura = searchBar;

                                searchNumbersBar = citesteInt(consoleInput, "Introduceti Nota Cartii:");
                                nota = searchNumbersBar;

                                searchBar = consoleInput.nextLine();
                                searchBar = citesteGenCarte(consoleInput, "Genul Cartii:");
                                tipCarte = GenCarte.valueOf(searchBar.toUpperCase());

                                searchNumbersBar = citesteInt(consoleInput, "Precizati Numarul de exemplare primite:");
                                nrExemplareTotale = searchNumbersBar;

                                Carte carte = new Carte(id, nume, autor, editura, nota, tipCarte, nrExemplareTotale, nrExemplareTotale);
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
                                System.out.println("Cartea Dumneavoastra a fost adaugata cu succes in sistem!");
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
                        System.out.println(getColectionsMenu());
                        specificMenuSelection = consoleInput.nextInt();
                        if (specificMenuSelection < 1 || specificMenuSelection > 4) {
                            System.out.println("Eroare! Selectia dvs nu exista.");
                            System.out.println(getColectionsMenu());
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
                        System.out.println(getLibraryMenu());
                        specificMenuSelection = consoleInput.nextInt();
                        if (specificMenuSelection < 1 || specificMenuSelection > 3) {
                            System.out.println("Eroare! Selectia dvs nu exista.");
                            System.out.println(getLibraryMenu());
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

//        colectieAventura.colectie.add(new Carte("id-01-01", "Moby Dick", "Herman Melville ", "ART", 8, GenCarte.AVENTURA, 3, 7));
//        colectieAventura.colectie.add(new Carte("id-01-02", "Imparatul mustelor", "William Golding", "Humanitas", 7.5f, GenCarte.AVENTURA, 1, 23));
//        colectieAventura.colectie.add(new Carte("id-01-03", "Harry Potter", "J. K. Rowling", "Corint", 10, GenCarte.AVENTURA, 5, 7));
//        colectiePoezie.colectie.add(new Carte("id-02-01", "Poezii", "M. Eminescu", "ART", 8.5f, GenCarte.POEZIE, 9, 12));
//        colectiePoezie.colectie.add(new Carte("id-02-02", "Poezii", "Vasile Alecsandri", "Teora", 7.5f, GenCarte.POEZIE, 8, 8));
//        colectiePoezie.colectie.add(new Carte("id-02-03", "Poezia", "Mircea Cartarescu", "Humanitas", 5.5f, GenCarte.POEZIE, 8, 8));
//        colectieDrama.colectie.add(new Carte("id-03-01", "Romeo si Julieta", "W. Shakespeare", "ART", 7, GenCarte.DRAMA, 1, 20));
//        colectieComedie.colectie.add(new Carte("id-04-01", "2 Loturi", "I. L. Caragiale", "Aramis", 6.5f, GenCarte.COMEDIE, 7, 7));
//        colectieComedie.colectie.add(new Carte("id-04-02", "Jurnalul Unui Burlac", "Mihai Bendeac", "Humanitas", 6.5f, GenCarte.COMEDIE, 0, 9));
//        colectieComedie.colectie.add(new Carte("id-04-03", "O scrisoare pierduta", "I. L. Caragiale", "Aramis", 10f, GenCarte.COMEDIE, 4, 6));
//        colectieTragedie.colectie.add(new Carte("id-05-01", "Iliada", "Homer", "Corint", 9, GenCarte.TRAGEDIE, 9, 9));
//        colectieRomance.colectie.add(new Carte("id-06-01", "Inainte Sa Te Cunosc", "Jojo Moyes", "ART", 10f, GenCarte.ROMANCE, 2, 5));
//        colectieRomance.colectie.add(new Carte("id-06-02", "Pe Aripile Vantului", "Margaret Mitchell", "Aramis", 10f, GenCarte.ROMANCE, 1, 10));
//        colectieRomance.colectie.add(new Carte("id-06-04", "Maitreyi", "Mircea Eliade", "Humanitas", 10, GenCarte.ROMANCE, 1, 4));
//        colectieEducational.colectie.add(new Carte("id-07-01", "Manual Matematica Liceu", "Ministerul Educatiei", "Teora", 5, GenCarte.EDUCATIONAL, 7, 7));
//        colectieEducational.colectie.add(new Carte("id-07-02", "Manual Fizica Liceu", "Ministerul Educatiei", "Teora", 6, GenCarte.EDUCATIONAL, 3, 5));
//        colectieEducational.colectie.add(new Carte("id-07-03", "Manual Chimie Liceu", "Ministerul Educatiei", "Teora", 4, GenCarte.EDUCATIONAL, 7, 13));
//        colectieEducational.colectie.add(new Carte("id-07-04", "Manual Biologie Liceu", "Ministerul Educatiei", "Teora", 8, GenCarte.EDUCATIONAL, 7, 9));
//        colectieEducational.colectie.add(new Carte("id-07-05", "Manual Informatica Liceu", "Ministerul Educatiei", "Teora", 10, GenCarte.EDUCATIONAL, 10, 12));
    }

}
