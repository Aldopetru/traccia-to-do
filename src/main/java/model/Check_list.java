package model;

import java.io.Serializable;

public class Check_list implements Serializable {
    public String nome;
    public boolean stato;

    public Check_list(String nome, boolean stato) {
        this.nome = nome;
        this.stato = stato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return (stato ? "✅ " : "🕓 ") + nome;
    }
}
