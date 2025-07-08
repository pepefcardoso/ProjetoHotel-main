package controller;

import model.Servico;
import model.Status;
import view.TelaCadastroServico;
import view.buscas.TelaBuscaServico;

public class ControllerCadServico extends ControllerCadAbstract {

    public ControllerCadServico(TelaCadastroServico telaCadastroServico) {
        super(telaCadastroServico, telaCadastroServico.getjPanelBotoes(), telaCadastroServico.getjPanelDados());
    }

    @Override
    public void buscar() {
        TelaBuscaServico telaBuscaServico = new TelaBuscaServico(null, true);
        ControllerBuscaServico controllerBuscaServico = new ControllerBuscaServico(telaBuscaServico);
        telaBuscaServico.setVisible(true);
    }

    @Override
    public void preencherObjeto() {
        Servico servico = new Servico();
        servico.setId(Integer.parseInt(((TelaCadastroServico) tela).getjTextFieldId().getText()));
        servico.setDescricao(((TelaCadastroServico) tela).getjTextFieldDescricao().getText());
        servico.setObs(((TelaCadastroServico) tela).getjTextFieldObservacao().getText());

        if (((TelaCadastroServico) tela).getjComboBoxStatus().getSelectedIndex() == 0) {
            servico.setStatus(Status.ATIVO);
        } else {
            servico.setStatus(Status.INATIVO);
        }
    }

    @Override
    public void preencherTela(Object objeto) {
        Servico servico = (Servico) objeto;
        ((TelaCadastroServico) tela).getjTextFieldId().setText(String.valueOf(servico.getId()));
        ((TelaCadastroServico) tela).getjTextFieldDescricao().setText(servico.getDescricao());
        ((TelaCadastroServico) tela).getjTextFieldObservacao().setText(servico.getObs());

        if (servico.getStatus() == Status.ATIVO) {
            ((TelaCadastroServico) tela).getjComboBoxStatus().setSelectedIndex(0);
        } else {
            ((TelaCadastroServico) tela).getjComboBoxStatus().setSelectedIndex(1);
        }
    }
}