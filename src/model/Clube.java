package model;

public class Clube {
    private String nome;

    public Clube() {
        this.nome = "";
    }

    public Clube(String nome) {
        this.nome = nome;
    }

    public Clube(String nome, String cidade) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String exibirResumo() {
        return "Clube: " + nome;
    }

}