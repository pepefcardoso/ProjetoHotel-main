/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

public class TelaCadastroReserva extends javax.swing.JDialog {

    private javax.swing.JLabel jLabelQuarto;
    private javax.swing.JTextField jTextFieldQuarto;
    private javax.swing.JButton jButtonBuscarQuarto;
    private javax.swing.JPanel jPanelQuarto;

    public TelaCadastroReserva(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        inicializarCamposQuarto();
    }

    private void inicializarCamposQuarto() {
        jLabelQuarto = new javax.swing.JLabel("Quarto:");

        jTextFieldQuarto = new javax.swing.JTextField();
        jTextFieldQuarto.setEnabled(false);

        jButtonBuscarQuarto = new javax.swing.JButton();
        jButtonBuscarQuarto.setIcon(
                new javax.swing.ImageIcon(getClass().getResource("/images/Find.png")));
        jButtonBuscarQuarto.setActionCommand("1");

        jPanelQuarto = new javax.swing.JPanel();
        jPanelQuarto.setBorder(
                javax.swing.BorderFactory.createBevelBorder(
                        javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout gl = new javax.swing.GroupLayout(jPanelQuarto);
        jPanelQuarto.setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(jLabelQuarto)
                .addComponent(jTextFieldQuarto)
                .addComponent(jButtonBuscarQuarto,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        gl.setVerticalGroup(
                gl.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelQuarto)
                        .addComponent(jTextFieldQuarto,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonBuscarQuarto)
        );

        javax.swing.GroupLayout layout
                = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout.createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jPanelTitulo,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPanelDados,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPanelQuarto,
                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                                                .addComponent(jPanelBotoes,
                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE))
                                        .addContainerGap())
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanelTitulo,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanelDados,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanelQuarto,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanelBotoes,
                                        javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }

    public javax.swing.JTextField getjTextFieldQuarto() {
        return jTextFieldQuarto;
    }

    public javax.swing.JButton getjButtonBuscarQuarto() {
        return jButtonBuscarQuarto;
    }

    public javax.swing.JButton getjButtonBuscar() {
        return jButtonBuscar;
    }

    public javax.swing.JButton getjButtonCancelar() {
        return jButtonCancelar;
    }

    public javax.swing.JButton getjButtonGravar() {
        return jButtonGravar;
    }

    public javax.swing.JButton getjButtonNovo() {
        return jButtonNovo;
    }

    public javax.swing.JButton getjButtonSair() {
        return jButtonSair;
    }

    public javax.swing.JPanel getjPanelBotoes() {
        return jPanelBotoes;
    }

    public javax.swing.JPanel getjPanelDados() {
        return jPanelDados;
    }

    public javax.swing.JTextField getjTextFieldId() {
        return jTextFieldId;
    }

    public javax.swing.JComboBox<String> getjComboBoxStatus() {
        return jComboBoxStatus;
    }

    public javax.swing.JFormattedTextField getjFormattedTextFieldPrevisaoEntrada() {
        return jFormattedTextFieldPrevisaoEntrada;
    }

    public javax.swing.JFormattedTextField getjFormattedTextFieldDataPrevisaoSaida() {
        return jFormattedTextFieldDataPrevisaoSaida;
    }

    public javax.swing.JTextField getjTextFieldObservacao() {
        return jTextFieldObservacao;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTitulo = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jPanelDados = new javax.swing.JPanel();
        jLabelId = new javax.swing.JLabel();
        jTextFieldId = new javax.swing.JTextField();
        jLabelPrevisaoEntrada = new javax.swing.JLabel();
        jLabelStatus = new javax.swing.JLabel();
        jComboBoxStatus = new javax.swing.JComboBox<>();
        jTextFieldObservacao = new javax.swing.JTextField();
        jFormattedTextFieldPrevisaoEntrada = new javax.swing.JFormattedTextField();
        jLabelObservacao = new javax.swing.JLabel();
        jFormattedTextFieldDataPrevisaoSaida = new javax.swing.JFormattedTextField();
        jLabelPrevisaoSaida = new javax.swing.JLabel();
        jPanelBotoes = new javax.swing.JPanel();
        jButtonNovo = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jButtonGravar = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reserva");
        setResizable(false);

        jPanelTitulo.setBackground(new java.awt.Color(255, 204, 153));
        jPanelTitulo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabelTitulo.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Reserva");

        javax.swing.GroupLayout jPanelTituloLayout = new javax.swing.GroupLayout(jPanelTitulo);
        jPanelTitulo.setLayout(jPanelTituloLayout);
        jPanelTituloLayout.setHorizontalGroup(
                jPanelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelTituloLayout.setVerticalGroup(
                jPanelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        jPanelDados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabelId.setText("ID");

        jTextFieldId.setEnabled(false);
        jTextFieldId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIdActionPerformed(evt);
            }
        });

        jLabelPrevisaoEntrada.setText("Data Previsão Entrada:");

        jLabelStatus.setText("Status");

        jComboBoxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ativo", "Inativo"}));
        jComboBoxStatus.setEnabled(false);

        jTextFieldObservacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldObservacaoActionPerformed(evt);
            }
        });

        jFormattedTextFieldPrevisaoEntrada.setEditable(false);
        try {
            jFormattedTextFieldPrevisaoEntrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldPrevisaoEntrada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldPrevisaoEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldPrevisaoEntradaActionPerformed(evt);
            }
        });

        jLabelObservacao.setText("Observação:");

        jFormattedTextFieldDataPrevisaoSaida.setEditable(false);
        try {
            jFormattedTextFieldDataPrevisaoSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDataPrevisaoSaida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldDataPrevisaoSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldDataPrevisaoSaidaActionPerformed(evt);
            }
        });

        jLabelPrevisaoSaida.setText("Data Previsão Saída:");

        javax.swing.GroupLayout jPanelDadosLayout = new javax.swing.GroupLayout(jPanelDados);
        jPanelDados.setLayout(jPanelDadosLayout);
        jPanelDadosLayout.setHorizontalGroup(
                jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanelDadosLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabelId)
                                                        .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 494, Short.MAX_VALUE)
                                                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jComboBoxStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(jPanelDadosLayout.createSequentialGroup()
                                                                .addComponent(jLabelStatus)
                                                                .addGap(156, 156, 156))))
                                        .addGroup(jPanelDadosLayout.createSequentialGroup()
                                                .addGap(272, 272, 272)
                                                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabelPrevisaoSaida)
                                                        .addComponent(jLabelPrevisaoEntrada)
                                                        .addComponent(jFormattedTextFieldDataPrevisaoSaida, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                                        .addComponent(jFormattedTextFieldPrevisaoEntrada)
                                                        .addComponent(jLabelObservacao)
                                                        .addComponent(jTextFieldObservacao))
                                                .addGap(10, 10, 10)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanelDadosLayout.setVerticalGroup(
                jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanelDadosLayout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabelId)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanelDadosLayout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDadosLayout.createSequentialGroup()
                                                                .addComponent(jLabelStatus)
                                                                .addGap(28, 28, 28))
                                                        .addComponent(jComboBoxStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addComponent(jLabelPrevisaoEntrada)
                                .addGap(4, 4, 4)
                                .addComponent(jFormattedTextFieldPrevisaoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelPrevisaoSaida)
                                .addGap(5, 5, 5)
                                .addComponent(jFormattedTextFieldDataPrevisaoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelObservacao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanelBotoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Create.png")));
        jButtonNovo.setText("Novo");
        jButtonNovo.setActionCommand("0");
        jPanelBotoes.add(jButtonNovo);

        jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel.png")));
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setActionCommand("1");
        jButtonCancelar.setEnabled(false);
        jPanelBotoes.add(jButtonCancelar);

        jButtonGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OK.png")));
        jButtonGravar.setText("Gravar");
        jButtonGravar.setActionCommand("1");
        jButtonGravar.setEnabled(false);
        jPanelBotoes.add(jButtonGravar);

        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Load.png")));
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.setActionCommand("0");
        jPanelBotoes.add(jButtonBuscar);

        jButtonSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Exit.png")));
        jButtonSair.setText("Sair");
        jButtonSair.setActionCommand("0");
        jPanelBotoes.add(jButtonSair);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldIdActionPerformed(java.awt.event.ActionEvent evt) {
    }//GEN-FIRST:event_jTextFieldIdActionPerformed
    //GEN-LAST:event_jTextFieldIdActionPerformed

    private void jTextFieldObservacaoActionPerformed(java.awt.event.ActionEvent evt) {
    }//GEN-FIRST:event_jTextFieldObservacaoActionPerformed
    //GEN-LAST:event_jTextFieldObservacaoActionPerformed

    private void jFormattedTextFieldPrevisaoEntradaActionPerformed(java.awt.event.ActionEvent evt) {
    }//GEN-FIRST:event_jFormattedTextFieldPrevisaoEntradaActionPerformed
    //GEN-LAST:event_jFormattedTextFieldPrevisaoEntradaActionPerformed

    private void jFormattedTextFieldDataPrevisaoSaidaActionPerformed(java.awt.event.ActionEvent evt) {
    }//GEN-FIRST:event_jFormattedTextFieldDataPrevisaoSaidaActionPerformed
    //GEN-LAST:event_jFormattedTextFieldDataPrevisaoSaidaActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            TelaCadastroReserva dialog = new TelaCadastroReserva(new javax.swing.JFrame(), true);
            new controller.ControllerCadReserva(dialog);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JComboBox<String> jComboBoxStatus;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataPrevisaoSaida;
    private javax.swing.JFormattedTextField jFormattedTextFieldPrevisaoEntrada;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelObservacao;
    private javax.swing.JLabel jLabelPrevisaoEntrada;
    private javax.swing.JLabel jLabelPrevisaoSaida;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanelBotoes;
    private javax.swing.JPanel jPanelDados;
    private javax.swing.JPanel jPanelTitulo;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldObservacao;
    // End of variables declaration//GEN-END:variables
}
