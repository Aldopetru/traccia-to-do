package model;

public class Bacheca {

    public enum tipoBacheca {
        Lavoro,
        Universita,
        TempoLibero
    }

    public tipoBacheca titolo_b;
    public String descrizione;

    public Bacheca(tipoBacheca titolo_b, String descrizione) {
        this.titolo_b = titolo_b;
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return titolo_b + " - " + descrizione;
    }
}


