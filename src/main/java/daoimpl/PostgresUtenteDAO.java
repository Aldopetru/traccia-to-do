package daoimpl;

import dao.UtenteDAO;
import database.ConnessioneDatabase;
import model.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link UtenteDAO} per l'accesso ai dati degli utenti
 * utilizzando un database PostgreSQL.
 */
public class PostgresUtenteDAO implements UtenteDAO {

    /**
     * Crea un nuovo utente nel database.
     *
     * @param utente l'oggetto {@link Utente} da salvare
     */
    @Override
    public void creaUtente(Utente utente) {
        String sql = "INSERT INTO utenti(username, password) VALUES (?, ?)";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utente.username);
            stmt.setString(2, utente.getPassword());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica se esiste un utente con lo username e la password forniti.
     *
     * @param username lo username da cercare
     * @param password la password corrispondente
     * @return true se l'utente esiste, false altrimenti
     */
    @Override
    public boolean verificaUtente(String username, String password) {
        String sql = "SELECT * FROM utenti WHERE username = ? AND password = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Recupera un utente dal database a partire dallo username.
     *
     * @param username lo username dell'utente da cercare
     * @return un oggetto {@link Utente} se trovato, altrimenti null
     */
    @Override
    public Utente getUtenteByUsername(String username) {
        String sql = "SELECT * FROM utenti WHERE username = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Utente(rs.getString("username"), rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Restituisce una lista di tutti gli utenti presenti nel database.
     *
     * @return lista di oggetti {@link Utente}
     */
    @Override
    public List<Utente> getTuttiUtenti() {
        List<Utente> lista = new ArrayList<>();
        String sql = "SELECT * FROM utenti";

        try (Connection conn = ConnessioneDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Utente(rs.getString("username"), rs.getString("password")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}

