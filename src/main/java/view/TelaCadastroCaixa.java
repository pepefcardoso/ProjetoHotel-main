package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaCadastroCaixa extends JDialog {

    private JTextField jTextFieldId;
    private JTextField jTextFieldValorAbertura;
    private JTextField jTextFieldValorFechamento;
    private JTextField jTextFieldObs;
    private JFormattedTextField jFormattedTextFieldDataAbertura;
    private JFormattedTextField jFormattedTextFieldDataFechamento;
    private JComboBox<String> jComboBoxStatus;
    private JTextField jTextFieldFuncionario;
    private JButton jButtonRelacionarFuncionario;

    private JButton jButtonNovo;
    private JButton jButtonCancelar;
    private JButton jButtonGravar;
    private JButton jButtonBuscar;
    private JButton jButtonSair;
    private JButton jButtonAbrirCaixa;
    private JButton jButtonFecharCaixa;

    private JTable jTableMovimentos;
    private JTextField jTextFieldTotalEntradas;
    private JTextField jTextFieldTotalSaidas;
    private JTextField jTextFieldSaldo;

    private JPanel jPanelBotoes;
    private JPanel jPanelDados;
    private JTabbedPane jTabbedPane;

    public TelaCadastroCaixa(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        setTitle("Controle de Caixa");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(0, 4));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(255, 204, 153));
        panelTitulo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelTitulo.setPreferredSize(new Dimension(0, 50));
        JLabel lbl = new JLabel("Controle de Caixa");
        lbl.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        panelTitulo.add(lbl, BorderLayout.CENTER);
        add(panelTitulo, BorderLayout.NORTH);

        jTabbedPane = new JTabbedPane();
        jTabbedPane.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 4));
        jTabbedPane.addTab("Caixa", buildTabCaixa());
        jTabbedPane.addTab("Movimentações", buildTabMovimentos());
        add(jTabbedPane, BorderLayout.CENTER);

        add(buildBotoes(), BorderLayout.SOUTH);

        pack();
        setSize(820, 560);
        setLocationRelativeTo(null);
    }

    private JPanel buildTabCaixa() {
        jPanelDados = new JPanel(new GridBagLayout());
        jPanelDados.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 10, 7, 10);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        jPanelDados.add(new JLabel("ID"), g);
        g.gridx = 1; g.gridy = 0; g.weightx = 0.2;
        jTextFieldId = new JTextField(6);
        jTextFieldId.setEnabled(false);
        jPanelDados.add(jTextFieldId, g);

        g.gridx = 2; g.gridy = 0; g.weightx = 0;
        jPanelDados.add(new JLabel("Status"), g);
        g.gridx = 3; g.gridy = 0; g.weightx = 0.3;
        jComboBoxStatus = new JComboBox<>(new String[]{"Aberto", "Fechado"});
        jComboBoxStatus.setEnabled(false);
        jPanelDados.add(jComboBoxStatus, g);

        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        jPanelDados.add(new JLabel("Funcionário Resp."), g);
        g.gridx = 1; g.gridy = 1; g.weightx = 1.0; g.gridwidth = 2;
        jTextFieldFuncionario = new JTextField();
        jTextFieldFuncionario.setEditable(false);
        jPanelDados.add(jTextFieldFuncionario, g);
        g.gridwidth = 1;
        g.gridx = 3; g.gridy = 1; g.weightx = 0; g.fill = GridBagConstraints.NONE;
        jButtonRelacionarFuncionario = criarBotaoIcone("/images/Find.png", "Buscar Funcionário");
        jPanelDados.add(jButtonRelacionarFuncionario, g);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 2; g.weightx = 0;
        jPanelDados.add(new JLabel("Valor de Abertura (R$)"), g);
        g.gridx = 1; g.gridy = 2; g.weightx = 0.3;
        jTextFieldValorAbertura = new JTextField("0.00");
        jPanelDados.add(jTextFieldValorAbertura, g);

        g.gridx = 2; g.gridy = 2; g.weightx = 0;
        jPanelDados.add(new JLabel("Data/Hora Abertura"), g);
        g.gridx = 3; g.gridy = 2; g.weightx = 0.3;
        try {
            jFormattedTextFieldDataAbertura = new JFormattedTextField(
                    new javax.swing.text.MaskFormatter("##/##/#### ##:##"));
        } catch (Exception e) {
            jFormattedTextFieldDataAbertura = new JFormattedTextField();
        }
        jFormattedTextFieldDataAbertura.setEditable(false);
        jFormattedTextFieldDataAbertura.setHorizontalAlignment(JTextField.CENTER);
        jPanelDados.add(jFormattedTextFieldDataAbertura, g);

        g.gridx = 0; g.gridy = 3; g.weightx = 0;
        jPanelDados.add(new JLabel("Valor de Fechamento (R$)"), g);
        g.gridx = 1; g.gridy = 3; g.weightx = 0.3;
        jTextFieldValorFechamento = new JTextField("0.00");
        jTextFieldValorFechamento.setEditable(false);
        jTextFieldValorFechamento.setBackground(new Color(245, 245, 245));
        jPanelDados.add(jTextFieldValorFechamento, g);

        g.gridx = 2; g.gridy = 3; g.weightx = 0;
        jPanelDados.add(new JLabel("Data/Hora Fechamento"), g);
        g.gridx = 3; g.gridy = 3; g.weightx = 0.3;
        try {
            jFormattedTextFieldDataFechamento = new JFormattedTextField(
                    new javax.swing.text.MaskFormatter("##/##/#### ##:##"));
        } catch (Exception e) {
            jFormattedTextFieldDataFechamento = new JFormattedTextField();
        }
        jFormattedTextFieldDataFechamento.setEditable(false);
        jFormattedTextFieldDataFechamento.setBackground(new Color(245, 245, 245));
        jFormattedTextFieldDataFechamento.setHorizontalAlignment(JTextField.CENTER);
        jPanelDados.add(jFormattedTextFieldDataFechamento, g);

        g.gridx = 0; g.gridy = 4; g.weightx = 0;
        jPanelDados.add(new JLabel("Observação"), g);
        g.gridx = 1; g.gridy = 4; g.weightx = 1.0; g.gridwidth = 3;
        jTextFieldObs = new JTextField();
        jPanelDados.add(jTextFieldObs, g);
        g.gridwidth = 1;

        JPanel panelAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelAcoes.setBorder(BorderFactory.createTitledBorder("Ações do Caixa"));

        jButtonAbrirCaixa = new JButton("Abrir Caixa");
        jButtonAbrirCaixa.setFont(new Font("Arial", Font.BOLD, 13));
        jButtonAbrirCaixa.setBackground(new Color(76, 175, 80));
        jButtonAbrirCaixa.setForeground(Color.WHITE);
        jButtonAbrirCaixa.setOpaque(true);
        jButtonAbrirCaixa.setPreferredSize(new Dimension(140, 36));
        try { jButtonAbrirCaixa.setIcon(new ImageIcon(getClass().getResource("/images/OK.png"))); } catch (Exception ignored) {}

        jButtonFecharCaixa = new JButton("Fechar Caixa");
        jButtonFecharCaixa.setFont(new Font("Arial", Font.BOLD, 13));
        jButtonFecharCaixa.setBackground(new Color(211, 47, 47));
        jButtonFecharCaixa.setForeground(Color.WHITE);
        jButtonFecharCaixa.setOpaque(true);
        jButtonFecharCaixa.setPreferredSize(new Dimension(140, 36));
        jButtonFecharCaixa.setEnabled(false);
        try { jButtonFecharCaixa.setIcon(new ImageIcon(getClass().getResource("/images/Cancel.png"))); } catch (Exception ignored) {}

        panelAcoes.add(jButtonAbrirCaixa);
        panelAcoes.add(jButtonFecharCaixa);

        g.gridx = 0; g.gridy = 5; g.weightx = 1.0; g.gridwidth = 4;
        g.fill = GridBagConstraints.BOTH;
        jPanelDados.add(panelAcoes, g);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridwidth = 1;

        return jPanelDados;
    }

    private JPanel buildTabMovimentos() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTableMovimentos = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Data/Hora", "Descrição", "Valor (R$)", "Status"}
        ));
        jTableMovimentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableMovimentos.setDefaultEditor(Object.class, null);
        jTableMovimentos.getColumnModel().getColumn(0).setMaxWidth(50);
        jTableMovimentos.getColumnModel().getColumn(1).setMaxWidth(140);
        jTableMovimentos.getColumnModel().getColumn(3).setMaxWidth(100);
        jTableMovimentos.getColumnModel().getColumn(4).setMaxWidth(70);

        panel.add(new JScrollPane(jTableMovimentos), BorderLayout.CENTER);

        JPanel panelTotais = new JPanel(new GridBagLayout());
        panelTotais.setBorder(BorderFactory.createTitledBorder("Resumo do Caixa"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 8, 4, 8);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        panelTotais.add(new JLabel("Total Entradas (R$):"), g);
        g.gridx = 1; g.gridy = 0; g.weightx = 0.33;
        jTextFieldTotalEntradas = new JTextField("0.00");
        jTextFieldTotalEntradas.setEditable(false);
        jTextFieldTotalEntradas.setForeground(new Color(0, 100, 0));
        jTextFieldTotalEntradas.setFont(jTextFieldTotalEntradas.getFont().deriveFont(Font.BOLD));
        panelTotais.add(jTextFieldTotalEntradas, g);

        g.gridx = 2; g.gridy = 0; g.weightx = 0;
        panelTotais.add(new JLabel("Total Saídas (R$):"), g);
        g.gridx = 3; g.gridy = 0; g.weightx = 0.33;
        jTextFieldTotalSaidas = new JTextField("0.00");
        jTextFieldTotalSaidas.setEditable(false);
        jTextFieldTotalSaidas.setForeground(new Color(150, 0, 0));
        jTextFieldTotalSaidas.setFont(jTextFieldTotalSaidas.getFont().deriveFont(Font.BOLD));
        panelTotais.add(jTextFieldTotalSaidas, g);

        g.gridx = 4; g.gridy = 0; g.weightx = 0;
        panelTotais.add(new JLabel("Saldo (R$):"), g);
        g.gridx = 5; g.gridy = 0; g.weightx = 0.34;
        jTextFieldSaldo = new JTextField("0.00");
        jTextFieldSaldo.setEditable(false);
        jTextFieldSaldo.setFont(jTextFieldSaldo.getFont().deriveFont(Font.BOLD, 14f));
        jTextFieldSaldo.setBackground(new Color(230, 255, 230));
        panelTotais.add(jTextFieldSaldo, g);

        panel.add(panelTotais, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildBotoes() {
        jPanelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        jPanelBotoes.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonNovo     = criarBotao("Novo",     "/images/Create.png", "0");
        jButtonCancelar = criarBotao("Cancelar", "/images/Cancel.png", "1");
        jButtonGravar   = criarBotao("Gravar",   "/images/OK.png",     "1");
        jButtonBuscar   = criarBotao("Buscar",   "/images/Load.png",   "0");
        jButtonSair     = criarBotao("Sair",     "/images/Exit.png",   "0");

        jButtonCancelar.setEnabled(false);
        jButtonGravar.setEnabled(false);

        jPanelBotoes.add(jButtonNovo);
        jPanelBotoes.add(jButtonCancelar);
        jPanelBotoes.add(jButtonGravar);
        jPanelBotoes.add(jButtonBuscar);
        jPanelBotoes.add(jButtonSair);
        return jPanelBotoes;
    }

    private JButton criarBotao(String texto, String iconPath, String actionCommand) {
        JButton btn = new JButton(texto);
        btn.setActionCommand(actionCommand);
        try { btn.setIcon(new ImageIcon(getClass().getResource(iconPath))); } catch (Exception ignored) {}
        return btn;
    }

    private JButton criarBotaoIcone(String iconPath, String tooltip) {
        JButton btn = new JButton();
        btn.setToolTipText(tooltip);
        btn.setActionCommand("1");
        try { btn.setIcon(new ImageIcon(getClass().getResource(iconPath))); } catch (Exception e) { btn.setText("..."); }
        btn.setMargin(new Insets(1, 4, 1, 4));
        return btn;
    }

    public JTextField getjTextFieldId()              { return jTextFieldId; }
    public JTextField getjTextFieldValorAbertura()   { return jTextFieldValorAbertura; }
    public JTextField getjTextFieldValorFechamento() { return jTextFieldValorFechamento; }
    public JTextField getjTextFieldObs()             { return jTextFieldObs; }
    public JTextField getjTextFieldFuncionario()     { return jTextFieldFuncionario; }
    public JTextField getjTextFieldTotalEntradas()   { return jTextFieldTotalEntradas; }
    public JTextField getjTextFieldTotalSaidas()     { return jTextFieldTotalSaidas; }
    public JTextField getjTextFieldSaldo()           { return jTextFieldSaldo; }
    public JFormattedTextField getjFormattedTextFieldDataAbertura()   { return jFormattedTextFieldDataAbertura; }
    public JFormattedTextField getjFormattedTextFieldDataFechamento() { return jFormattedTextFieldDataFechamento; }
    public JComboBox<String> getjComboBoxStatus()    { return jComboBoxStatus; }
    public JTable getjTableMovimentos()              { return jTableMovimentos; }
    public JTabbedPane getjTabbedPane()              { return jTabbedPane; }
    public JButton getjButtonNovo()                  { return jButtonNovo; }
    public JButton getjButtonCancelar()              { return jButtonCancelar; }
    public JButton getjButtonGravar()                { return jButtonGravar; }
    public JButton getjButtonBuscar()                { return jButtonBuscar; }
    public JButton getjButtonSair()                  { return jButtonSair; }
    public JButton getjButtonAbrirCaixa()            { return jButtonAbrirCaixa; }
    public JButton getjButtonFecharCaixa()           { return jButtonFecharCaixa; }
    public JButton getjButtonRelacionarFuncionario() { return jButtonRelacionarFuncionario; }
    public JPanel getjPanelBotoes()                  { return jPanelBotoes; }
    public JPanel getjPanelDados()                   { return jPanelDados; }
}
