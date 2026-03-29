package view;

import model.Aposta;
import model.Clube;
import model.GrupoApostas;
import model.Partida;
import model.Participante;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private GrupoApostas grupo;
    private List<Clube> clubes;
    private List<Partida> partidas;
    private JTextArea areaTexto;

    public TelaPrincipal() {
        grupo = new GrupoApostas("Grupo Copa");
        clubes = new ArrayList<>();
        partidas = new ArrayList<>();

        setTitle("Sistema de Apostas - Campeonato de Futebol");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Sistema de Apostas Futebol", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(2, 3, 10, 10));

        JButton btnCadastrarParticipante = new JButton("Cadastrar Participante");
        JButton btnCadastrarClube = new JButton("Cadastrar Clube");
        JButton btnCriarPartida = new JButton("Criar Partida");
        JButton btnRegistrarAposta = new JButton("Registrar Aposta");
        JButton btnRegistrarResultado = new JButton("Registrar Resultado");
        JButton btnVerClassificacao = new JButton("Ver Classificação");

        painelBotoes.add(btnCadastrarParticipante);
        painelBotoes.add(btnCadastrarClube);
        painelBotoes.add(btnCriarPartida);
        painelBotoes.add(btnRegistrarAposta);
        painelBotoes.add(btnRegistrarResultado);
        painelBotoes.add(btnVerClassificacao);

        add(painelBotoes, BorderLayout.SOUTH);

        btnCadastrarParticipante.addActionListener(e -> cadastrarParticipante());
        btnCadastrarClube.addActionListener(e -> cadastrarClube());
        btnCriarPartida.addActionListener(e -> criarPartida());
        btnRegistrarAposta.addActionListener(e -> registrarAposta());
        btnRegistrarResultado.addActionListener(e -> registrarResultado());
        btnVerClassificacao.addActionListener(e -> verClassificacao());
    }

    private void cadastrarParticipante() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do participante:");
        if (nome != null && !nome.trim().isEmpty()) {
            Participante participante = new Participante(nome);
            boolean adicionado = grupo.adicionarParticipante(participante);

            if (adicionado) {
                areaTexto.append("Participante cadastrado: " + nome + "\n");
            } else {
                areaTexto.append("Não foi possível cadastrar participante.\n");
            }
        }
    }

    private void cadastrarClube() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do clube:");
        if (nome != null && !nome.trim().isEmpty()) {
            Clube clube = new Clube(nome);
            clubes.add(clube);
            areaTexto.append("Clube cadastrado: " + nome + "\n");
        }
    }

    private void criarPartida() {
        if (clubes.size() < 2) {
            JOptionPane.showMessageDialog(this, "Cadastre pelo menos 2 clubes primeiro.");
            return;
        }

        String nomeCasa = JOptionPane.showInputDialog(this, "Nome do clube da casa:");
        String nomeVisitante = JOptionPane.showInputDialog(this, "Nome do clube visitante:");

        Clube clubeCasa = buscarClubePorNome(nomeCasa);
        Clube clubeVisitante = buscarClubePorNome(nomeVisitante);

        if (clubeCasa == null || clubeVisitante == null) {
            JOptionPane.showMessageDialog(this, "Um ou ambos os clubes não foram encontrados.");
            return;
        }

        Partida partida = new Partida(clubeCasa, clubeVisitante, LocalDateTime.now().plusHours(2));
        partidas.add(partida);

        areaTexto.append("Partida criada: " + partida.exibirResumo() + "\n");
    }

    private void registrarAposta() {
        if (partidas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma partida cadastrada.");
            return;
        }

        String nomeParticipante = JOptionPane.showInputDialog(this, "Nome do participante:");
        Participante participante = buscarParticipantePorNome(nomeParticipante);

        if (participante == null) {
            JOptionPane.showMessageDialog(this, "Participante não encontrado.");
            return;
        }

        Partida partida = partidas.get(0);

        String golsCasaStr = JOptionPane.showInputDialog(this, "Gols previstos para o time da casa:");
        String golsVisitanteStr = JOptionPane.showInputDialog(this, "Gols previstos para o time visitante:");

        try {
            int golsCasa = Integer.parseInt(golsCasaStr);
            int golsVisitante = Integer.parseInt(golsVisitanteStr);

            Aposta aposta = new Aposta(participante, partida, golsCasa, golsVisitante);
            grupo.adicionarAposta(aposta);

            areaTexto.append("Aposta registrada: " + aposta.exibirResumo() + "\n");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite valores numéricos válidos.");
        }
    }

    private void registrarResultado() {
        if (partidas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma partida cadastrada.");
            return;
        }

        Partida partida = partidas.get(0);

        String golsCasaStr = JOptionPane.showInputDialog(this, "Gols reais do time da casa:");
        String golsVisitanteStr = JOptionPane.showInputDialog(this, "Gols reais do time visitante:");

        try {
            int golsCasa = Integer.parseInt(golsCasaStr);
            int golsVisitante = Integer.parseInt(golsVisitanteStr);

            partida.registrarResultado(golsCasa, golsVisitante);
            grupo.calcularPontuacoes();

            areaTexto.append("Resultado registrado: " + partida.exibirResumo() + "\n");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite valores numéricos válidos.");
        }
    }

    private void verClassificacao() {
        areaTexto.append("\n=== CLASSIFICAÇÃO ===\n");
        for (Participante p : grupo.getParticipantesOrdenados()) {
            areaTexto.append(p.getNome() + " - " + p.getPontuacaoTotal() + " pontos\n");
        }
        areaTexto.append("\n");
    }

    private Clube buscarClubePorNome(String nome) {
        for (Clube clube : clubes) {
            if (clube.getNome().equalsIgnoreCase(nome)) {
                return clube;
            }
        }
        return null;
    }

    private Participante buscarParticipantePorNome(String nome) {
        for (Participante participante : grupo.getParticipantes()) {
            if (participante.getNome().equalsIgnoreCase(nome)) {
                return participante;
            }
        }
        return null;
    }
}