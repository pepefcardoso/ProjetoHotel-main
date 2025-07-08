package controller;

import model.Funcionario;
import model.Status;
import view.TelaCadastroFuncionario;
import view.buscas.TelaBuscaFuncionario;

public class ControllerCadFuncionario extends ControllerCadAbstract {

    public ControllerCadFuncionario(TelaCadastroFuncionario telaCadastroFuncionario) {
        super(telaCadastroFuncionario, telaCadastroFuncionario.getjPanelBotoes(), telaCadastroFuncionario.getjPanelDados());

        utilities.Utilities.ativaDesativa(this.painelBotoes, true);
        utilities.Utilities.limpaComponentes(this.painelDados, false);
    }

    @Override
    public void buscar() {
        TelaBuscaFuncionario telaBuscaFuncionario = new TelaBuscaFuncionario(null, true);
        ControllerBuscaFuncionario controllerBuscaFuncionario = new ControllerBuscaFuncionario(telaBuscaFuncionario);
        telaBuscaFuncionario.setVisible(true);
    }

    @Override
    public void preencherObjeto() {
        Funcionario funcionario = new Funcionario();
        TelaCadastroFuncionario tela = (TelaCadastroFuncionario) this.tela;

        funcionario.setNome(tela.getjTextFieldDescricao().getText());
        funcionario.setUsuario(tela.getjTextFieldDescricao().getText()); // Assumindo que o nome é o usuário
        funcionario.setSenha(new String(tela.getjPasswordFieldSenha().getPassword()));
        funcionario.setRg(tela.getjTextFieldRg().getText());
        funcionario.setCpf(tela.getjFormattedTextFieldCpf().getText());
        funcionario.setFone1(tela.getjFormattedTextFieldFone1().getText());
        funcionario.setFone2(tela.getjFormattedTextFieldFone2().getText());
        funcionario.setEmail(tela.getjTextFieldEmail().getText());
        funcionario.setCep(tela.getjFormattedTextFieldCep().getText());
        funcionario.setCidade(tela.getjTextFieldCidade().getText());
        funcionario.setBairro(tela.getjTextFieldBairro().getText());
        funcionario.setLogradouro(tela.getjTextFieldLogradouro().getText());
        funcionario.setComplemento(tela.getjTextFieldComplemento().getText());
        funcionario.setObs(tela.getjTextFieldObs().getText());

        if (tela.getjComboBoxStatus().getSelectedIndex() == 0) {
            funcionario.setStatus(Status.ATIVO);
        } else {
            funcionario.setStatus(Status.INATIVO);
        }

        System.out.println(funcionario);
    }

    @Override
    public void preencherTela(Object objeto) {
        if (objeto instanceof Funcionario funcionario) {
            TelaCadastroFuncionario tela = (TelaCadastroFuncionario) this.tela;

            tela.getjTextFieldId().setText(String.valueOf(funcionario.getId()));
            tela.getjTextFieldDescricao().setText(funcionario.getNome());
            tela.getjPasswordFieldSenha().setText(funcionario.getSenha());
            tela.getjTextFieldRg().setText(funcionario.getRg());
            tela.getjFormattedTextFieldCpf().setText(funcionario.getCpf());
            tela.getjFormattedTextFieldFone1().setText(funcionario.getFone1());
            tela.getjFormattedTextFieldFone2().setText(funcionario.getFone2());
            tela.getjTextFieldEmail().setText(funcionario.getEmail());
            tela.getjFormattedTextFieldCep().setText(funcionario.getCep());
            tela.getjTextFieldCidade().setText(funcionario.getCidade());
            tela.getjTextFieldBairro().setText(funcionario.getBairro());
            tela.getjTextFieldLogradouro().setText(funcionario.getLogradouro());
            tela.getjTextFieldComplemento().setText(funcionario.getComplemento());
            tela.getjTextFieldObs().setText(funcionario.getObs());

            if (funcionario.getStatus() == Status.ATIVO) {
                tela.getjComboBoxStatus().setSelectedIndex(0);
            } else {
                tela.getjComboBoxStatus().setSelectedIndex(1);
            }
        }
    }
}
