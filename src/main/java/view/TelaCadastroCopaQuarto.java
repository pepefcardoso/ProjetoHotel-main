package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class TelaCadastroCopaQuartoNova extends JDialog {

    private JTextField jTextFieldId;
    private JTextField jTextFieldObservacao;
    private JTextField jTextFieldQuantidade;
    private JFormattedTextField jFormattedTextFieldQuarto;
    private JFormattedTextField jFormattedTextFieldProduto;
    private JFormattedTextField jFormattedTextFieldCadastro;
    private JComboBox<String> jComboBoxStatus;

    private JButton jButtonNovo;
    private JButton jButtonCancelar;
    private JButton jButtonGravar;
    private JButton jButtonBuscar;
    private JButton jButtonSair;
    private JButton jButtonRelacionarQuarto;
    private JButton jButtonRelacionarProduto;

    private JPanel jPanelBotoes;
    private JPanel jPanelDados;

    public TelaCadastroCopaQuartoNova(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        setTitle("Copa do Quarto – Pedidos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(0, 4));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(255, 204, 153));
        panelTitulo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelTitulo.setPreferredSize(new Dimension(0, 50));
        JLabel lbl = new JLabel("Copa do Quarto");
        lbl.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        panelTitulo.add(lbl, BorderLayout.CENTER);
        add(panelTitulo, BorderLayout.NORTH);

        jPanelDados = new JPanel(new GridBagLayout());
        jPanelDados.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 10, 6, 10);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        jPanelDados.add(new JLabel("ID"), g);
        g.gridx = 1; g.gridy = 0; g.weightx = 0.3;
        jTextFieldId = new JTextField(6);
        jTextFieldId.setEnabled(false);
        jPanelDados.add(jTextFieldId, g);

        g.gridx = 2; g.gridy = 0; g.weightx = 0;
        jPanelDados.add(new JLabel("Status"), g);
        g.gridx = 3; g.gridy = 0; g.weightx = 0.3;
        jComboBoxStatus = new JComboBox<>(new String[]{"Ativo", "Inativo"});
        jComboBoxStatus.setEnabled(false);
        jPanelDados.add(jComboBoxStatus, g);

        g.gridx = 4; g.gridy = 0; g.weightx = 0;
        jPanelDados.add(new JLabel("Data Pedido"), g);
        g.gridx = 5; g.gridy = 0; g.weightx = 0.4;
        try {
            jFormattedTextFieldCadastro = new JFormattedTextField(
                    new javax.swing.text.MaskFormatter("##/##/####"));
        } catch (Exception e) {
            jFormattedTextFieldCadastro = new JFormattedTextField();
        }
        jFormattedTextFieldCadastro.setEditable(false);
        jFormattedTextFieldCadastro.setHorizontalAlignment(JTextField.CENTER);
        jPanelDados.add(jFormattedTextFieldCadastro, g);

        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        jPanelDados.add(new JLabel("Quarto"), g);
        g.gridx = 1; g.gridy = 1; g.weightx = 1.0; g.gridwidth = 4;
        jFormattedTextFieldQuarto = new JFormattedTextField();
        jFormattedTextFieldQuarto.setEditable(false);
        jFormattedTextFieldQuarto.setPreferredSize(new Dimension(300, 25));
        jPanelDados.add(jFormattedTextFieldQuarto, g);
        g.gridwidth = 1;
        g.gridx = 5; g.gridy = 1; g.weightx = 0; g.fill = GridBagConstraints.NONE;
        jButtonRelacionarQuarto = criarBotaoIcone("/images/Find.png", "Buscar Quarto");
        jPanelDados.add(jButtonRelacionarQuarto, g);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 2; g.weightx = 0;
        jPanelDados.add(new JLabel("Produto"), g);
        g.gridx = 1; g.gridy = 2; g.weightx = 1.0; g.gridwidth = 4;
        jFormattedTextFieldProduto = new JFormattedTextField();
        jFormattedTextFieldProduto.setEditable(false);
        jFormattedTextFieldProduto.setPreferredSize(new Dimension(300, 25));
        jPanelDados.add(jFormattedTextFieldProduto, g);
        g.gridwidth = 1;
        g.gridx = 5; g.gridy = 2; g.weightx = 0; g.fill = GridBagConstraints.NONE;
        jButtonRelacionarProduto = criarBotaoIcone("/images/Find.png", "Buscar Produto");
        jPanelDados.add(jButtonRelacionarProduto, g);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 3; g.weightx = 0;
        jPanelDados.add(new JLabel("Quantidade"), g);
        g.gridx = 1; g.gridy = 3; g.weightx = 0.3;
        jTextFieldQuantidade = new JTextField("1", 6);
        jPanelDados.add(jTextFieldQuantidade, g);

        g.gridx = 2; g.gridy = 3; g.weightx = 1.0; g.gridwidth = 4;
        jPanelDados.add(new JLabel(""), g);
        g.gridwidth = 1;

        g.gridx = 0; g.gridy = 4; g.weightx = 0;
        jPanelDados.add(new JLabel("Observação"), g);
        g.gridx = 1; g.gridy = 4; g.weightx = 1.0; g.gridwidth = 5;
        jTextFieldObservacao = new JTextField();
        jPanelDados.add(jTextFieldObservacao, g);
        g.gridwidth = 1;

        add(jPanelDados, BorderLayout.CENTER);

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
        add(jPanelBotoes, BorderLayout.SOUTH);

        pack();
        setSize(780, 360);
        setLocationRelativeTo(null);
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

    public JTextField getjTextFieldId()            { return jTextFieldId; }
    public JTextField getjTextFieldObservacao()     { return jTextFieldObservacao; }
    public JTextField getjTextFieldQuantidade()     { return jTextFieldQuantidade; }
    public JFormattedTextField getjFormattedTextFieldQuarto()   { return jFormattedTextFieldQuarto; }
    public JFormattedTextField getjFormattedTextFieldProduto()  { return jFormattedTextFieldProduto; }
    public JFormattedTextField getjFormattedTextFieldCadastro() { return jFormattedTextFieldCadastro; }
    public JComboBox<String> getjComboBoxStatus()   { return jComboBoxStatus; }
    public JButton getjButtonNovo()                 { return jButtonNovo; }
    public JButton getjButtonCancelar()             { return jButtonCancelar; }
    public JButton getjButtonGravar()               { return jButtonGravar; }
    public JButton getjButtonBuscar()               { return jButtonBuscar; }
    public JButton getjButtonSair()                 { return jButtonSair; }
    public JButton getjButtonRelacionarQuarto()     { return jButtonRelacionarQuarto; }
    public JButton getjButtonRelacionarProduto()    { return jButtonRelacionarProduto; }
    public JPanel getjPanelBotoes()                 { return jPanelBotoes; }
    public JPanel getjPanelDados()                  { return jPanelDados; }
}
