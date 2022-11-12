package ro.ase.ppoo.entitati;

public class Statics {

    public final static String welcome = "Bun venit in aplicatia de gestionare a Bibliotecii X!";

    public final static String mainMenu = ("\nSelectati un numar din meniu: \n" + "1. Meniu carti \n" + "2. Meniu colectii \n"
            + "3. Meniu biblioteca \n" + "4. Iesire ");

    public final static String booksMenu = ("\nSelectati un numar din meniu: \n" + "1. Afiseaza toate cartile \n"
            + "2. Afisare carte dupa nume \n" + "3. Adauga o carte \n" + "4. Sterge o carte dupa nume \n"
            + "5. Imprumuta o carte \n" + "6. Returneaza o carte \n" + "7. Iesire ");

    public final static String colectionsMenu = ("\nSelectati un numar din meniu: \n" + "1. Afiseaza toate colectiile \n"
            + "2. Afisare colectie dupa nume \n" + "3. Calculeaza numarul de carti imprumutate \n" + "4. Iesire ");

    public final static String libraryMenu = ("\nSelectati un numar din meniu: \n" + "1. Afiseaza toate cartile \n"
            + "2. Generare raport biblioteca \n" + "3. Iesire ");

    public static String getWelcome() {
        return welcome;
    }

    public static String getMainMenu() {
        return mainMenu;
    }

    public static String getBooksMenu() {
        return booksMenu;
    }

    public static String getColectionsMenu() {
        return colectionsMenu;
    }

    public static String getLibraryMenu() {
        return libraryMenu;
    }

}
