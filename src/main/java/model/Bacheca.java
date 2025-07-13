package model;

/**
 * Rappresenta una bacheca appartenente a un utente.
 * Ogni bacheca ha un tipo (Università, Lavoro, Tempo Libero),
 * una descrizione facoltativa e un utente proprietario.
 */
public class Bacheca {

    /**
     * Enum che rappresenta le tipologie di bacheca disponibili.
     */
    public enum tipoBacheca {
        Universita,
        Lavoro,
        TempoLibero
    }

    /** Identificativo univoco della bacheca (PK). */
    public int id;

    /** Tipo della bacheca (Università, Lavoro, Tempo Libero). */
    public tipoBacheca tipo;

    /** Titolo visualizzato della bacheca. */
    public String titolo_b;

    /** Descrizione opzionale della bacheca. */
    public String descrizione;

    /** Username dell’utente proprietario della bacheca. */
    public String username;

    /**
     * Costruttore base per creare una bacheca con tipo e descrizione.
     *
     * @param tipo         Tipo della bacheca
     * @param descrizione  Descrizione della bacheca
     */
    public Bacheca(tipoBacheca tipo, String descrizione) {
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.titolo_b = tipo.name();
    }

    /**
     * Costruttore con tipo, descrizione e username.
     *
     * @param tipo         Tipo della bacheca
     * @param descrizione  Descrizione della bacheca
     * @param username     Username del proprietario
     */
    public Bacheca(tipoBacheca tipo, String descrizione, String username) {
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.username = username;
        this.titolo_b = tipo.name();
    }

    /**
     * Costruttore che imposta un titolo personalizzato per la bacheca,
     * includendo il nome utente.
     *
     * @param tipo             Tipo della bacheca
     * @param username         Username del proprietario
     * @param descrizione      Descrizione della bacheca
     * @param includeUsername  Flag per usare titolo personalizzato
     */
    public Bacheca(tipoBacheca tipo, String username, String descrizione, boolean includeUsername) {
        this.tipo = tipo;
        this.username = username;
        this.descrizione = descrizione;
        this.titolo_b = "Bacheca di " + username + " (" + tipo.toString() + ")";
    }

    /**
     * Imposta l'ID della bacheca.
     *
     * @param id Identificativo numerico
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce l'ID della bacheca.
     *
     * @return ID numerico
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta una nuova descrizione per la bacheca.
     *
     * @param descrizione Nuova descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Ritorna una rappresentazione testuale della bacheca per la visualizzazione.
     *
     * @return Stringa nel formato: [tipo] - descrizione oppure [tipo] se la descrizione è vuota
     */
    @Override
    public String toString() {
        if (descrizione == null || descrizione.trim().isEmpty()) {
            return "[" + tipo + "]";
        } else {
            return "[" + tipo + "] - " + descrizione;
        }
    }
}




