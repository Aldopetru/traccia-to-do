package model;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe di test per verificare la creazione e l'utilizzo degli oggetti principali del sistema:
 * {@link Utente}, {@link Bacheca}, {@link Check_list}, {@link ToDo}.
 */
public class Main {

    /**
     * Metodo principale per l'esecuzione del programma di prova.
     * Crea un utente, una bacheca, una checklist e un ToDo, stampando poi i relativi dati.
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        Utente utente = new Utente("AndreaStarace27", "Abcd2004");

        Bacheca bacheca = new Bacheca(Bacheca.tipoBacheca.Universita, "Triennale in Informatica");

        Check_list check_list = new Check_list("Scrivere Main", true);

        ToDo todo = new ToDo(
                "Titolo",
                "url",
                "descrizione",
                LocalDate.now(),
                "immagine",
                "#fff",
                "#000",
                false,
                List.of("demo"),
                1,
                "admin",
                bacheca
        );

        System.out.println(utente.username);
        System.out.println(bacheca.titolo_b + " - " + bacheca.descrizione);
        System.out.println(check_list.getNome() + " - Stato: " + check_list.isStato());
        System.out.println(todo.titolo_todo + " - " + todo.descrizione + " - " + todo.datascadenza + " - " + todo.utenti_condivisi);
    }
}

