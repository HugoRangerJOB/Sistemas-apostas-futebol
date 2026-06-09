package model;

import java.util.ArrayList;
import java.util.List;

public class Campeonato {

    private String nome;
    private List<Clube> clubes;
    private List<Partida> partidas;

    public Campeonato() {
        this.nome = "";
        this.clubes = new ArrayList<>();
        this.partidas = new ArrayList<>();
    }

    public Campeonato(String nome) {
        this.nome = nome;
        this.clubes = new ArrayList<>();
        this.partidas = new ArrayList<>();
    }

    public boolean adicionarClube(Clube clube) {

        if (clubes.size() >= 8) {
            return false;
        }

        clubes.add(clube);
        return true;
    }

    public void adicionarPartida(Partida partida) {
        partidas.add(partida);
    }

    public String getNome() {
        return nome;
    }

    public List<Clube> getClubes() {
        return clubes;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }
}