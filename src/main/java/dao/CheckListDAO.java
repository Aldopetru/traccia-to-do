package dao;

import model.Check_list;
import java.util.List;

/**
 * Interfaccia per l'accesso ai dati della checklist.
 */
public interface CheckListDAO {

    /**
     * Aggiunge una voce alla checklist di un ToDo.
     *
     * @param todoId l'ID del ToDo
     * @param check  la voce da aggiungere
     */
    void aggiungiCheck(int todoId, Check_list check);

    /**
     * Modifica il nome di una voce della checklist.
     *
     * @param check la voce da modificare
     */
    void modificaCheck(Check_list check);

    /**
     * Elimina una voce dalla checklist.
     *
     * @param checkId l'ID della voce da eliminare
     */
    void eliminaCheck(int checkId);

    /**
     * Restituisce l'elenco delle voci associate a un ToDo.
     *
     * @param todoId l'ID del ToDo
     * @return la lista delle voci della checklist
     */
    List<Check_list> getCheckListPerToDo(int todoId);

    /**
     * Segna come completata una voce della checklist.
     *
     * @param checkId l'ID della voce da completare
     */
    void completaCheck(int checkId);
}


