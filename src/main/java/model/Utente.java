package model;

/**
 * Classe che rappresenta un utente del sistema.
 * Contiene il nome utente e la password.
 */
public class Utente {

    /** Nome utente univoco dell'utente */
    public final String username;

    /** Password dell'utente (non viene mostrata o modificata direttamente) */
    private String password;

    /**
     * Costruttore per creare un nuovo utente.
     *
     * @param username nome utente
     * @param password password associata all'utente
     */
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Restituisce la password dell'utente.
     * ⚠️ Nota: in un sistema reale, questo metodo andrebbe evitato o gestito in modo sicuro.
     *
     * @return password dell'utente
     */
    public String getPassword() {
        return password;
    }
}



