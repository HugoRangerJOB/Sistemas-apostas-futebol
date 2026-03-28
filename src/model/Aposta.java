package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Aposta {
    private Participante participante;
    private Partida partida;
    private int golsPrevistosCasa;
    private int golsPrevistosVisitante;
    private LocalDateTime dataHoraAposta;

    public Aposta(Participante participante, Partida partida, int golsPrevistosCasa, int golsPrevistosVisitante) {
        this.participante = participante;
        this.partida = partida;
        this.golsPrevistosCasa = golsPrevistosCasa;
        this.golsPrevistosVisitante = golsPrevistosVisitante;
        this.dataHoraAposta = LocalDateTime.now();
    }

    public Participante getParticipante() {
        return participante;
    }

    public Partida getPartida() {
        return partida;
    }

    public int getGolsPrevistosCasa() {
        return golsPrevistosCasa;
    }

    public int getGolsPrevistosVisitante() {
        return golsPrevistosVisitante;
    }

    public LocalDateTime getDataHoraAposta() {
        return dataHoraAposta;
    }

    public boolean apostaDentroDoPrazo() {
        Duration diferenca = Duration.between(dataHoraAposta, partida.getDataHora());
        return diferenca.toMinutes() >= 20;
    }

    public String getResultadoPrevisto() {
        if (golsPrevistosCasa > golsPrevistosVisitante) {
            return "CASA";
        } else if (golsPrevistosVisitante > golsPrevistosCasa) {
            return "VISITANTE";
        } else {
            return "EMPATE";
        }
    }

    public int calcularPontos() {
        if (!partida.resultadoRegistrado()) {
            return 0;
        }

        boolean acertouResultado = getResultadoPrevisto().equals(partida.getResultadoFinal());
        boolean acertouPlacarExato = golsPrevistosCasa == partida.getGolsCasa()
                && golsPrevistosVisitante == partida.getGolsVisitante();

        if (acertouPlacarExato) {
            return 10;
        } else if (acertouResultado) {
            return 5;
        } else {
            return 0;
        }
    }

    public String exibirResumo() {
        return "Participante: " + participante.getNome() +
                " | Partida: " + partida.getClubeCasa().getNome() + " x " + partida.getClubeVisitante().getNome() +
                " | Palpite: " + golsPrevistosCasa + " x " + golsPrevistosVisitante;
    }
}