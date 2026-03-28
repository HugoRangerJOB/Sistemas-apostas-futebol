package model;

public class Administrador extends Usuario {

    public Administrador(String nome) {
        super(nome);
    }

    @Override
    public String exibirResumo() {
        return "Administrador: " + getNome();
    }
}