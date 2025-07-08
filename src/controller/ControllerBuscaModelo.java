package controller;

import javax.swing.JOptionPane;
import view.buscas.TelaBuscaModelo;

public class ControllerBuscaModelo extends ControllerBuscaAbstract {

    public ControllerBuscaModelo(TelaBuscaModelo telaBuscaModelo) {
        super(telaBuscaModelo, telaBuscaModelo.getjTableDados(), telaBuscaModelo.getjTFFiltro());
    }

    @Override
    protected void realizarFiltragem() {
        TelaBuscaModelo tela = (TelaBuscaModelo) telaBusca;
        JOptionPane.showMessageDialog(null, "Filtrando informações...");

        switch (tela.getjCBFiltro().getSelectedIndex()) {
            case 0 ->
                JOptionPane.showMessageDialog(null, "Filtrando por ID");
            case 1 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Descrição");
            case 2 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Marca");
        }
    }
}
