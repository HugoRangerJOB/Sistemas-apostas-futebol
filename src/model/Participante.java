package model;

import interfaces.Classificavel;

public class Participante extends Usuario implements Classificavel {
    private int pontuacaoTotal;

    public Participante(String nome) {
        super(nome);
        this.pontuacaoTotal = 0;
    }

    public void adicionarPontos(int pontos) {
        this.pontuacaoTotal += pontos;
    }

    @Override
    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    @Override
    public String exibirResumo() {
        return "Participante: " + getNome() + " | Pontos: " + pontuacaoTotal;
    }
}