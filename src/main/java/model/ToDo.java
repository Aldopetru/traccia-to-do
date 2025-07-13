package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un'attività (ToDo) all'interno di una bacheca.
 * Contiene informazioni come titolo, descrizione, data di scadenza, colore, utenti condivisi e checklist.
 */
public class ToDo {

    /** Titolo del ToDo */
    public String titolo_todo;

    /** URL associato al ToDo */
    public String URL;

    /** Descrizione dettagliata del ToDo */
    public String descrizione;

    /** Data di scadenza del ToDo */
    public LocalDate datascadenza;

    /** Percorso dell'immagine associata al ToDo */
    public String image;

    /** Colore di sfondo per la visualizzazione del ToDo */
    public String colore_sfondo;

    /** Colore del titolo per la visualizzazione del ToDo */
    public String colore_titolo;

    /** Stato di completamento del ToDo */
    public boolean Stato_todo = false;

    /** Elenco degli utenti con cui il ToDo è condiviso */
    public List<String> utenti_condivisi;

    /** Ordine di visualizzazione del ToDo nella bacheca */
    public int ordine;

    /** Nome utente del creatore del ToDo */
    public String username;

    /** Bacheca di appartenenza del ToDo */
    private Bacheca bacheca;

    /** Identificativo univoco del ToDo nel database */
    private int id;

    /** Lista delle voci della checklist associate al ToDo */
    private List<Check_list> checklist = new ArrayList<>();

    /**
     * Costruttore della classe ToDo.
     *
     * @param titolo_todo titolo dell'attività
     * @param URL URL collegato
     * @param descrizione descrizione dell'attività
     * @param datascadenza data di scadenza
     * @param image immagine associata
     * @param colore_sfondo colore di sfondo
     * @param colore_titolo colore del titolo
     * @param Stato_todo stato di completamento
     * @param utenti_condivisi utenti con cui è condiviso
     * @param ordine ordine di visualizzazione
     * @param username autore del ToDo
     * @param bacheca bacheca di appartenenza
     */
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

    /**
     * Restituisce la bacheca di appartenenza del ToDo.
     * @return oggetto {@link Bacheca}
     */
    public Bacheca getBacheca() {
        return bacheca;
    }

    /**
     * Imposta la bacheca di appartenenza del ToDo.
     * @param nuova nuova bacheca
     */
    public void setBacheca(Bacheca nuova) {
        this.bacheca = nuova;
    }

    /**
     * Restituisce la checklist associata al ToDo.
     * @return lista di {@link Check_list}
     */
    public List<Check_list> getChecklist() {
        return checklist;
    }

    /**
     * Imposta la checklist del ToDo.
     * @param checklist nuova checklist
     */
    public void setChecklist(List<Check_list> checklist) {
        this.checklist = checklist;
    }

    /**
     * Restituisce l'ID del ToDo.
     * @return ID intero
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'ID del ToDo.
     * @param id ID da assegnare
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Rappresentazione testuale del ToDo.
     * @return stringa con titolo e scadenza
     */
    @Override
    public String toString() {
        return titolo_todo + " (scadenza: " + datascadenza + ")";
    }
}


