package controller;

import javax.swing.JOptionPane;
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
        TelaCadastroServico telaServico = (TelaCadastroServico) this.tela;

        if (!telaServico.getjTextFieldId().getText().isEmpty()) {
            try {
                servico.setId(Integer.parseInt(telaServico.getjTextFieldId().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(tela, "O ID do serviço é inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro de validação no ID do Serviço.");
            }
        }

        servico.setDescricao(telaServico.getjTextFieldDescricao().getText());
        servico.setObs(telaServico.getjTextFieldObservacao().getText());

        if (telaServico.getjComboBoxStatus().getSelectedIndex() == 0) {
            servico.setStatus(Status.ATIVO);
        } else {
            servico.setStatus(Status.INATIVO);
        }

        System.out.println("Serviço a ser salvo: " + servico);
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
