package controller;

import javax.swing.JOptionPane;
import model.Hospede;
import model.Status;
import view.TelaCadastroHospede;
import view.buscas.TelaBuscaHospede;

public class ControllerCadHospede extends ControllerCadAbstract {

    public ControllerCadHospede(TelaCadastroHospede telaCadastroHospede) {
        super(telaCadastroHospede, telaCadastroHospede.getjPanelBotoes(), telaCadastroHospede.getjPanelDados());

        utilities.Utilities.ativaDesativa(this.painelBotoes, true);
        utilities.Utilities.limpaComponentes(this.painelDados, false);
    }

    @Override
    public void buscar() {
        TelaBuscaHospede telaBuscaHospede = new TelaBuscaHospede(null, true);
        ControllerBuscaHospede controllerBuscaHospede = new ControllerBuscaHospede(telaBuscaHospede);
        telaBuscaHospede.setVisible(true);
    }

    @Override
    public void preencherObjeto() {
        Hospede hospede = new Hospede();
        TelaCadastroHospede telaHospede = (TelaCadastroHospede) this.tela;

        if (!telaHospede.getjTextFieldId().getText().isEmpty()) {
            try {
                hospede.setId(Integer.parseInt(telaHospede.getjTextFieldId().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(tela, "O ID do hóspede é inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro de validação no ID do Hóspede.");
            }
        }

        hospede.setNome(telaHospede.getjTextFieldNomeFantasia().getText());
        hospede.setCpf(telaHospede.getjFormattedTextFieldCpf().getText());
        hospede.setRg(telaHospede.getjTextFieldRg().getText());
        hospede.setFone1(telaHospede.getjFormattedTextFieldFone1().getText());
        hospede.setFone2(telaHospede.getjFormattedTextFieldFone2().getText());
        hospede.setEmail(telaHospede.getjTextFieldEmail().getText());
        hospede.setCep(telaHospede.getjFormattedTextFieldCep().getText());
        hospede.setCidade(telaHospede.getjTextFieldCidade().getText());
        hospede.setBairro(telaHospede.getjTextFieldBairro().getText());
        hospede.setLogradouro(telaHospede.getjTextFieldLogradouro().getText());
        hospede.setComplemento(telaHospede.getjTextFieldComplemento().getText());
        hospede.setObs(telaHospede.getjTextFieldObs().getText());
        hospede.setContato(telaHospede.getjTextFieldContato().getText());

        if (telaHospede.getjComboBoxStatus().getSelectedIndex() == 0) {
            hospede.setStatus(Status.ATIVO);
        } else {
            hospede.setStatus(Status.INATIVO);
        }

        System.out.println("Hóspede a ser salvo: " + hospede);
    }

    @Override
    public void preencherTela(Object objeto) {
        if (objeto instanceof Hospede hospede) {
            TelaCadastroHospede tela = (TelaCadastroHospede) this.tela;

            tela.getjTextFieldId().setText(String.valueOf(hospede.getId()));
            tela.getjTextFieldNomeFantasia().setText(hospede.getNome());
            tela.getjFormattedTextFieldCpf().setText(hospede.getCpf());
            tela.getjTextFieldRg().setText(hospede.getRg());
            tela.getjFormattedTextFieldFone1().setText(hospede.getFone1());
            tela.getjFormattedTextFieldFone2().setText(hospede.getFone2());
            tela.getjTextFieldEmail().setText(hospede.getEmail());
            tela.getjFormattedTextFieldCep().setText(hospede.getCep());
            tela.getjTextFieldCidade().setText(hospede.getCidade());
            tela.getjTextFieldBairro().setText(hospede.getBairro());
            tela.getjTextFieldLogradouro().setText(hospede.getLogradouro());
            tela.getjTextFieldComplemento().setText(hospede.getComplemento());
            tela.getjTextFieldObs().setText(hospede.getObs());
            tela.getjTextFieldContato().setText(hospede.getContato());

            if (hospede.getStatus() == Status.ATIVO) {
                tela.getjComboBoxStatus().setSelectedIndex(0);
            } else {
                tela.getjComboBoxStatus().setSelectedIndex(1);
            }
        }
    }
}
