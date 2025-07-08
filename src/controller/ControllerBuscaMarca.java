package controller;

import javax.swing.JOptionPane;
import view.buscas.TelaBuscaMarca;

public class ControllerBuscaMarca extends ControllerBuscaAbstract {

    public ControllerBuscaMarca(TelaBuscaMarca telaBuscaMarca) {
        super(telaBuscaMarca, telaBuscaMarca.getjTableDados(), telaBuscaMarca.getjTFFiltro());
    }

    @Override
    protected void realizarFiltragem() {
        TelaBuscaMarca tela = (TelaBuscaMarca) telaBusca;
        JOptionPane.showMessageDialog(null, "Filtrando informações...");

        if (tela.getjCBFiltro().getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Filtrando por ID");
        } else if (tela.getjCBFiltro().getSelectedIndex() == 1) {
            JOptionPane.showMessageDialog(null, "Filtrando por Descrição");
        }
    }
}
