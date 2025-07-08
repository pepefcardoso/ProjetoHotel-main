package controller;

import javax.swing.JOptionPane;
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
        TelaCadastroFornecedor telaFornecedor = (TelaCadastroFornecedor) this.tela;

        if (!telaFornecedor.getjTextFieldId().getText().isEmpty()) {
            try {
                fornecedor.setId(Integer.parseInt(telaFornecedor.getjTextFieldId().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(tela, "O ID do fornecedor é inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro de validação no ID do Fornecedor.");
            }
        }

        fornecedor.setNome(telaFornecedor.getjTextFieldNomeFantasia().getText());
        fornecedor.setRazaoSocial(telaFornecedor.getjTextFieldRazaoSocial().getText());
        fornecedor.setCnpj(telaFornecedor.getjFormattedTextFieldCnpj().getText());
        fornecedor.setInscricaoEstadual(telaFornecedor.getjTextFieldInscricaoEstadual().getText());
        fornecedor.setFone1(telaFornecedor.getjFormattedTextFieldFone1().getText());
        fornecedor.setFone2(telaFornecedor.getjFormattedTextFieldFone2().getText());
        fornecedor.setEmail(telaFornecedor.getjTextFieldEmail().getText());
        fornecedor.setCep(telaFornecedor.getjFormattedTextFieldCep().getText());
        fornecedor.setCidade(telaFornecedor.getjTextFieldCidade().getText());
        fornecedor.setBairro(telaFornecedor.getjTextFieldBairro().getText());
        fornecedor.setLogradouro(telaFornecedor.getjTextFieldLogradouro().getText());
        fornecedor.setComplemento(telaFornecedor.getjTextFieldComplemento().getText());
        fornecedor.setObs(telaFornecedor.getjTextFieldObs().getText());
        fornecedor.setContato(telaFornecedor.getjTextFieldContato().getText());

        if (telaFornecedor.getjComboBoxStatus().getSelectedIndex() == 0) {
            fornecedor.setStatus(Status.ATIVO);
        } else {
            fornecedor.setStatus(Status.INATIVO);
        }

        System.out.println("Fornecedor a ser salvo: " + fornecedor);
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
