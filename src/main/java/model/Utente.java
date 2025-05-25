
package model;

public class Utente {
    public String username;
    private String password;

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}


