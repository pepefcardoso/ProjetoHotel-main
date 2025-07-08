package controller;

import javax.swing.JOptionPane;
import view.buscas.TelaBuscaFornecedor;

public class ControllerBuscaFornecedor extends ControllerBuscaAbstract {

    public ControllerBuscaFornecedor(TelaBuscaFornecedor telaBuscaFornecedor) {
        super(telaBuscaFornecedor, telaBuscaFornecedor.getjTableDados(), telaBuscaFornecedor.getjTFFiltro());
    }

    @Override
    protected void realizarFiltragem() {
        TelaBuscaFornecedor tela = (TelaBuscaFornecedor) telaBusca;
        JOptionPane.showMessageDialog(null, "Filtrando informações...");
        switch (tela.getjCBFiltro().getSelectedIndex()) {
            case 0 ->
                JOptionPane.showMessageDialog(null, "Filtrando por ID");
            case 1 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Nome Fantasia");
            case 2 ->
                JOptionPane.showMessageDialog(null, "Filtrando por Razão Social");
            case 3 ->
                JOptionPane.showMessageDialog(null, "Filtrando por CNPJ");
            default -> {
            }
        }
    }
}
