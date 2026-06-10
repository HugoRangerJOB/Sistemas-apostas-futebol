package view;

import model.Aposta;
import model.Campeonato;
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
    private Campeonato campeonato;
    private List<Clube> clubes;
    private List<Partida> partidas;
    private JTextArea areaTexto;

    public TelaPrincipal() {
        grupo = new GrupoApostas("Grupo Copa");

        campeonato = new Campeonato("Brasileirão");

        clubes = campeonato.getClubes();
        partidas = campeonato.getPartidas();

        setTitle("Sistema de Apostas do Campeonato");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(
                new Color(34, 40, 49)
        );

        JLabel titulo = new JLabel("SISTEMA DE APOSTAS DO CAMPEONATO", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        titulo.setOpaque(true);

        titulo.setBackground(
                new Color(34, 40, 49)
        );

        add(titulo, BorderLayout.NORTH);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setBackground(
                new Color(57, 62, 70)
        );

        areaTexto.setForeground(
                Color.WHITE
        );

        areaTexto.setCaretColor(
                Color.WHITE
        );

        areaTexto.setFont(
                new Font("Consolas", Font.PLAIN, 14)
        );

        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(
                new Color(34, 40, 49)
        );
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

        JButton[] botoes = {
                btnCadastrarParticipante,
                btnCadastrarClube,
                btnCriarPartida,
                btnRegistrarAposta,
                btnRegistrarResultado,
                btnVerClassificacao
        };

        for (JButton botao : botoes) {

            botao.setFont(
                    new Font("Segoe UI", Font.BOLD, 14)
            );

            botao.setFocusPainted(false);

            botao.setBackground(
                    new Color(57, 62, 70)
            );

            botao.setForeground(Color.WHITE);

            botao.setBorder(
                    BorderFactory.createLineBorder(
                            new Color(0, 173, 181),
                            2
                    )
            );
        }

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
            boolean adicionado = campeonato.adicionarClube(clube);

            if (adicionado) {
                areaTexto.append("Clube cadastrado: " + nome + "\n");
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Limite máximo de 8 clubes atingido."
                );
            }
        }
    }

    private void criarPartida() {
        if (clubes.size() < 2) {
            JOptionPane.showMessageDialog(this, "Cadastre pelo menos 2 clubes primeiro.");
            return;
        }

        String nomeCasa = JOptionPane.showInputDialog(this, "Nome do clube da casa:");
        String nomeVisitante = JOptionPane.showInputDialog(this, "Nome do clube visitante:");

        areaTexto.append("\nClubes cadastrados:\n");

        for (Clube clube : clubes) {
            areaTexto.append("- " + clube.getNome() + "\n");
        }
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