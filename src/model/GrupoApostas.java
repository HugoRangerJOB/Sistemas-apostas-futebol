package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GrupoApostas {
    private String nome;
    private List<Participante> participantes;
    private List<Aposta> apostas;

    public GrupoApostas(String nome) {
        this.nome = nome;
        this.participantes = new ArrayList<>();
        this.apostas = new ArrayList<>();
    }

    public boolean adicionarParticipante(Participante p) {
        if (participantes.size() >= 5) {
            System.out.println("Limite de participantes atingido!");
            return false;
        }
        participantes.add(p);
        return true;
    }

    public void adicionarAposta(Aposta aposta) {
        if (!aposta.apostaDentroDoPrazo()) {
            System.out.println("Aposta fora do prazo!");
            return;
        }
        apostas.add(aposta);
    }

    public void calcularPontuacoes() {
        for (Participante participante : participantes) {
            // zera antes de recalcular
            participante.zerarPontuacao();
        }

        for (Aposta aposta : apostas) {
            int pontos = aposta.calcularPontos();
            aposta.getParticipante().adicionarPontos(pontos);
        }
    }

    public void exibirClassificacao() {
        participantes.sort(Comparator.comparingInt(Participante::getPontuacaoTotal).reversed());

        System.out.println("\n=== CLASSIFICAÇÃO ===");
        for (Participante p : participantes) {
            System.out.println(p.getNome() + " - " + p.getPontuacaoTotal() + " pontos");
        }
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public List<Participante> getParticipantesOrdenados() {
        List<Participante> copia = new ArrayList<>(participantes);
        copia.sort(Comparator.comparingInt(Participante::getPontuacaoTotal).reversed());
        return copia;
    }

    public String getNome() {
        return nome;
    }
}