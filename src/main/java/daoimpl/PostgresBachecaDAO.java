package daoimpl;

import dao.BachecaDAO;
import database.ConnessioneDatabase;
import model.Bacheca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link BachecaDAO} per la gestione delle bacheche
 * utilizzando un database PostgreSQL.
 */
public class PostgresBachecaDAO implements BachecaDAO {

    /**
     * Crea una nuova bacheca per un determinato utente.
     *
     * @param username    l'username del proprietario
     * @param tipo        il tipo della bacheca
     * @param descrizione la descrizione opzionale
     * @return l'oggetto {@link Bacheca} creato con ID assegnato, oppure {@code null} in caso di errore
     */
    @Override
    public Bacheca creaBacheca(String username, Bacheca.tipoBacheca tipo, String descrizione) {
        String sql = "INSERT INTO bacheche (tipo, descrizione, username) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo.name());
            stmt.setString(2, descrizione);
            stmt.setString(3, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                Bacheca b = new Bacheca(tipo, username);
                b.setDescrizione(descrizione);
                b.setId(id);
                return b;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Restituisce tutte le bacheche associate a un determinato utente.
     *
     * @param username l'username dell'utente
     * @return lista di {@link Bacheca} appartenenti all'utente
     */
    @Override
    public List<Bacheca> getBachechePerUtente(String username) {
        List<Bacheca> lista = new ArrayList<>();
        String sql = "SELECT * FROM bacheche WHERE username = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bacheca.tipoBacheca tipo = Bacheca.tipoBacheca.valueOf(rs.getString("tipo"));
                String descrizione = rs.getString("descrizione");
                int id = rs.getInt("id");

                Bacheca b = new Bacheca(tipo, username);
                b.setDescrizione(descrizione);
                b.setId(id);
                lista.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Aggiorna la descrizione di una bacheca.
     *
     * @param id               ID della bacheca da aggiornare
     * @param nuovaDescrizione nuova descrizione da impostare
     */
    @Override
    public void aggiornaDescrizione(int id, String nuovaDescrizione) {
        String sql = "UPDATE bacheche SET descrizione = ? WHERE id = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuovaDescrizione);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una bacheca dato il suo ID.
     *
     * @param bachecaId ID della bacheca da eliminare
     */
    @Override
    public void eliminaBacheca(int bachecaId) {
        String sql = "DELETE FROM bacheche WHERE id = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bachecaId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restituisce una bacheca dato il suo ID.
     *
     * @param id ID della bacheca
     * @return l'oggetto {@link Bacheca} corrispondente, oppure {@code null} se non trovata
     */
    @Override
    public Bacheca getById(int id) {
        String sql = "SELECT * FROM bacheche WHERE id = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Bacheca.tipoBacheca tipo = Bacheca.tipoBacheca.valueOf(rs.getString("tipo"));
                String descrizione = rs.getString("descrizione");
                String username = rs.getString("username");

                Bacheca b = new Bacheca(tipo, username);
                b.setDescrizione(descrizione);
                b.setId(id);
                return b;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Restituisce una bacheca dato il suo ID (metodo duplicato di {@link #getById(int)}).
     *
     * @param id ID della bacheca
     * @return l'oggetto {@link Bacheca} corrispondente, oppure {@code null} se non trovata
     */
    @Override
    public Bacheca getBachecaById(int id) {
        // questo metodo è identico a getById ed è mantenuto per compatibilità
        return getById(id);
    }
}



