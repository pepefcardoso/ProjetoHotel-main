package controller;

import model.Modelo;
import model.Status;
import model.Veiculo;
import view.TelaCadastroVeiculo;
import view.buscas.TelaBuscaVeiculo;

public class ControllerCadVeiculo extends ControllerCadAbstract {

    public ControllerCadVeiculo(TelaCadastroVeiculo telaCadastroVeiculo) {
        super(telaCadastroVeiculo, telaCadastroVeiculo.getjPanelBotoes(), telaCadastroVeiculo.getjPanelDados());
    }

    @Override
    public void buscar() {
        TelaBuscaVeiculo telaBuscaVeiculo = new TelaBuscaVeiculo(null, true);
        ControllerBuscaVeiculo controllerBuscaVeiculo = new ControllerBuscaVeiculo(telaBuscaVeiculo);
        telaBuscaVeiculo.setVisible(true);
    }

    @Override
    public void preencherObjeto() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(Integer.parseInt(((TelaCadastroVeiculo) tela).getjTextFieldId().getText()));
        veiculo.setPlaca(((TelaCadastroVeiculo) tela).getjTextFieldPlaca().getText());
        veiculo.setCor(((TelaCadastroVeiculo) tela).getjTextFieldCor().getText());

        if (((TelaCadastroVeiculo) tela).getjComboBoxStatus().getSelectedIndex() == 0) {
            veiculo.setStatus(Status.ATIVO);
        } else {
            veiculo.setStatus(Status.INATIVO);
        }

        Modelo modelo = (Modelo) ((TelaCadastroVeiculo) tela).getjComboBoxModelo().getSelectedItem();
        veiculo.setModelo(modelo);
    }

    @Override
    public void preencherTela(Object objeto) {
        Veiculo veiculo = (Veiculo) objeto;
        ((TelaCadastroVeiculo) tela).getjTextFieldId().setText(String.valueOf(veiculo.getId()));
        ((TelaCadastroVeiculo) tela).getjTextFieldPlaca().setText(veiculo.getPlaca());
        ((TelaCadastroVeiculo) tela).getjTextFieldCor().setText(veiculo.getCor());

        if (veiculo.getStatus() == Status.ATIVO) {
            ((TelaCadastroVeiculo) tela).getjComboBoxStatus().setSelectedIndex(0);
        } else {
            ((TelaCadastroVeiculo) tela).getjComboBoxStatus().setSelectedIndex(1);
        }

        ((TelaCadastroVeiculo) tela).getjComboBoxModelo().setSelectedItem(veiculo.getModelo());
    }
}