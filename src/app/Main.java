package app;

import model.Administrador;
import model.Aposta;
import model.Clube;
import model.Partida;
import model.Participante;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Administrador admin = new Administrador("Administrador");
        Participante participante1 = new Participante("Hugo");
        Participante participante2 = new Participante("Maria");

        Clube clube1 = new Clube("Brasil");
        Clube clube2 = new Clube("Argentina");

        Partida partida = new Partida(clube1, clube2, LocalDateTime.now().plusHours(5));

        Aposta aposta1 = new Aposta(participante1, partida, 2, 1);
        Aposta aposta2 = new Aposta(participante2, partida, 1, 1);

        System.out.println(admin.exibirResumo());
        System.out.println(participante1.exibirResumo());
        System.out.println(participante2.exibirResumo());
        System.out.println(clube1.exibirResumo());
        System.out.println(clube2.exibirResumo());
        System.out.println(partida.exibirResumo());

        System.out.println(aposta1.exibirResumo());
        System.out.println("Aposta 1 dentro do prazo? " + aposta1.apostaDentroDoPrazo());

        System.out.println(aposta2.exibirResumo());
        System.out.println("Aposta 2 dentro do prazo? " + aposta2.apostaDentroDoPrazo());

        partida.registrarResultado(2, 1);

        System.out.println("Resultado final da partida: " + partida.exibirResumo());
        System.out.println("Pontos de Hugo: " + aposta1.calcularPontos());
        System.out.println("Pontos de Maria: " + aposta2.calcularPontos());
    }
}