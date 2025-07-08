package controller;

import javax.swing.JOptionPane;
import view.buscas.TelaBuscaProduto;

public class ControllerBuscaProduto extends ControllerBuscaAbstract {

    public ControllerBuscaProduto(TelaBuscaProduto telaBuscaProduto) {
        super(telaBuscaProduto, telaBuscaProduto.getjTableDados(), telaBuscaProduto.getjTFFiltro());
    }

    @Override
    protected void realizarFiltragem() {
        TelaBuscaProduto tela = (TelaBuscaProduto) telaBusca;
        JOptionPane.showMessageDialog(null, "Filtrando informações...");

        switch (tela.getjCBFiltro().getSelectedIndex()) {
            case 0 ->
                JOptionPane.showMessageDialog(null, "Filtrando por ID");
            case 1 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Descrição");
            case 2 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Observação");
            case 3 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Valor");
        }
    }
}
