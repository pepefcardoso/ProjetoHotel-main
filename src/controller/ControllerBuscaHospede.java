package controller;

import javax.swing.JOptionPane;
import view.buscas.TelaBuscaHospede;

public class ControllerBuscaHospede extends ControllerBuscaAbstract {

    public ControllerBuscaHospede(TelaBuscaHospede telaBuscaHospede) {
        super(telaBuscaHospede, telaBuscaHospede.getjTableDados(), telaBuscaHospede.getjTFFiltro());
    }

    @Override
    protected void realizarFiltragem() {
        TelaBuscaHospede tela = (TelaBuscaHospede) telaBusca;
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
