package controller;

import model.Fornecedor;
import model.Status;
import view.TelaCadastroFornecedor;
import view.buscas.TelaBuscaFornecedor;

public class ControllerCadFornecedor extends ControllerCadAbstract {

    public ControllerCadFornecedor(TelaCadastroFornecedor telaCadastroFornecedor) {
        super(telaCadastroFornecedor, telaCadastroFornecedor.getjPanelBotoes(), telaCadastroFornecedor.getjPanelDados());

        utilities.Utilities.ativaDesativa(this.painelBotoes, true);
        utilities.Utilities.limpaComponentes(this.painelDados, false);
    }

    @Override
    public void buscar() {
        TelaBuscaFornecedor telaBuscaFornecedor = new TelaBuscaFornecedor(null, true);
        ControllerBuscaFornecedor controllerBuscaFornecedor = new ControllerBuscaFornecedor(telaBuscaFornecedor);
        telaBuscaFornecedor.setVisible(true);
    }

    @Override
    public void preencherObjeto() {
        Fornecedor fornecedor = new Fornecedor();
        TelaCadastroFornecedor tela = (TelaCadastroFornecedor) this.tela;

        fornecedor.setNome(tela.getjTextFieldNomeFantasia().getText());
        fornecedor.setRazaoSocial(tela.getjTextFieldRazaoSocial().getText());
        fornecedor.setCnpj(tela.getjFormattedTextFieldCnpj().getText());
        fornecedor.setInscricaoEstadual(tela.getjTextFieldInscricaoEstadual().getText());
        fornecedor.setFone1(tela.getjFormattedTextFieldFone1().getText());
        fornecedor.setFone2(tela.getjFormattedTextFieldFone2().getText());
        fornecedor.setEmail(tela.getjTextFieldEmail().getText());
        fornecedor.setCep(tela.getjFormattedTextFieldCep().getText());
        fornecedor.setCidade(tela.getjTextFieldCidade().getText());
        fornecedor.setBairro(tela.getjTextFieldBairro().getText());
        fornecedor.setLogradouro(tela.getjTextFieldLogradouro().getText());
        fornecedor.setComplemento(tela.getjTextFieldComplemento().getText());
        fornecedor.setObs(tela.getjTextFieldObs().getText());
        fornecedor.setContato(tela.getjTextFieldContato().getText());

        if (tela.getjComboBoxStatus().getSelectedIndex() == 0) {
            fornecedor.setStatus(Status.ATIVO);
        } else {
            fornecedor.setStatus(Status.INATIVO);
        }

        System.out.println(fornecedor);
    }

    @Override
    public void preencherTela(Object objeto) {
        if (objeto instanceof Fornecedor fornecedor) {
            TelaCadastroFornecedor tela = (TelaCadastroFornecedor) this.tela;

            tela.getjTextFieldId().setText(String.valueOf(fornecedor.getId()));
            tela.getjTextFieldNomeFantasia().setText(fornecedor.getNome());
            tela.getjTextFieldRazaoSocial().setText(fornecedor.getRazaoSocial());
            tela.getjFormattedTextFieldCnpj().setText(fornecedor.getCnpj());
            tela.getjTextFieldInscricaoEstadual().setText(fornecedor.getInscricaoEstadual());
            tela.getjFormattedTextFieldFone1().setText(fornecedor.getFone1());
            tela.getjFormattedTextFieldFone2().setText(fornecedor.getFone2());
            tela.getjTextFieldEmail().setText(fornecedor.getEmail());
            tela.getjFormattedTextFieldCep().setText(fornecedor.getCep());
            tela.getjTextFieldCidade().setText(fornecedor.getCidade());
            tela.getjTextFieldBairro().setText(fornecedor.getBairro());
            tela.getjTextFieldLogradouro().setText(fornecedor.getLogradouro());
            tela.getjTextFieldComplemento().setText(fornecedor.getComplemento());
            tela.getjTextFieldObs().setText(fornecedor.getObs());
            tela.getjTextFieldContato().setText(fornecedor.getContato());

            if (fornecedor.getStatus() == Status.ATIVO) {
                tela.getjComboBoxStatus().setSelectedIndex(0);
            } else {
                tela.getjComboBoxStatus().setSelectedIndex(1);
            }
        }
    }
}
