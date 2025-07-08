package controller;

import javax.swing.JOptionPane;
import view.buscas.TelaBuscaServico;

public class ControllerBuscaServico extends ControllerBuscaAbstract {

    public ControllerBuscaServico(TelaBuscaServico telaBuscaServico) {
        super(telaBuscaServico, telaBuscaServico.getjTableDados(), telaBuscaServico.getjTFFiltro());
    }

    @Override
    protected void realizarFiltragem() {
        TelaBuscaServico tela = (TelaBuscaServico) telaBusca;
        JOptionPane.showMessageDialog(null, "Filtrando informações...");

        switch (tela.getjCBFiltro().getSelectedIndex()) {
            case 0 ->
                JOptionPane.showMessageDialog(null, "Filtrando por ID");
            case 1 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Descrição");
            case 2 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Observação");
        }
    }
}
