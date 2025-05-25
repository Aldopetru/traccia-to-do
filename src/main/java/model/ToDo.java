package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToDo {
    public String titolo_todo;
    public String URL;
    public String descrizione;
    public LocalDate datascadenza;
    public String image;
    public String colore_sfondo;
    public String colore_titolo;
    public boolean Stato_todo = false;
    public List<String> utenti_condivisi;
    public int ordine;
    public String username;
    private Bacheca bacheca;

    private List<Check_list> checklist = new ArrayList<>();

    public ToDo(String titolo_todo, String URL, String descrizione, LocalDate datascadenza,
                String image, String colore_sfondo, String colore_titolo,
                boolean Stato_todo, List<String> utenti_condivisi, int ordine,
                String username, Bacheca bacheca) {

        this.titolo_todo = titolo_todo;
        this.URL = URL;
        this.descrizione = descrizione;
        this.datascadenza = datascadenza;
        this.image = image;
        this.colore_sfondo = colore_sfondo;
        this.colore_titolo = colore_titolo;
        this.Stato_todo = Stato_todo;
        this.utenti_condivisi = utenti_condivisi;
        this.ordine = ordine;
        this.username = username;
        this.bacheca = bacheca;
    }

    public Bacheca getBacheca() {
        return bacheca;
    }

    public List<Check_list> getChecklist() {
        return checklist;
    }

    public void setChecklist(List<Check_list> checklist) {
        this.checklist = checklist;
    }

    @Override
    public String toString() {
        return titolo_todo + " (scadenza: " + datascadenza + ")";
    }
}

