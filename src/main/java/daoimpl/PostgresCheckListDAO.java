package daoimpl;

import dao.CheckListDAO;
import database.ConnessioneDatabase;
import model.Check_list;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link CheckListDAO} per la gestione della checklist
 * associata ai ToDo, utilizzando un database PostgreSQL.
 */
public class PostgresCheckListDAO implements CheckListDAO {

    /**
     * Aggiunge una nuova voce di checklist associata a un ToDo.
     *
     * @param todoId ID del ToDo a cui la voce è associata
     * @param check  oggetto {@link Check_list} da aggiungere
     */
    @Override
    public void aggiungiCheck(int todoId, Check_list check) {
        String sql = "INSERT INTO checklist (nome, stato, todo_id) VALUES (?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, check.getNome());
            stmt.setBoolean(2, check.isStato());
            stmt.setInt(3, todoId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica il nome di una voce della checklist.
     *
     * @param check voce della checklist con ID e nuovo nome
     */
    @Override
    public void modificaCheck(Check_list check) {
        String sql = "UPDATE checklist SET nome = ? WHERE id = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, check.getNome());
            stmt.setInt(2, check.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una voce dalla checklist.
     *
     * @param checkId ID della voce da eliminare
     */
    @Override
    public void eliminaCheck(int checkId) {
        String sql = "DELETE FROM checklist WHERE id = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, checkId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restituisce tutte le voci della checklist associate a un determinato ToDo.
     *
     * @param todoId ID del ToDo
     * @return lista di oggetti {@link Check_list}
     */
    @Override
    public List<Check_list> getCheckListPerToDo(int todoId) {
        List<Check_list> lista = new ArrayList<>();
        String sql = "SELECT * FROM checklist WHERE todo_id = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, todoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Check_list c = new Check_list(
                        rs.getString("nome"),
                        rs.getBoolean("stato")
                );
                c.setId(rs.getInt("id"));
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Segna una voce della checklist come completata.
     *
     * @param checkId ID della voce da completare
     */
    @Override
    public void completaCheck(int checkId) {
        String sql = "UPDATE checklist SET stato = true WHERE id = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, checkId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


