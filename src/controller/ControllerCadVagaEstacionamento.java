package controller;

import model.Status;
import model.VagaEstacionamento;
import view.TelaCadastroVagaEstacionamento;
import view.buscas.TelaBuscaVaga;

public class ControllerCadVagaEstacionamento extends ControllerCadAbstract {

    public ControllerCadVagaEstacionamento(TelaCadastroVagaEstacionamento telaCadastroVagaEstacionamento) {
        super(telaCadastroVagaEstacionamento, telaCadastroVagaEstacionamento.getjPanelBotoes(), telaCadastroVagaEstacionamento.getjPanelDados());
    }

    @Override
    public void buscar() {
        TelaBuscaVaga telaBuscaVaga = new TelaBuscaVaga(null, true);
        ControllerBuscaVagaEstacionamento controllerBuscaVagaEstacionamento = new ControllerBuscaVagaEstacionamento(telaBuscaVaga);
        telaBuscaVaga.setVisible(true);
    }

    @Override
    public void preencherObjeto() {
        VagaEstacionamento vaga = new VagaEstacionamento();
        vaga.setId(Integer.parseInt(((TelaCadastroVagaEstacionamento) tela).getjTextFieldId().getText()));
        vaga.setDescricao(((TelaCadastroVagaEstacionamento) tela).getjTextFieldDescricao().getText());
        vaga.setMetragemVaga(Float.parseFloat(((TelaCadastroVagaEstacionamento) tela).getjFormattedTextFieldMetragem().getText()));

        if (((TelaCadastroVagaEstacionamento) tela).getjComboBoxStatus().getSelectedIndex() == 0) {
            vaga.setStatus(Status.ATIVO);
        } else {
            vaga.setStatus(Status.INATIVO);
        }
    }

    @Override
    public void preencherTela(Object objeto) {
        VagaEstacionamento vaga = (VagaEstacionamento) objeto;
        ((TelaCadastroVagaEstacionamento) tela).getjTextFieldId().setText(String.valueOf(vaga.getId()));
        ((TelaCadastroVagaEstacionamento) tela).getjTextFieldDescricao().setText(vaga.getDescricao());
        ((TelaCadastroVagaEstacionamento) tela).getjFormattedTextFieldMetragem().setText(String.valueOf(vaga.getMetragemVaga()));

        if (vaga.getStatus() == Status.ATIVO) {
            ((TelaCadastroVagaEstacionamento) tela).getjComboBoxStatus().setSelectedIndex(0);
        } else {
            ((TelaCadastroVagaEstacionamento) tela).getjComboBoxStatus().setSelectedIndex(1);
        }
    }
}