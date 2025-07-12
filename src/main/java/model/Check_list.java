package model;

import java.io.Serializable;

/**
 * Rappresenta una voce di checklist associata a un ToDo.
 * Ogni voce è composta da un nome, uno stato (completato o meno),
 * un identificativo univoco e il riferimento al ToDo associato.
 */
public class Check_list implements Serializable {
    private int id;
    private String nome;
    private boolean stato;
    private int todoId; // ✅ ID del ToDo a cui è collegato

    /**
     * Costruttore con nome e stato della checklist.
     *
     * @param nome  Il nome della voce della checklist.
     * @param stato Lo stato (true se completato, false altrimenti).
     */
    public Check_list(String nome, boolean stato) {
        this.nome = nome;
        this.stato = stato;
    }

    /**
     * Restituisce l'identificativo della voce checklist.
     *
     * @return ID della voce.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'identificativo della voce checklist.
     *
     * @param id ID da assegnare.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce il nome della voce della checklist.
     *
     * @return Nome della voce.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome della voce della checklist.
     *
     * @param nome Nome da assegnare.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce lo stato della voce della checklist.
     *
     * @return true se completata, false altrimenti.
     */
    public boolean isStato() {
        return stato;
    }

    /**
     * Imposta lo stato della voce della checklist.
     *
     * @param stato Stato da assegnare (true se completata).
     */
    public void setStato(boolean stato) {
        this.stato = stato;
    }

    /**
     * Restituisce l'ID del ToDo associato.
     *
     * @return ID del ToDo.
     */
    public int getTodoId() {
        return todoId;
    }

    /**
     * Imposta l'ID del ToDo associato.
     *
     * @param todoId ID del ToDo da associare.
     */
    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    /**
     * Restituisce una rappresentazione testuale della voce checklist.
     *
     * @return Una stringa con lo stato e il nome della voce.
     */
    @Override
    public String toString() {
        return (stato ? "✅ " : "🕓 ") + nome;
    }
}


