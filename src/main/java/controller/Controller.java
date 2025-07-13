package controller;

import model.*;
import dao.*;
import daoimpl.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Controller che funge da punto centrale per la logica applicativa.
 * Utilizza i DAO per interagire con il database.
 */
public class Controller {

    private static final UtenteDAO utenteDAO = new PostgresUtenteDAO();
    private static final BachecaDAO bachecaDAO = new PostgresBachecaDAO();
    private static final ToDoDAO todoDAO = new PostgresToDoDAO();
    private static final CheckListDAO checklistDAO = new PostgresCheckListDAO();

    /**
     * Crea un nuovo utente.
     */
    public static void creaUtente(String username, String password) {
        utenteDAO.creaUtente(new Utente(username, password));
    }

    /**
     * Verifica se l'utente esiste con username e password corretti.
     */
    public static boolean verificaUtente(String username, String password) {
        return utenteDAO.verificaUtente(username, password);
    }

    /**
     * Restituisce la lista di tutti gli utenti.
     */
    public static List<Utente> getUtenti() {
        return utenteDAO.getTuttiUtenti();
    }

    /**
     * Crea una nuova bacheca per l'utente, se non ne possiede già una dello stesso tipo.
     */
    public static Bacheca creaBacheca(String username, Bacheca.tipoBacheca tipo, String descrizione) {
        List<Bacheca> esistenti = bachecaDAO.getBachechePerUtente(username);
        for (Bacheca b : esistenti) {
            if (b.tipo == tipo) {
                throw new IllegalArgumentException("Hai già una bacheca di tipo " + tipo.name());
            }
        }
        return bachecaDAO.creaBacheca(username, tipo, descrizione);
    }

    /**
     * Restituisce tutte le bacheche associate a un utente.
     */
    public static List<Bacheca> getBachechePerUtente(String username) {
        return bachecaDAO.getBachechePerUtente(username);
    }

    /**
     * Elimina una bacheca.
     */
    public static void eliminaBacheca(String username, Bacheca bacheca) {
        bachecaDAO.eliminaBacheca(bacheca.getId());
    }

    /**
     * Crea un nuovo ToDo.
     */
    public static void creaToDo(String titolo, String url, String descrizione, LocalDate dataScadenza,
                                String immagine, String coloreSfondo, String coloreTitolo, boolean stato,
                                List<String> condivisi, int ordine, String username, Bacheca bacheca) {
        ToDo todo = new ToDo(titolo, url, descrizione, dataScadenza, immagine, coloreSfondo,
                coloreTitolo, stato, condivisi, ordine, username, bacheca);
        todoDAO.creaToDo(todo);
    }

    /**
     * Verifica se esiste un utente dato lo username.
     */
    public static boolean utenteEsiste(String username) {
        return utenteDAO.getUtenteByUsername(username) != null;
    }

    /**
     * Aggiorna la descrizione di una bacheca.
     */
    public static void aggiornaDescrizioneBacheca(int id, String nuovaDescrizione) {
        bachecaDAO.aggiornaDescrizione(id, nuovaDescrizione);
    }

    /**
     * Restituisce la lista dei ToDo visibili all'utente (propri o condivisi).
     */
    public static List<ToDo> getListaToDo(String username) {
        List<ToDo> tutti = todoDAO.getTutti();
        List<ToDo> validi = new ArrayList<>();
        for (ToDo t : tutti) {
            if (t.getBacheca() != null && (t.username.equals(username) || t.utenti_condivisi.contains(username))) {
                validi.add(t);
            }
        }
        return validi;
    }

    /**
     * Restituisce i ToDo associati a una bacheca specifica e visibili all'utente.
     */
    public static List<ToDo> getToDoPerBacheca(Bacheca b, String username) {
        List<ToDo> validi = new ArrayList<>();
        for (ToDo t : todoDAO.getTutti()) {
            if (t.getBacheca() != null &&
                    t.getBacheca().getId() == b.getId() &&
                    (t.username.equals(username) || t.utenti_condivisi.contains(username))) {
                validi.add(t);
            }
        }
        return validi;
    }

    /**
     * Restituisce i ToDo condivisi con l'utente.
     */
    public static List<ToDo> getToDoCondivisiConUtente(String username) {
        List<ToDo> condivisi = new ArrayList<>();
        for (ToDo t : todoDAO.getTutti()) {
            if (t.utenti_condivisi.contains(username)) {
                condivisi.add(t);
            }
        }
        return condivisi;
    }

    /**
     * Elimina un ToDo.
     */
    public static void eliminaToDo(ToDo todo) {
        todoDAO.eliminaToDo(todo.getId());
    }

    /**
     * Aggiorna un ToDo (inclusi gli utenti condivisi).
     */
    public static void aggiornaToDo(ToDo todo) {
        todoDAO.aggiornaToDo(todo);
        todoDAO.eliminaUtentiCondivisi(todo.getId());
        todoDAO.aggiungiUtentiCondivisi(todo.getId(), todo.utenti_condivisi);
    }

    /**
     * Sposta un ToDo in una nuova bacheca.
     */
    public static void spostaToDo(ToDo todo, Bacheca nuova) {
        todo.setBacheca(nuova);
        todoDAO.aggiornaToDo(todo);
    }

    /**
     * Aggiunge una voce alla checklist di un ToDo.
     */
    public static void aggiungiCheck(ToDo todo, String voce) {
        Check_list nuova = new Check_list(voce, false);
        checklistDAO.aggiungiCheck(todo.getId(), nuova);
        todo.getChecklist().add(nuova);
    }

    /**
     * Modifica una voce della checklist.
     */
    public static void modificaCheck(Check_list voce, String nuovaVoce) {
        voce.setNome(nuovaVoce);
        checklistDAO.modificaCheck(voce);
    }

    /**
     * Segna una voce della checklist come completata.
     * Se tutte le voci sono completate, segna il ToDo come completato.
     */
    public static void completaCheck(Check_list voce) {
        voce.setStato(true);
        checklistDAO.completaCheck(voce.getId());

        ToDo todo = todoDAO.getById(voce.getTodoId());
        if (todo != null) {
            List<Check_list> tutte = checklistDAO.getCheckListPerToDo(todo.getId());
            boolean tutteCompletate = tutte.stream().allMatch(Check_list::isStato);
            if (tutteCompletate) {
                todo.Stato_todo = true;
                aggiornaToDo(todo);
            }
        }
    }

    /**
     * Elimina una voce dalla checklist.
     */
    public static void eliminaCheck(ToDo todo, Check_list voce) {
        checklistDAO.eliminaCheck(voce.getId());
        todo.getChecklist().remove(voce);
    }

    /**
     * Restituisce la checklist associata a un ToDo.
     */
    public static List<Check_list> getChecklistPerToDo(ToDo todo) {
        return checklistDAO.getCheckListPerToDo(todo.getId());
    }
}







