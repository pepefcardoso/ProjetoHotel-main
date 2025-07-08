package controller;

import javax.swing.JOptionPane;
import view.buscas.TelaBuscaFuncionario;

public class ControllerBuscaFuncionario extends ControllerBuscaAbstract {

    public ControllerBuscaFuncionario(TelaBuscaFuncionario telaBuscaFuncionario) {
        super(telaBuscaFuncionario, telaBuscaFuncionario.getjTableDados(), telaBuscaFuncionario.getjTFFiltro());
    }

    @Override
    protected void realizarFiltragem() {
        TelaBuscaFuncionario tela = (TelaBuscaFuncionario) telaBusca;
        JOptionPane.showMessageDialog(null, "Filtrando informações...");

        switch (tela.getjCBFiltro().getSelectedIndex()) {
            case 0 ->
                JOptionPane.showMessageDialog(null, "Filtrando por ID");
            case 1 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Nome");
            case 2 ->
                JOptionPane.showMessageDialog(null, "Filtrando por CPF");
        }
    }
}
