
package controller;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import utilities.Utilities;


public abstract class ControllerCadAbstract implements IController {

    protected JDialog tela;
    protected JPanel painelBotoes;
    protected JPanel painelDados;

    public ControllerCadAbstract(JDialog tela, JPanel painelBotoes, JPanel painelDados) {
        this.tela = tela;
        this.painelBotoes = painelBotoes;
        this.painelDados = painelDados;

        for (Object componente : this.painelBotoes.getComponents()) {
            if (componente instanceof JButton jButton) {
                jButton.addActionListener(this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Novo" ->
                novo();
            case "Gravar" ->
                gravar();
            case "Cancelar" ->
                cancelar();
            case "Buscar" ->
                buscar();
            case "Fechar" ->
                sair();
        }
    }

    @Override
    public void novo() {
        Utilities.ativaDesativa(painelBotoes, false);
        Utilities.limpaComponentes(painelDados, true);
    }

    @Override
    public void gravar() {
        try {
            preencherObjeto();
            Utilities.ativaDesativa(painelBotoes, true);
            Utilities.limpaComponentes(painelDados, false);
        } catch (Exception e) {
            System.err.println("A gravação foi interrompida: " + e.getMessage());
        }
    }

    @Override
    public void cancelar() {
        Utilities.ativaDesativa(painelBotoes, true);
        Utilities.limpaComponentes(painelDados, false);
    }

    @Override
    public void sair() {
        tela.dispose();
    }

    @Override
    public abstract void buscar();

    @Override
    public abstract void preencherObjeto();

    @Override
    public abstract void preencherTela(Object objeto);
}
