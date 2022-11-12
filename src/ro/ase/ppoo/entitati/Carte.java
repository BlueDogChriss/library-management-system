package ro.ase.ppoo.entitati;

import java.io.Serializable;

public class Carte implements Serializable {
    private String id;
    private String nume;
    private String autor;
    private String editura;
    private int nrPagini;
    private GenCarte tipCarte;
    private int nrExemplareActual;
    private int nrExemplareTotale;

    public Carte() {
    }

    public Carte(String id, String nume, String autor, String editura, int nrPagini, GenCarte tipCarte,
                 int nrExemplareActual, int nrExemplareTotale) {
        super();
        this.id = id;
        this.nume = nume;
        this.autor = autor;
        this.editura = editura;
        this.nrPagini = nrPagini;
        this.tipCarte = tipCarte;
        this.nrExemplareActual = nrExemplareActual;
        this.nrExemplareTotale = nrExemplareTotale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditura() {
        return editura;
    }

    public void setEditura(String editura) {
        this.editura = editura;
    }

    public int getNrPagini() {
        return nrPagini;
    }

    public void setNrPagini(int nrPagini) {
        this.nrPagini = nrPagini;
    }

    public GenCarte getTipCarte() {
        return tipCarte;
    }

    public void setTipCarte(GenCarte tipCarte) {
        this.tipCarte = tipCarte;
    }

    public int getNrExemplareActual() {
        return nrExemplareActual;
    }

    public void setNrExemplareActual(int nrExemplareActual) {
        this.nrExemplareActual = nrExemplareActual;
    }

    public int getNrExemplareTotale() {
        return nrExemplareTotale;
    }

    public void setNrExemplareTotale(int nrExemplareTotale) {
        this.nrExemplareTotale = nrExemplareTotale;
    }

    @Override
    public String toString() {
        return "Carte [id=" + id + ", nume=" + nume + ", autor=" + autor + ", editura=" + editura + ", nrPagini="
                + nrPagini + ", tipCarte=" + tipCarte + ", nrExemplareActual=" + nrExemplareActual
                + ", nrExemplareTotale=" + nrExemplareTotale + "]";
    }


}
