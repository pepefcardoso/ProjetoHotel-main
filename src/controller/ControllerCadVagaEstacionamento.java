package controller;

import javax.swing.JOptionPane;
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
        TelaCadastroVagaEstacionamento telaVaga = (TelaCadastroVagaEstacionamento) this.tela;

        if (!telaVaga.getjTextFieldId().getText().isEmpty()) {
            try {
                vaga.setId(Integer.parseInt(telaVaga.getjTextFieldId().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(tela, "O ID da vaga é inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro de validação no ID da Vaga.");
            }
        }

        vaga.setDescricao(telaVaga.getjTextFieldDescricao().getText());

        try {
            String metragemTexto = telaVaga.getjFormattedTextFieldMetragem().getText();
            if (!metragemTexto.isEmpty()) {
                vaga.setMetragemVaga(Float.parseFloat(metragemTexto.replace(",", ".")));
            } else {
                vaga.setMetragemVaga(0.0f);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(tela, "O campo 'Metragem' deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro de validação no campo Metragem.");
        }

        if (telaVaga.getjComboBoxStatus().getSelectedIndex() == 0) {
            vaga.setStatus(Status.ATIVO);
        } else {
            vaga.setStatus(Status.INATIVO);
        }

        System.out.println("Vaga a ser salva: " + vaga);
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
