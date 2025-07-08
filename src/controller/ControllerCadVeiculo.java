package controller;

import javax.swing.JOptionPane;
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
        TelaCadastroVeiculo telaVeiculo = (TelaCadastroVeiculo) this.tela;

        if (!telaVeiculo.getjTextFieldId().getText().isEmpty()) {
            try {
                veiculo.setId(Integer.parseInt(telaVeiculo.getjTextFieldId().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(tela, "O ID do veículo é inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro de validação no ID do Veículo.");
            }
        }

        veiculo.setPlaca(telaVeiculo.getjTextFieldPlaca().getText());
        veiculo.setCor(telaVeiculo.getjTextFieldCor().getText());

        if (telaVeiculo.getjComboBoxStatus().getSelectedIndex() == 0) {
            veiculo.setStatus(Status.ATIVO);
        } else {
            veiculo.setStatus(Status.INATIVO);
        }

        Object itemSelecionado = telaVeiculo.getjComboBoxModelo().getSelectedItem();
        if (itemSelecionado instanceof Modelo) {
            veiculo.setModelo((Modelo) itemSelecionado);
        } else {
            JOptionPane.showMessageDialog(tela, "Por favor, selecione um modelo para o veículo.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException("Modelo não selecionado.");
        }

        System.out.println("Veículo a ser salvo: " + veiculo);
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
