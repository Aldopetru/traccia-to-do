package daoimpl;

import dao.BachecaDAO;
import dao.ToDoDAO;
import database.ConnessioneDatabase;
import model.Bacheca;
import model.ToDo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link ToDoDAO} per gestire le operazioni CRUD
 * sui ToDo tramite database PostgreSQL.
 */
public class PostgresToDoDAO implements ToDoDAO {

    private final BachecaDAO bachecaDAO = new PostgresBachecaDAO();

    /**
     * Crea un nuovo ToDo nel database e imposta l'ID nel modello.
     *
     * @param todo l'oggetto {@link ToDo} da salvare
     */
    @Override
    public void creaToDo(ToDo todo) {
        String sql = "INSERT INTO todo (titolo, url, descrizione, data_scadenza, immagine, colore_sfondo, colore_titolo, stato, ordine, username, bacheca_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, todo.titolo_todo);
            stmt.setString(2, todo.URL);
            stmt.setString(3, todo.descrizione);
            stmt.setDate(4, Date.valueOf(todo.datascadenza));
            stmt.setString(5, todo.image);
            stmt.setString(6, todo.colore_sfondo);
            stmt.setString(7, todo.colore_titolo);
            stmt.setBoolean(8, todo.Stato_todo);
            stmt.setInt(9, todo.ordine);
            stmt.setString(10, todo.username);
            stmt.setInt(11, todo.getBacheca().getId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int todoId = rs.getInt("id");
                todo.setId(todoId);
                aggiungiUtentiCondivisi(todoId, todo.utenti_condivisi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera tutti i ToDo associati a una bacheca per un determinato utente,
     * considerando anche i ToDo condivisi.
     *
     * @param bacheca  la bacheca di riferimento
     * @param username l'utente
     * @return lista dei ToDo trovati
     */
    @Override
    public List<ToDo> getToDoPerBacheca(Bacheca bacheca, String username) {
        List<ToDo> lista = new ArrayList<>();
        String sql = """
        SELECT DISTINCT todo.*
        FROM todo
        LEFT JOIN todo_condivisi ON todo.id = todo_condivisi.todo_id
        WHERE todo.bacheca_id = ?
          AND (todo.username = ? OR todo_condivisi.username = ?)
        ORDER BY todo.ordine
        """;

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bacheca.getId());
            stmt.setString(2, username);
            stmt.setString(3, username);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ToDo todo = new ToDo(
                        rs.getString("titolo"),
                        rs.getString("url"),
                        rs.getString("descrizione"),
                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getString("immagine"),
                        rs.getString("colore_sfondo"),
                        rs.getString("colore_titolo"),
                        rs.getBoolean("stato"),
                        getUtentiCondivisi(rs.getInt("id")),
                        rs.getInt("ordine"),
                        rs.getString("username"),
                        bacheca
                );
                todo.setId(rs.getInt("id"));
                lista.add(todo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Elimina un ToDo dal database.
     *
     * @param id l'identificativo del ToDo da eliminare
     */
    @Override
    public void eliminaToDo(int id) {
        eliminaUtentiCondivisi(id);
        String sql = "DELETE FROM todo WHERE id = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aggiorna i dati di un ToDo esistente nel database.
     *
     * @param todo il ToDo aggiornato
     */
    @Override
    public void aggiornaToDo(ToDo todo) {
        eliminaUtentiCondivisi(todo.getId());
        aggiungiUtentiCondivisi(todo.getId(), todo.utenti_condivisi);

        String sql = "UPDATE todo SET titolo=?, url=?, descrizione=?, data_scadenza=?, immagine=?, colore_sfondo=?, colore_titolo=?, stato=?, ordine=?, bacheca_id=? WHERE id=?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, todo.titolo_todo);
            stmt.setString(2, todo.URL);
            stmt.setString(3, todo.descrizione);
            stmt.setDate(4, Date.valueOf(todo.datascadenza));
            stmt.setString(5, todo.image);
            stmt.setString(6, todo.colore_sfondo);
            stmt.setString(7, todo.colore_titolo);
            stmt.setBoolean(8, todo.Stato_todo);
            stmt.setInt(9, todo.ordine);
            stmt.setInt(10, todo.getBacheca().getId());
            stmt.setInt(11, todo.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera un ToDo in base al suo ID.
     *
     * @param id identificativo del ToDo
     * @return il ToDo trovato, oppure null
     */
    @Override
    public ToDo getById(int id) {
        String sql = "SELECT * FROM todo WHERE id = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idBacheca = rs.getInt("bacheca_id");
                Bacheca b = bachecaDAO.getById(idBacheca);

                ToDo todo = new ToDo(
                        rs.getString("titolo"),
                        rs.getString("url"),
                        rs.getString("descrizione"),
                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getString("immagine"),
                        rs.getString("colore_sfondo"),
                        rs.getString("colore_titolo"),
                        rs.getBoolean("stato"),
                        getUtentiCondivisi(id),
                        rs.getInt("ordine"),
                        rs.getString("username"),
                        b
                );
                todo.setId(id);
                return todo;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Recupera tutti i ToDo nel sistema.
     *
     * @return lista completa dei ToDo
     */
    @Override
    public List<ToDo> getTutti() {
        List<ToDo> lista = new ArrayList<>();
        String sql = "SELECT * FROM todo ORDER BY ordine";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idBacheca = rs.getInt("bacheca_id");
                Bacheca b = bachecaDAO.getById(idBacheca);

                ToDo todo = new ToDo(
                        rs.getString("titolo"),
                        rs.getString("url"),
                        rs.getString("descrizione"),
                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getString("immagine"),
                        rs.getString("colore_sfondo"),
                        rs.getString("colore_titolo"),
                        rs.getBoolean("stato"),
                        getUtentiCondivisi(rs.getInt("id")),
                        rs.getInt("ordine"),
                        rs.getString("username"),
                        b
                );
                todo.setId(rs.getInt("id"));
                lista.add(todo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Aggiunge utenti condivisi per un ToDo.
     *
     * @param todoId   l'identificativo del ToDo
     * @param usernames lista di username da aggiungere
     */
    @Override
    public void aggiungiUtentiCondivisi(int todoId, List<String> usernames) {
        String sql = "INSERT INTO todo_condivisi (todo_id, username) VALUES (?, ?)";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (String user : usernames) {
                if (user != null && !user.trim().isEmpty()) {
                    stmt.setInt(1, todoId);
                    stmt.setString(2, user.trim());
                    stmt.addBatch();
                }
            }
            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera gli utenti con cui è stato condiviso un ToDo.
     *
     * @param todoId l'identificativo del ToDo
     * @return lista di username
     */
    @Override
    public List<String> getUtentiCondivisi(int todoId) {
        List<String> utenti = new ArrayList<>();
        String sql = "SELECT username FROM todo_condivisi WHERE todo_id = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, todoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                utenti.add(rs.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utenti;
    }

    /**
     * Elimina tutti gli utenti condivisi da un ToDo.
     *
     * @param todoId l'identificativo del ToDo
     */
    @Override
    public void eliminaUtentiCondivisi(int todoId) {
        String sql = "DELETE FROM todo_condivisi WHERE todo_id = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, todoId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}





