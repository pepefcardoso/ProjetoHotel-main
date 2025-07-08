package controller;

import javax.swing.JOptionPane;
import view.buscas.TelaBuscaVeiculo;

public class ControllerBuscaVeiculo extends ControllerBuscaAbstract {

    public ControllerBuscaVeiculo(TelaBuscaVeiculo telaBuscaVeiculo) {
        super(telaBuscaVeiculo, telaBuscaVeiculo.getjTableDados(), telaBuscaVeiculo.getjTFFiltro());
    }

    @Override
    protected void realizarFiltragem() {
        TelaBuscaVeiculo tela = (TelaBuscaVeiculo) telaBusca;
        JOptionPane.showMessageDialog(null, "Filtrando informações...");
        
        switch (tela.getjCBFiltro().getSelectedIndex()) {
            case 0 -> JOptionPane.showMessageDialog(null, "Filtrando por ID");
            case 1 -> JOptionPane.showMessageDialog(null, "Filtrando por Placa");
            case 2 -> JOptionPane.showMessageDialog(null, "Filtrando por Cor");
            case 3 -> JOptionPane.showMessageDialog(null, "Filtrando por Modelo");
        }
    }
}