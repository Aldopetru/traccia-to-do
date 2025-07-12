package dao;

import model.Utente;

import java.util.List;

/**
 * Interfaccia per l'accesso ai dati relativi agli utenti.
 * Definisce le operazioni CRUD fondamentali.
 */
public interface UtenteDAO {

    /**
     * Crea un nuovo utente nel database.
     *
     * @param utente l'oggetto {@link Utente} da salvare
     */
    void creaUtente(Utente utente);

    /**
     * Verifica se esiste un utente con le credenziali fornite.
     *
     * @param username il nome utente
     * @param password la password
     * @return {@code true} se le credenziali corrispondono, altrimenti {@code false}
     */
    boolean verificaUtente(String username, String password);

    /**
     * Restituisce la lista completa di tutti gli utenti registrati.
     *
     * @return lista di oggetti {@link Utente}
     */
    List<Utente> getTuttiUtenti();

    /**
     * Recupera un utente a partire dal suo username.
     *
     * @param username il nome utente
     * @return l'oggetto {@link Utente} corrispondente, oppure {@code null} se non trovato
     */
    Utente getUtenteByUsername(String username);
}

