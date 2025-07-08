package controller;

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

        TelaCadastroHospede tela = (TelaCadastroHospede) this.tela;

        hospede.setNome(tela.getjTextFieldNomeFantasia().getText());
        hospede.setCpf(tela.getjFormattedTextFieldCpf().getText());
        hospede.setRg(tela.getjTextFieldRg().getText());
        hospede.setFone1(tela.getjFormattedTextFieldFone1().getText());
        hospede.setFone2(tela.getjFormattedTextFieldFone2().getText());
        hospede.setEmail(tela.getjTextFieldEmail().getText());
        hospede.setCep(tela.getjFormattedTextFieldCep().getText());
        hospede.setCidade(tela.getjTextFieldCidade().getText());
        hospede.setBairro(tela.getjTextFieldBairro().getText());
        hospede.setLogradouro(tela.getjTextFieldLogradouro().getText());
        hospede.setComplemento(tela.getjTextFieldComplemento().getText());
        hospede.setObs(tela.getjTextFieldObs().getText());
        hospede.setContato(tela.getjTextFieldContato().getText());

        if (tela.getjComboBoxStatus().getSelectedIndex() == 0) {
            hospede.setStatus(Status.ATIVO);
        } else {
            hospede.setStatus(Status.INATIVO);
        }

        System.out.println(hospede);
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
