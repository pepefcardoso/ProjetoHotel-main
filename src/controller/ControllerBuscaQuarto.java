package controller;

import javax.swing.JOptionPane;
import view.buscas.TelaBuscaQuarto;

public class ControllerBuscaQuarto extends ControllerBuscaAbstract {

    public ControllerBuscaQuarto(TelaBuscaQuarto telaBuscaQuarto) {
        super(telaBuscaQuarto, telaBuscaQuarto.getjTableDados(), telaBuscaQuarto.getjTFFiltro());
    }

    @Override
    protected void realizarFiltragem() {
        TelaBuscaQuarto tela = (TelaBuscaQuarto) telaBusca;
        JOptionPane.showMessageDialog(null, "Filtrando informações...");

        switch (tela.getjCBFiltro().getSelectedIndex()) {
            case 0 ->
                JOptionPane.showMessageDialog(null, "Filtrando por ID");
            case 1 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Descrição");
            case 2 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Capacidade");
            case 3 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Metragem");
            case 4 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Identificação");
            case 5 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Andar");
            case 6 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Animais");
            case 7 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Observação");
        }
    }
}
