package dao;

import model.ToDo;
import model.Bacheca;

import java.util.List;

/**
 * Interfaccia per l'accesso ai dati relativi agli oggetti {@link ToDo}.
 * Definisce le operazioni CRUD e la gestione degli utenti condivisi.
 */
public interface ToDoDAO {

    /**
     * Crea un nuovo ToDo nel database.
     *
     * @param todo l'oggetto {@link ToDo} da salvare
     */
    void creaToDo(ToDo todo);

    /**
     * Recupera tutti i ToDo relativi a una determinata bacheca e utente.
     *
     * @param bacheca  la {@link Bacheca} di riferimento
     * @param username l'username dell'utente
     * @return lista di oggetti {@link ToDo} associati
     */
    List<ToDo> getToDoPerBacheca(Bacheca bacheca, String username);

    /**
     * Elimina un ToDo dal database.
     *
     * @param id l'ID del ToDo da eliminare
     */
    void eliminaToDo(int id);

    /**
     * Aggiorna le informazioni di un ToDo esistente.
     *
     * @param todo l'oggetto {@link ToDo} aggiornato
     */
    void aggiornaToDo(ToDo todo);

    /**
     * Recupera un ToDo dato il suo ID.
     *
     * @param id l'ID del ToDo
     * @return l'oggetto {@link ToDo} trovato, oppure {@code null} se non esiste
     */
    ToDo getById(int id);

    /**
     * Restituisce la lista completa di tutti i ToDo presenti.
     *
     * @return lista di tutti i {@link ToDo}
     */
    List<ToDo> getTutti();

    /**
     * Aggiunge uno o più utenti con cui un ToDo è condiviso.
     *
     * @param todoId    l'ID del ToDo
     * @param usernames lista di username da associare
     */
    void aggiungiUtentiCondivisi(int todoId, List<String> usernames);

    /**
     * Restituisce gli utenti con cui un ToDo è condiviso.
     *
     * @param todoId l'ID del ToDo
     * @return lista di username
     */
    List<String> getUtentiCondivisi(int todoId);

    /**
     * Rimuove tutti gli utenti con cui un ToDo è stato condiviso.
     *
     * @param todoId l'ID del ToDo
     */
    void eliminaUtentiCondivisi(int todoId);
}





