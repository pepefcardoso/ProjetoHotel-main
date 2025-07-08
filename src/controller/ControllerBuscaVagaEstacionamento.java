package controller;

import javax.swing.JOptionPane;
import view.buscas.TelaBuscaVaga;

public class ControllerBuscaVagaEstacionamento extends ControllerBuscaAbstract {

    public ControllerBuscaVagaEstacionamento(TelaBuscaVaga telaBuscaVaga) {
        super(telaBuscaVaga, telaBuscaVaga.getjTableDados(), telaBuscaVaga.getjTFFiltro());
    }

    @Override
    protected void realizarFiltragem() {
        TelaBuscaVaga tela = (TelaBuscaVaga) telaBusca;
        JOptionPane.showMessageDialog(null, "Filtrando informações...");

        switch (tela.getjCBFiltro().getSelectedIndex()) {
            case 0 ->
                JOptionPane.showMessageDialog(null, "Filtrando por ID");
            case 1 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Número");
            case 2 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Tipo");
        }
    }
}
