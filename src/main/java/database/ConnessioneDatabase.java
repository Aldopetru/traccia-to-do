package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe di utilità per la gestione della connessione al database PostgreSQL.
 */
public class ConnessioneDatabase {

    /** URL di connessione al database PostgreSQL */
    private static final String URL = "jdbc:postgresql://localhost:5432/todolist_db";

    /** Nome utente del database */
    private static final String USER = "postgres"; // Sostituisci con il tuo username se diverso

    /** Password del database */
    private static final String PASSWORD = "Aldo2005"; // ⚠️ Metti qui la TUA password

    /**
     * Restituisce una connessione attiva al database PostgreSQL.
     *
     * @return un oggetto {@link Connection} valido
     * @throws RuntimeException se la connessione fallisce
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}


