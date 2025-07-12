package dao;

import model.Bacheca;
import java.util.List;

/**
 * Interfaccia per la gestione dell'accesso ai dati relativi alle bacheche.
 */
public interface BachecaDAO {

    /**
     * Aggiorna la descrizione di una bacheca dato il suo ID.
     *
     * @param id               ID della bacheca
     * @param nuovaDescrizione nuova descrizione da impostare
     */
    void aggiornaDescrizione(int id, String nuovaDescrizione);

    /**
     * Restituisce una bacheca dato il suo ID.
     *
     * @param id ID della bacheca
     * @return la bacheca corrispondente, o null se non trovata
     */
    Bacheca getById(int id);

    /**
     * Crea una nuova bacheca per un utente.
     *
     * @param username    nome utente
     * @param tipo        tipo della bacheca
     * @param descrizione descrizione opzionale
     * @return la bacheca creata
     */
    Bacheca creaBacheca(String username, Bacheca.tipoBacheca tipo, String descrizione);

    /**
     * Restituisce l'elenco delle bacheche associate a un utente.
     *
     * @param username nome utente
     * @return lista di bacheche
     */
    List<Bacheca> getBachechePerUtente(String username);

    /**
     * Elimina una bacheca dato il suo ID.
     *
     * @param bachecaId ID della bacheca da eliminare
     */
    void eliminaBacheca(int bachecaId);

    /**
     * Restituisce una bacheca dato il suo ID (metodo alternativo a getById).
     *
     * @param id ID della bacheca
     * @return la bacheca trovata, o null se non esiste
     */
    Bacheca getBachecaById(int id);
}


