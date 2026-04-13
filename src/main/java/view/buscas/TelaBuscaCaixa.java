package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaBuscaCaixa extends JDialog {

    private JTable jTableDados;
    private JScrollPane jScrollPane1;
    private JTextField jTFFiltro;
    private JComboBox<String> jCBFiltro;
    private JButton jButtonCarregar;
    private JButton jButtonFiltar;
    private JButton jButtonSair;
    private JButton jButtonAtivar;
    private JButton jButtonInativar;

    public TelaBuscaCaixa(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        setTitle("Busca de Caixas");
        setResizable(false);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(255, 204, 153));
        panelTitulo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelTitulo.setPreferredSize(new Dimension(0, 45));
        JLabel labelTitulo = new JLabel("Caixas");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelTitulo.add(labelTitulo, BorderLayout.CENTER);

        jTableDados = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Abertura", "Fechamento", "Vlr. Abertura", "Vlr. Fechamento", "Status", "Obs"}
        ));
        jTableDados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableDados.setDefaultEditor(Object.class, null);
        int[] maxWidths = {40, 130, 130, 110, 110, 60, 200};
        for (int i = 0; i < maxWidths.length; i++) {
            jTableDados.getColumnModel().getColumn(i).setMaxWidth(maxWidths[i]);
        }

        jScrollPane1 = new JScrollPane(jTableDados);
        JPanel panelDados = new JPanel(new BorderLayout());
        panelDados.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelDados.add(jScrollPane1, BorderLayout.CENTER);
        panelDados.setPreferredSize(new Dimension(0, 220));

        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; panelFiltros.add(new JLabel("Filtrar Por"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelFiltros.add(new JLabel("Valor"), gbc);

        jCBFiltro = new JComboBox<>(new String[]{"ID", "Status", "Observação"});
        jCBFiltro.setPreferredSize(new Dimension(162, 25));
        gbc.gridx = 0; gbc.gridy = 1; panelFiltros.add(jCBFiltro, gbc);

        jTFFiltro = new JTextField();
        jTFFiltro.setPreferredSize(new Dimension(258, 25));
        gbc.gridx = 1; gbc.gridy = 1; panelFiltros.add(jTFFiltro, gbc);

        jButtonFiltar = new JButton("Filtrar");
        try { jButtonFiltar.setIcon(new ImageIcon(getClass().getResource("/images/Find.png"))); } catch (Exception ignored) {}
        gbc.gridx = 2; gbc.gridy = 1; panelFiltros.add(jButtonFiltar, gbc);

        jButtonCarregar = new JButton("Carregar");
        try { jButtonCarregar.setIcon(new ImageIcon(getClass().getResource("/images/Load.png"))); } catch (Exception ignored) {}
        gbc.gridx = 0; gbc.gridy = 2; panelFiltros.add(jButtonCarregar, gbc);

        jButtonAtivar = new JButton("Ativar");
        try { jButtonAtivar.setIcon(new ImageIcon(getClass().getResource("/images/OK.png"))); } catch (Exception ignored) {}
        jButtonAtivar.setEnabled(false);
        jButtonAtivar.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 1; gbc.gridy = 2; panelFiltros.add(jButtonAtivar, gbc);

        jButtonInativar = new JButton("Inativar");
        try { jButtonInativar.setIcon(new ImageIcon(getClass().getResource("/images/Cancel.png"))); } catch (Exception ignored) {}
        jButtonInativar.setEnabled(false);
        jButtonInativar.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 2; gbc.gridy = 2; panelFiltros.add(jButtonInativar, gbc);

        jButtonSair = new JButton("Fechar");
        try { jButtonSair.setIcon(new ImageIcon(getClass().getResource("/images/Exit.png"))); } catch (Exception ignored) {}
        gbc.gridx = 3; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        panelFiltros.add(jButtonSair, gbc);

        JPanel main = new JPanel(new BorderLayout(0, 2));
        main.add(panelTitulo, BorderLayout.NORTH);
        main.add(panelDados, BorderLayout.CENTER);
        main.add(panelFiltros, BorderLayout.SOUTH);

        setContentPane(main);
        pack();
        setSize(800, 400);
        setLocationRelativeTo(null);
    }

    public JButton getjButtonCarregar()  { return jButtonCarregar; }
    public JButton getjButtonFiltar()    { return jButtonFiltar; }
    public JButton getjButtonSair()      { return jButtonSair; }
    public JButton getjButtonAtivar()    { return jButtonAtivar; }
    public JButton getjButtonInativar()  { return jButtonInativar; }
    public JTable getjTableDados()       { return jTableDados; }
    public JTextField getjTFFiltro()     { return jTFFiltro; }
    public JComboBox<String> getjCBFiltro() { return jCBFiltro; }
}
