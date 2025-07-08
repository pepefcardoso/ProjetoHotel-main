package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public abstract class ControllerBuscaAbstract implements ActionListener {

    protected JDialog telaBusca;
    protected JTable tabela;
    protected JTextField filtro;

    public ControllerBuscaAbstract(JDialog tela, JTable tabela, JTextField filtro) {
        this.telaBusca = tela;
        this.tabela = tabela;
        this.filtro = filtro;

        for (Object componente : tela.getContentPane().getComponents()) {
            if (componente instanceof JPanel jPanel) {
                for (Object subcomponente : jPanel.getComponents()) {
                    if (subcomponente instanceof JButton jButton) {
                        jButton.addActionListener(this);
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Carregar" ->
                carregar();
            case "Filtrar" ->
                filtrar();
            case "Fechar" ->
                sair();
        }
    }

    protected void carregar() {
        if (tabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Não existem dados selecionados!");
        } else {
            JOptionPane.showMessageDialog(null, "Carregando dados para edição...");
        }
    }

    protected void filtrar() {
        if (filtro.getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Sem dados para a seleção...");
        } else {
            realizarFiltragem();
        }
    }

    protected void sair() {
        telaBusca.dispose();
    }

    protected abstract void realizarFiltragem();
}
