package controller;

import javax.swing.JOptionPane;
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
        TelaCadastroFuncionario telaFuncionario = (TelaCadastroFuncionario) this.tela;

        if (!telaFuncionario.getjTextFieldId().getText().isEmpty()) {
            try {
                funcionario.setId(Integer.parseInt(telaFuncionario.getjTextFieldId().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(tela, "O ID do funcionário é inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro de validação no ID do Funcionário.");
            }
        }

        funcionario.setNome(telaFuncionario.getjTextFieldDescricao().getText());
        funcionario.setUsuario(telaFuncionario.getjTextFieldDescricao().getText()); // Assumindo que o nome é o usuário
        funcionario.setSenha(new String(telaFuncionario.getjPasswordFieldSenha().getPassword()));
        funcionario.setRg(telaFuncionario.getjTextFieldRg().getText());
        funcionario.setCpf(telaFuncionario.getjFormattedTextFieldCpf().getText());
        funcionario.setFone1(telaFuncionario.getjFormattedTextFieldFone1().getText());
        funcionario.setFone2(telaFuncionario.getjFormattedTextFieldFone2().getText());
        funcionario.setEmail(telaFuncionario.getjTextFieldEmail().getText());
        funcionario.setCep(telaFuncionario.getjFormattedTextFieldCep().getText());
        funcionario.setCidade(telaFuncionario.getjTextFieldCidade().getText());
        funcionario.setBairro(telaFuncionario.getjTextFieldBairro().getText());
        funcionario.setLogradouro(telaFuncionario.getjTextFieldLogradouro().getText());
        funcionario.setComplemento(telaFuncionario.getjTextFieldComplemento().getText());
        funcionario.setObs(telaFuncionario.getjTextFieldObs().getText());

        if (telaFuncionario.getjComboBoxStatus().getSelectedIndex() == 0) {
            funcionario.setStatus(Status.ATIVO);
        } else {
            funcionario.setStatus(Status.INATIVO);
        }

        System.out.println("Funcionário a ser salvo: " + funcionario);
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
