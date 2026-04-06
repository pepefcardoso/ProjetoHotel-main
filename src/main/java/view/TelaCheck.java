package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

/**
 * Tela de Check-in do Hotel. Abas: Check | Hóspedes | Quarto | Vaga/Veículo |
 * Recebimento
 */
public class TelaCheck extends JDialog {

    private JPanel jPanelTitulo;
    private JTabbedPane jTabbedPane1;
    private JPanel jPanelBotoes;

    private JPanel jPanelCheck;
    private JTextField jTextFieldId;
    private JComboBox<String> jComboBoxStatus;
    private JFormattedTextField jFormattedTextFieldDataCadastro;
    private JFormattedTextField jFormattedTextFieldDataEntrada;
    private JFormattedTextField jFormattedTextFieldDataSaida;
    private JFormattedTextField jFormattedTextFieldReserva;
    private JButton jButtonRelacionarReserva;
    private JTextField jTextFieldObs;

    private JPanel jPanelHospedes;
    private JFormattedTextField jFormattedTextFieldHospede;
    private JButton jButtonRelacionarHospede;
    private JComboBox<String> jComboBoxTipoHospede;
    private JTextField jTextFieldObsHospede;
    private JButton jButtonAlocarHospede;
    private JButton jButtonRemoverHospede;
    private JTable jTableHospedes;

    private JPanel jPanelQuartos;
    private JFormattedTextField jFormattedTextFieldQuarto;
    private JButton jButtonRelacionarQuarto;
    private JTextField jTextFieldObsQuarto;
    private JButton jButtonAlocarQuarto;
    private JButton jButtonRemoverQuarto;
    private JTable jTableQuartos;

    private JPanel jPanelVaga;
    private JFormattedTextField jFormattedTextFieldVeiculo;
    private JButton jButtonRelacionarVeiculo;
    private JFormattedTextField jFormattedTextFieldVaga;
    private JButton jButtonRelacionarVaga;
    private JTextField jTextFieldObsVaga;
    private JButton jButtonAlocarVaga;
    private JButton jButtonRemoverVaga;
    private JTable jTableAlocacoesVagas;

    private JPanel jPanelRecebimento;
    private JTextField jTextFieldValorOriginal;
    private JTextField jTextFieldDesconto;
    private JTextField jTextFieldAcrescimo;
    private JTextField jTextFieldValorProdutos;
    private JTextField jTextFieldValorPagar;
    private JTextField jTextFieldValorPago;
    private JTextField jTextFieldObsRecebimento;
    private JComboBox<String> jComboBoxStatusRecebimento;

    private JButton jButtonNovo;
    private JButton jButtonCancelar;
    private JButton jButtonGravar;
    private JButton jButtonBuscar;
    private JButton jButtonSair;

    public TelaCheck(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        setTitle("Check-in");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(0, 4));

        add(buildTitulo(), BorderLayout.NORTH);
        add(buildTabs(), BorderLayout.CENTER);
        add(buildBotoes(), BorderLayout.SOUTH);

        pack();
        setSize(820, 620);
        setLocationRelativeTo(null);
    }

    private JPanel buildTitulo() {
        jPanelTitulo = new JPanel(new BorderLayout());
        jPanelTitulo.setBackground(new Color(255, 204, 153));
        jPanelTitulo.setBorder(BorderFactory.createBevelBorder(
                javax.swing.border.BevelBorder.RAISED));
        jPanelTitulo.setPreferredSize(new Dimension(0, 50));

        JLabel lbl = new JLabel("Check-in");
        lbl.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        jPanelTitulo.add(lbl, BorderLayout.CENTER);
        return jPanelTitulo;
    }

    private JTabbedPane buildTabs() {
        jTabbedPane1 = new JTabbedPane();
        jTabbedPane1.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 4));

        jTabbedPane1.addTab("Check", buildTabCheck());
        jTabbedPane1.addTab("Hóspedes", buildTabHospedes());
        jTabbedPane1.addTab("Quarto", buildTabQuarto());
        jTabbedPane1.addTab("Vaga/Veículo", buildTabVaga());
        jTabbedPane1.addTab("Recebimento", buildTabRecebimento());
        
        return jTabbedPane1;
    }

    private JPanel buildTabCheck() {
        jPanelCheck = new JPanel(new GridBagLayout());
        jPanelCheck.setBorder(BorderFactory.createBevelBorder(
                javax.swing.border.BevelBorder.RAISED));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 8, 5, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0;
        g.gridy = 0;
        g.weightx = 0;
        jPanelCheck.add(new JLabel("ID"), g);

        g.gridx = 1;
        g.gridy = 0;
        g.weightx = 0.3;
        jTextFieldId = new JTextField(8);
        jTextFieldId.setEnabled(false);
        jPanelCheck.add(jTextFieldId, g);

        g.gridx = 2;
        g.gridy = 0;
        g.weightx = 0;
        jPanelCheck.add(new JLabel("Status"), g);

        g.gridx = 3;
        g.gridy = 0;
        g.weightx = 0.7;
        jComboBoxStatus = new JComboBox<>(new String[]{"Ativo", "Inativo"});
        jComboBoxStatus.setEnabled(false);
        jPanelCheck.add(jComboBoxStatus, g);

        g.gridx = 0;
        g.gridy = 1;
        g.weightx = 0;
        jPanelCheck.add(new JLabel("Data Cadastro"), g);

        g.gridx = 1;
        g.gridy = 1;
        g.weightx = 0.3;
        jFormattedTextFieldDataCadastro = criarCampoData();
        jFormattedTextFieldDataCadastro.setEditable(false);
        jPanelCheck.add(jFormattedTextFieldDataCadastro, g);

        g.gridx = 2;
        g.gridy = 1;
        g.weightx = 0;
        jPanelCheck.add(new JLabel("Data Entrada"), g);

        g.gridx = 3;
        g.gridy = 1;
        g.weightx = 0.7;
        jFormattedTextFieldDataEntrada = criarCampoData();
        jPanelCheck.add(jFormattedTextFieldDataEntrada, g);

        g.gridx = 0;
        g.gridy = 2;
        g.weightx = 0;
        jPanelCheck.add(new JLabel("Data Saída Prev."), g);

        g.gridx = 1;
        g.gridy = 2;
        g.weightx = 0.3;
        jFormattedTextFieldDataSaida = criarCampoData();
        jPanelCheck.add(jFormattedTextFieldDataSaida, g);

        g.gridx = 0;
        g.gridy = 3;
        g.weightx = 0;
        jPanelCheck.add(new JLabel("Reserva"), g);

        g.gridx = 1;
        g.gridy = 3;
        g.weightx = 0.3;
        jFormattedTextFieldReserva = new JFormattedTextField();
        jFormattedTextFieldReserva.setEnabled(false);
        jPanelCheck.add(jFormattedTextFieldReserva, g);

        g.gridx = 2;
        g.gridy = 3;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        jButtonRelacionarReserva = iconButton("/images/Find.png", "Buscar Reserva");
        jPanelCheck.add(jButtonRelacionarReserva, g);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0;
        g.gridy = 4;
        g.weightx = 0;
        jPanelCheck.add(new JLabel("Observação"), g);

        g.gridx = 1;
        g.gridy = 4;
        g.weightx = 1.0;
        g.gridwidth = 3;
        jTextFieldObs = new JTextField();
        jPanelCheck.add(jTextFieldObs, g);
        g.gridwidth = 1;

        return jPanelCheck;
    }

    private JPanel buildTabHospedes() {
        jPanelHospedes = new JPanel(new BorderLayout(4, 4));
        jPanelHospedes.setBorder(BorderFactory.createBevelBorder(
                javax.swing.border.BevelBorder.RAISED));

        JPanel top = new JPanel(new GridBagLayout());
        top.setBorder(BorderFactory.createTitledBorder("Selecionar Hóspede"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0;
        g.gridy = 0;
        g.weightx = 0;
        top.add(new JLabel("Hóspede"), g);

        g.gridx = 1;
        g.gridy = 0;
        g.weightx = 1.0;
        jFormattedTextFieldHospede = new JFormattedTextField();
        jFormattedTextFieldHospede.setEnabled(false);
        jFormattedTextFieldHospede.setPreferredSize(new Dimension(220, 25));
        top.add(jFormattedTextFieldHospede, g);

        g.gridx = 2;
        g.gridy = 0;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        jButtonRelacionarHospede = iconButton("/images/Find.png", "Buscar");
        top.add(jButtonRelacionarHospede, g);

        g.gridx = 0;
        g.gridy = 1;
        g.weightx = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        top.add(new JLabel("Tipo"), g);

        g.gridx = 1;
        g.gridy = 1;
        g.weightx = 0.4;
        jComboBoxTipoHospede = new JComboBox<>(
                new String[]{"Responsável", "Dependente", "Visitante"});
        top.add(jComboBoxTipoHospede, g);

        g.gridx = 2;
        g.gridy = 1;
        g.weightx = 0;
        top.add(new JLabel("Obs"), g);

        g.gridx = 3;
        g.gridy = 1;
        g.weightx = 0.6;
        jTextFieldObsHospede = new JTextField();
        top.add(jTextFieldObsHospede, g);

        g.gridx = 4;
        g.gridy = 1;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        jButtonAlocarHospede = new JButton("Alocar");
        try {
            jButtonAlocarHospede.setIcon(new ImageIcon(getClass().getResource("/images/OK.png")));
        } catch (Exception ignored) {
        }
        top.add(jButtonAlocarHospede, g);

        jTableHospedes = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"#", "Nome", "Tipo", "OBS", "Status"}));
        jTableHospedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableHospedes.getColumnModel().getColumn(0).setMaxWidth(40);
        jTableHospedes.getColumnModel().getColumn(4).setMaxWidth(60);
        jTableHospedes.setDefaultEditor(Object.class, null);

        JPanel bot = new JPanel(new BorderLayout(2, 2));
        bot.add(new JScrollPane(jTableHospedes), BorderLayout.CENTER);

        jButtonRemoverHospede = new JButton("Remover Selecionado");
        try {
            jButtonRemoverHospede.setIcon(new ImageIcon(getClass().getResource("/images/Cancel.png")));
        } catch (Exception ignored) {
        }
        JPanel remPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        remPanel.add(jButtonRemoverHospede);
        bot.add(remPanel, BorderLayout.SOUTH);

        jPanelHospedes.add(top, BorderLayout.NORTH);
        jPanelHospedes.add(bot, BorderLayout.CENTER);
        return jPanelHospedes;
    }

    private JPanel buildTabQuarto() {
        jPanelQuartos = new JPanel(new BorderLayout(4, 4));
        jPanelQuartos.setBorder(BorderFactory.createBevelBorder(
                javax.swing.border.BevelBorder.RAISED));

        JPanel top = new JPanel(new GridBagLayout());
        top.setBorder(BorderFactory.createTitledBorder("Selecionar Quarto"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0;
        g.gridy = 0;
        g.weightx = 0;
        top.add(new JLabel("Quarto"), g);

        g.gridx = 1;
        g.gridy = 0;
        g.weightx = 1.0;
        jFormattedTextFieldQuarto = new JFormattedTextField();
        jFormattedTextFieldQuarto.setEnabled(false);
        jFormattedTextFieldQuarto.setPreferredSize(new Dimension(220, 25));
        top.add(jFormattedTextFieldQuarto, g);

        g.gridx = 2;
        g.gridy = 0;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        jButtonRelacionarQuarto = iconButton("/images/Find.png", "Buscar");
        top.add(jButtonRelacionarQuarto, g);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0;
        g.gridy = 1;
        g.weightx = 0;
        top.add(new JLabel("OBS"), g);

        g.gridx = 1;
        g.gridy = 1;
        g.weightx = 0.8;
        jTextFieldObsQuarto = new JTextField();
        top.add(jTextFieldObsQuarto, g);

        g.gridx = 2;
        g.gridy = 1;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        jButtonAlocarQuarto = new JButton("Alocar");
        try {
            jButtonAlocarQuarto.setIcon(new ImageIcon(getClass().getResource("/images/OK.png")));
        } catch (Exception ignored) {
        }
        top.add(jButtonAlocarQuarto, g);

        jTableQuartos = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"#", "Identificação", "Descrição", "OBS", "Status"}));
        jTableQuartos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableQuartos.getColumnModel().getColumn(0).setMaxWidth(40);
        jTableQuartos.getColumnModel().getColumn(4).setMaxWidth(60);
        jTableQuartos.setDefaultEditor(Object.class, null);

        JPanel bot = new JPanel(new BorderLayout(2, 2));
        bot.add(new JScrollPane(jTableQuartos), BorderLayout.CENTER);

        jButtonRemoverQuarto = new JButton("Remover Selecionado");
        try {
            jButtonRemoverQuarto.setIcon(new ImageIcon(getClass().getResource("/images/Cancel.png")));
        } catch (Exception ignored) {
        }
        JPanel remPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        remPanel.add(jButtonRemoverQuarto);
        bot.add(remPanel, BorderLayout.SOUTH);

        jPanelQuartos.add(top, BorderLayout.NORTH);
        jPanelQuartos.add(bot, BorderLayout.CENTER);
        return jPanelQuartos;
    }

    private JPanel buildTabVaga() {
        jPanelVaga = new JPanel(new BorderLayout(4, 4));
        jPanelVaga.setBorder(BorderFactory.createBevelBorder(
                javax.swing.border.BevelBorder.RAISED));

        JPanel top = new JPanel(new GridBagLayout());
        top.setBorder(BorderFactory.createTitledBorder("Alocar Vaga / Veículo"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0;
        g.gridy = 0;
        g.weightx = 0;
        top.add(new JLabel("Veículo"), g);
        g.gridx = 1;
        g.gridy = 0;
        g.weightx = 1.0;
        jFormattedTextFieldVeiculo = new JFormattedTextField();
        jFormattedTextFieldVeiculo.setEnabled(false);
        top.add(jFormattedTextFieldVeiculo, g);
        g.gridx = 2;
        g.gridy = 0;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        jButtonRelacionarVeiculo = iconButton("/images/Find.png", "Buscar Veículo");
        top.add(jButtonRelacionarVeiculo, g);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0;
        g.gridy = 1;
        g.weightx = 0;
        top.add(new JLabel("Vaga"), g);
        g.gridx = 1;
        g.gridy = 1;
        g.weightx = 1.0;
        jFormattedTextFieldVaga = new JFormattedTextField();
        jFormattedTextFieldVaga.setEnabled(false);
        top.add(jFormattedTextFieldVaga, g);
        g.gridx = 2;
        g.gridy = 1;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        jButtonRelacionarVaga = iconButton("/images/Find.png", "Buscar Vaga");
        top.add(jButtonRelacionarVaga, g);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0;
        g.gridy = 2;
        g.weightx = 0;
        top.add(new JLabel("OBS"), g);
        g.gridx = 1;
        g.gridy = 2;
        g.weightx = 0.8;
        jTextFieldObsVaga = new JTextField();
        top.add(jTextFieldObsVaga, g);
        g.gridx = 2;
        g.gridy = 2;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        jButtonAlocarVaga = new JButton("Alocar");
        try {
            jButtonAlocarVaga.setIcon(new ImageIcon(getClass().getResource("/images/OK.png")));
        } catch (Exception ignored) {
        }
        top.add(jButtonAlocarVaga, g);

        jTableAlocacoesVagas = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"#", "Placa", "Vaga", "OBS", "Status"}));
        jTableAlocacoesVagas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableAlocacoesVagas.getColumnModel().getColumn(0).setMaxWidth(40);
        jTableAlocacoesVagas.getColumnModel().getColumn(4).setMaxWidth(60);
        jTableAlocacoesVagas.setDefaultEditor(Object.class, null);

        JPanel bot = new JPanel(new BorderLayout(2, 2));
        bot.add(new JScrollPane(jTableAlocacoesVagas), BorderLayout.CENTER);
        jButtonRemoverVaga = new JButton("Remover Selecionado");
        try {
            jButtonRemoverVaga.setIcon(new ImageIcon(getClass().getResource("/images/Cancel.png")));
        } catch (Exception ignored) {
        }
        JPanel remPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        remPanel.add(jButtonRemoverVaga);
        bot.add(remPanel, BorderLayout.SOUTH);

        jPanelVaga.add(top, BorderLayout.NORTH);
        jPanelVaga.add(bot, BorderLayout.CENTER);
        return jPanelVaga;
    }

    private JPanel buildTabRecebimento() {
        jPanelRecebimento = new JPanel(new GridBagLayout());
        jPanelRecebimento.setBorder(BorderFactory.createBevelBorder(
                javax.swing.border.BevelBorder.RAISED));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 8, 5, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Valor Original", "Desconto", "Acréscimo",
            "Valor Produtos (auto)", "Valor a Pagar (auto)", "Valor Pago"};
        int row = 0;
        for (String lbl : labels) {
            g.gridx = 0;
            g.gridy = row;
            g.weightx = 0;
            jPanelRecebimento.add(new JLabel(lbl), g);

            g.gridx = 1;
            g.gridy = row;
            g.weightx = 1.0;
            JTextField tf = new JTextField(10);
            switch (row) {
                case 0:
                    jTextFieldValorOriginal = tf;
                    break;
                case 1:
                    jTextFieldDesconto = tf;
                    break;
                case 2:
                    jTextFieldAcrescimo = tf;
                    break;
                case 3:
                    jTextFieldValorProdutos = tf;
                    tf.setEditable(false);
                    break;
                case 4:
                    jTextFieldValorPagar = tf;
                    tf.setEditable(false);
                    tf.setFont(tf.getFont().deriveFont(Font.BOLD));
                    tf.setForeground(new Color(0, 100, 0));
                    break;
                case 5:
                    jTextFieldValorPago = tf;
                    break;
            }
            jPanelRecebimento.add(tf, g);
            row++;
        }

        g.gridx = 0;
        g.gridy = row;
        g.weightx = 0;
        jPanelRecebimento.add(new JLabel("Status Recebimento"), g);
        g.gridx = 1;
        g.gridy = row;
        g.weightx = 1.0;
        jComboBoxStatusRecebimento = new JComboBox<>(new String[]{"Pendente", "Pago"});
        jPanelRecebimento.add(jComboBoxStatusRecebimento, g);
        row++;

        g.gridx = 0;
        g.gridy = row;
        g.weightx = 0;
        jPanelRecebimento.add(new JLabel("Observação"), g);
        g.gridx = 1;
        g.gridy = row;
        g.weightx = 1.0;
        jTextFieldObsRecebimento = new JTextField();
        jPanelRecebimento.add(jTextFieldObsRecebimento, g);

        return jPanelRecebimento;
    }

    private JPanel buildBotoes() {
        jPanelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        jPanelBotoes.setBorder(BorderFactory.createBevelBorder(
                javax.swing.border.BevelBorder.RAISED));

        jButtonNovo = criarBotao("Novo", "/images/Create.png");
        jButtonCancelar = criarBotao("Cancelar", "/images/Cancel.png");
        jButtonGravar = criarBotao("Gravar", "/images/OK.png");
        jButtonBuscar = criarBotao("Buscar", "/images/Load.png");
        jButtonSair = criarBotao("Sair", "/images/Exit.png");

        jButtonCancelar.setEnabled(false);
        jButtonGravar.setEnabled(false);

        jPanelBotoes.add(jButtonNovo);
        jPanelBotoes.add(jButtonCancelar);
        jPanelBotoes.add(jButtonGravar);
        jPanelBotoes.add(jButtonBuscar);
        jPanelBotoes.add(jButtonSair);
        return jPanelBotoes;
    }

    private JFormattedTextField criarCampoData() {
        try {
            MaskFormatter fmt = new MaskFormatter("##/##/####");
            fmt.setPlaceholderCharacter('_');
            JFormattedTextField ftf = new JFormattedTextField(fmt);
            ftf.setHorizontalAlignment(JTextField.CENTER);
            ftf.setPreferredSize(new Dimension(110, 25));
            return ftf;
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    private JButton iconButton(String iconPath, String tooltip) {
        JButton btn = new JButton();
        btn.setToolTipText(tooltip);
        try {
            btn.setIcon(new ImageIcon(getClass().getResource(iconPath)));
        } catch (Exception ignored) {
            btn.setText("...");
        }
        btn.setMargin(new Insets(1, 4, 1, 4));
        return btn;
    }

    private JButton criarBotao(String texto, String iconPath) {
        JButton btn = new JButton(texto);
        try {
            btn.setIcon(new ImageIcon(getClass().getResource(iconPath)));
        } catch (Exception ignored) {
        }
        return btn;
    }

    public JPanel getjPanelBotoes() {
        return jPanelBotoes;
    }

    public JPanel getjPanelCheck() {
        return jPanelCheck;
    }

    public JPanel getjPanelHospedes() {
        return jPanelHospedes;
    }

    public JPanel getjPanelQuartos() {
        return jPanelQuartos;
    }

    public JPanel getjPanelVaga() {
        return jPanelVaga;
    }

    public JPanel getjPanelRecebimento() {
        return jPanelRecebimento;
    }

    public JTabbedPane getjTabbedPane() {
        return jTabbedPane1;
    }

    public JButton getjButtonNovo() {
        return jButtonNovo;
    }

    public JButton getjButtonCancelar() {
        return jButtonCancelar;
    }

    public JButton getjButtonGravar() {
        return jButtonGravar;
    }

    public JButton getjButtonBuscar() {
        return jButtonBuscar;
    }

    public JButton getjButtonSair() {
        return jButtonSair;
    }

    public JTextField getjTextFieldId() {
        return jTextFieldId;
    }

    public JComboBox<String> getjComboBoxStatus() {
        return jComboBoxStatus;
    }

    public JFormattedTextField getjFormattedTextFieldDataCadastro() {
        return jFormattedTextFieldDataCadastro;
    }

    public JFormattedTextField getjFormattedTextFieldDataEntrada() {
        return jFormattedTextFieldDataEntrada;
    }

    public JFormattedTextField getjFormattedTextFieldDataSaida() {
        return jFormattedTextFieldDataSaida;
    }

    public JFormattedTextField getjFormattedTextFieldReserva() {
        return jFormattedTextFieldReserva;
    }

    public JButton getjButtonRelacionarReserva() {
        return jButtonRelacionarReserva;
    }

    public JTextField getjTextFieldObs() {
        return jTextFieldObs;
    }

    public JFormattedTextField getjFormattedTextFieldHospede() {
        return jFormattedTextFieldHospede;
    }

    public JButton getjButtonRelacionarHospede() {
        return jButtonRelacionarHospede;
    }

    public JComboBox<String> getjComboBoxTipoHospede() {
        return jComboBoxTipoHospede;
    }

    public JTextField getjTextFieldObsHospede() {
        return jTextFieldObsHospede;
    }

    public JButton getjButtonAlocarHospede() {
        return jButtonAlocarHospede;
    }

    public JButton getjButtonRemoverHospede() {
        return jButtonRemoverHospede;
    }

    public JTable getjTableHospedes() {
        return jTableHospedes;
    }

    public JFormattedTextField getjFormattedTextFieldQuarto() {
        return jFormattedTextFieldQuarto;
    }

    public JButton getjButtonRelacionarQuarto() {
        return jButtonRelacionarQuarto;
    }

    public JTextField getjTextFieldObsQuarto() {
        return jTextFieldObsQuarto;
    }

    public JButton getjButtonAlocarQuarto() {
        return jButtonAlocarQuarto;
    }

    public JButton getjButtonRemoverQuarto() {
        return jButtonRemoverQuarto;
    }

    public JTable getjTableQuartos() {
        return jTableQuartos;
    }

    public JFormattedTextField getjFormattedTextFieldVeiculo() {
        return jFormattedTextFieldVeiculo;
    }

    public JButton getjButtonRelacionarVeiculo() {
        return jButtonRelacionarVeiculo;
    }

    public JFormattedTextField getjFormattedTextFieldVaga() {
        return jFormattedTextFieldVaga;
    }

    public JButton getjButtonRelacionarVaga() {
        return jButtonRelacionarVaga;
    }

    public JTextField getjTextFieldObsVaga() {
        return jTextFieldObsVaga;
    }

    public JButton getjButtonAlocarVaga() {
        return jButtonAlocarVaga;
    }

    public JButton getjButtonRemoverVaga() {
        return jButtonRemoverVaga;
    }

    public JTable getjTableAlocacoesVagas() {
        return jTableAlocacoesVagas;
    }

    public JTextField getjTextFieldValorOriginal() {
        return jTextFieldValorOriginal;
    }

    public JTextField getjTextFieldDesconto() {
        return jTextFieldDesconto;
    }

    public JTextField getjTextFieldAcrescimo() {
        return jTextFieldAcrescimo;
    }

    public JTextField getjTextFieldValorProdutos() {
        return jTextFieldValorProdutos;
    }

    public JTextField getjTextFieldValorPagar() {
        return jTextFieldValorPagar;
    }

    public JTextField getjTextFieldValorPago() {
        return jTextFieldValorPago;
    }

    public JTextField getjTextFieldObsRecebimento() {
        return jTextFieldObsRecebimento;
    }

    public JComboBox<String> getjComboBoxStatusRecebimento() {
        return jComboBoxStatusRecebimento;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaCheck d = new TelaCheck(null, true);
            d.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            d.setVisible(true);
        });
    }
}
