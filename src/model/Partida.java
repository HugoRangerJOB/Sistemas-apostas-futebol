package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Partida {
    private Clube clubeCasa;
    private Clube clubeVisitante;
    private LocalDateTime dataHora;
    private Integer golsCasa;
    private Integer golsVisitante;

    public Partida(Clube clubeCasa, Clube clubeVisitante, LocalDateTime dataHora) {
        this.clubeCasa = clubeCasa;
        this.clubeVisitante = clubeVisitante;
        this.dataHora = dataHora;
        this.golsCasa = null;
        this.golsVisitante = null;
    }

    public Clube getClubeCasa() {
        return clubeCasa;
    }

    public Clube getClubeVisitante() {
        return clubeVisitante;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Integer getGolsCasa() {
        return golsCasa;
    }

    public Integer getGolsVisitante() {
        return golsVisitante;
    }

    public void registrarResultado(int golsCasa, int golsVisitante) {
        this.golsCasa = golsCasa;
        this.golsVisitante = golsVisitante;
    }

    public boolean resultadoRegistrado() {
        return golsCasa != null && golsVisitante != null;
    }

    public String getResultadoFinal() {
        if (!resultadoRegistrado()) {
            return "Resultado ainda não registrado";
        }

        if (golsCasa > golsVisitante) {
            return "CASA";
        } else if (golsVisitante > golsCasa) {
            return "VISITANTE";
        } else {
            return "EMPATE";
        }
    }

    public String exibirResumo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String resultado;
        if (resultadoRegistrado()) {
            resultado = golsCasa + " x " + golsVisitante;
        } else {
            resultado = "Sem resultado";
        }

        return clubeCasa.getNome() + " x " + clubeVisitante.getNome() +
                " | Data: " + dataHora.format(formatter) +
                " | Resultado: " + resultado;
    }
}