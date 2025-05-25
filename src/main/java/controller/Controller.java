package controller;

import model.Bacheca;
import model.Check_list;
import model.ToDo;
import model.Utente;

import java.time.LocalDate;
import java.util.*;

public class Controller {

    private static final List<Utente> utenti = new ArrayList<>();

    public static void creaUtente(String username, String password) {
        utenti.add(new Utente(username, password));
    }

    public static boolean verificaUtente(String username, String password) {
        return utenti.stream()
                .anyMatch(u -> u.username.equals(username) && u.getPassword().equals(password));
    }

    public static List<Utente> getUtenti() {
        return utenti;
    }

    private static final Map<String, List<Bacheca>> mappaUtentiBacheche = new HashMap<>();

    public static Bacheca creaBacheca(String username, Bacheca.tipoBacheca tipo, String descrizione) {
        List<Bacheca> bachecheUtente = mappaUtentiBacheche.computeIfAbsent(username, k -> new ArrayList<>());

        for (Bacheca b : bachecheUtente) {
            if (b.titolo_b == tipo) {
                throw new IllegalArgumentException("Hai già una bacheca di tipo " + tipo.name());
            }
        }

        Bacheca nuova = new Bacheca(tipo, descrizione);
        bachecheUtente.add(nuova);
        return nuova;
    }

    public static List<Bacheca> getBachechePerUtente(String username) {
        return mappaUtentiBacheche.getOrDefault(username, new ArrayList<>());
    }

    public static void eliminaBacheca(String username, Bacheca bacheca) {
        List<Bacheca> bachecheUtente = mappaUtentiBacheche.get(username);
        if (bachecheUtente != null) {
            bachecheUtente.remove(bacheca);
        }
    }

    private static final List<ToDo> listaToDo = new ArrayList<>();

    public static void creaToDo(String titolo, String url, String descrizione, LocalDate dataScadenza,
                                String immagine, String coloreSfondo, String coloreTitolo, boolean stato,
                                List<String> condivisi, int ordine, String username, Bacheca bacheca) {

        ToDo todo = new ToDo(titolo, url, descrizione, dataScadenza, immagine, coloreSfondo,
                coloreTitolo, stato, condivisi, ordine, username, bacheca);

        listaToDo.add(todo);
    }

    public static List<ToDo> getListaToDo() {
        return listaToDo;
    }

    public static void eliminaToDo(ToDo todo) {
        listaToDo.remove(todo);
    }

    public static List<ToDo> getToDoPerBacheca(Bacheca bacheca) {
        List<ToDo> result = new ArrayList<>();
        for (ToDo t : listaToDo) {
            if (t.getBacheca().equals(bacheca)) {
                result.add(t);
            }
        }
        return result;
    }

    public static void aggiungiCheck(ToDo todo, String voce) {
        todo.getChecklist().add(new Check_list(voce, false));
    }

    public static void modificaCheck(Check_list voce, String nuovaVoce) {
        voce.setNome(nuovaVoce);
    }

    public static void completaCheck(Check_list voce) {
        voce.setStato(true);
    }

    public static void eliminaCheck(ToDo todo, Check_list voce) {
        todo.getChecklist().remove(voce);
    }
}

