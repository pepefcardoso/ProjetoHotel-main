package controller;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Funcionario;
import service.FuncionarioService;
import utilities.Utilities;
import view.TelaBuscaFuncionario;
import view.TelaCadastroFuncionario;

public final class ControllerCadFuncionario extends AbstractControllerCad<Funcionario, TelaCadastroFuncionario> {

    public ControllerCadFuncionario(TelaCadastroFuncionario view) {
        super(view, new FuncionarioService());
    }

    @Override
    protected void inicializarView() {
        super.inicializarView();
        Utilities.setAlwaysDisabled(view.getjFormattedTextFieldDataCadastro(), true);
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldCep());
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldCpf());
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldFone1());
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldFone2());
    }

    @Override
    public void handleNovo() {
        super.handleNovo();
        view.getjFormattedTextFieldDataCadastro().setText(Utilities.getDataHoje());
    }

    @Override
    public boolean isFormularioValido() {
        if (!utilities.ValidadorCampos.validarCampoUsuario(view.getjTextFieldUsuario().getText())) {
            showMessage("O atributo Usuário é Inválido.\n(De 5 a 20 caracteres. Apenas letras, números, '.' e '_' são permitidos)");
            view.getjTextFieldUsuario().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarSenha(view.getjPasswordFieldSenha().getPassword())) {
            showMessage("A Senha é obrigatória.");
            view.getjPasswordFieldSenha().requestFocus();
            return false;
        }
        if (!validarCampoObrigatorio(view.getjTextFieldNome(), "Nome")) {
            return false;
        }
        if (!utilities.ValidadorCampos.validarSexo(view.getjComboBoxSexo().getSelectedItem())) {
            showMessage("O atributo Sexo é Inválido.");
            view.getjComboBoxSexo().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCpf(view.getjFormattedTextFieldCpf().getText())) {
            showMessage("O Atributo CPF é Inválido.");
            view.getjFormattedTextFieldCpf().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarFone(view.getjFormattedTextFieldFone1().getText())) {
            showMessage("O campo Fone 1 é inválido.");
            view.getjFormattedTextFieldFone1().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCampoEmail(view.getjTextFieldEmail().getText())) {
            showMessage("O atributo Email é Inválido.");
            view.getjTextFieldEmail().requestFocus();
            return false;
        }
        if (!utilities.ValidadorCampos.validarCep(view.getjFormattedTextFieldCep().getText())) {
            showMessage("O CEP é obrigatório.");
            view.getjFormattedTextFieldCep().requestFocus();
            return false;
        }
        if (!validarCampoObrigatorio(view.getjTextFieldCidade(), "Cidade")) {
            return false;
        }
        if (!validarCampoObrigatorio(view.getjTextFieldBairro(), "Bairro")) {
            return false;
        }
        if (!validarCampoObrigatorio(view.getjTextFieldLogradouro(), "Logradouro")) {
            return false;
        }
        return true;
    }

    @Override
    public Funcionario construirDoFormulario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(view.getjTextFieldNome().getText());
        funcionario.setCpf(Utilities.apenasNumeros(view.getjFormattedTextFieldCpf().getText()));
        funcionario.setRg(view.getjTextFieldRg().getText());
        funcionario.setObs(view.getjTextFieldObs().getText());
        Object sexoSelecionado = view.getjComboBoxSexo().getSelectedItem();
        funcionario.setSexo(sexoSelecionado != null && sexoSelecionado.equals("Masculino") ? 'M' : 'F');
        funcionario.setStatus(getStatusDoFormulario());
        funcionario.setUsuario(view.getjTextFieldUsuario().getText());
        funcionario.setSenha(new String(view.getjPasswordFieldSenha().getPassword()));
        funcionario.setFone1(Utilities.apenasNumeros(view.getjFormattedTextFieldFone1().getText()));
        funcionario.setFone2(Utilities.apenasNumeros(view.getjFormattedTextFieldFone2().getText()));
        funcionario.setEmail(view.getjTextFieldEmail().getText());
        funcionario.setCep(Utilities.apenasNumeros(view.getjFormattedTextFieldCep().getText()));
        funcionario.setBairro(view.getjTextFieldBairro().getText());
        funcionario.setCidade(view.getjTextFieldCidade().getText());
        funcionario.setLogradouro(view.getjTextFieldLogradouro().getText());
        funcionario.setComplemento(view.getjTextFieldComplemento().getText());
        return funcionario;
    }

    @Override
    protected void preencherFormulario(Funcionario funcionario) {
        view.getjFormattedTextFieldDataCadastro().setText(Utilities.formatarDataFromSqlData(funcionario.getDataCadastro()));
        view.getjTextFieldNome().setText(funcionario.getNome());
        view.getjFormattedTextFieldCpf().setText(funcionario.getCpf());
        view.getjTextFieldRg().setText(funcionario.getRg());
        view.getjTextFieldObs().setText(funcionario.getObs());
        view.getjComboBoxSexo().setSelectedItem(funcionario.getSexo() == 'M' ? "Masculino" : "Feminino");
        view.getjComboBoxStatus().setSelectedItem(funcionario.getStatus() == 'A' ? "Ativo" : "Inativo");
        view.getjTextFieldUsuario().setText(funcionario.getUsuario());
        view.getjPasswordFieldSenha().setText(funcionario.getSenha());
        view.getjFormattedTextFieldFone1().setText(Utilities.formatarFone(funcionario.getFone1()));
        view.getjFormattedTextFieldFone2().setText(Utilities.formatarFone(funcionario.getFone2()));
        view.getjTextFieldEmail().setText(funcionario.getEmail());
        view.getjFormattedTextFieldCep().setText(Utilities.formatarCep(funcionario.getCep()));
        view.getjTextFieldBairro().setText(funcionario.getBairro());
        view.getjTextFieldCidade().setText(funcionario.getCidade());
        view.getjTextFieldLogradouro().setText(funcionario.getLogradouro());
        view.getjTextFieldComplemento().setText(funcionario.getComplemento());
    }

    @Override
    protected void setId(Funcionario entidade, int id) {
        entidade.setId(id);
    }

    @Override
    protected JDialog criarTelaBusca() {
        return new TelaBuscaFuncionario(null, true);
    }

    @Override
    protected void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback) {
        new ControllerBuscaFuncionario((TelaBuscaFuncionario) telaBusca, callback);
    }

    @Override
    protected void focarPrimeiroCampo() {
        view.getjTextFieldUsuario().requestFocus();
    }

    @Override
    protected JTextField getTextFieldId() {
        return view.getjTextFieldId();
    }

    @Override
    protected JComboBox<String> getComboBoxStatus() {
        return view.getjComboBoxStatus();
    }

    @Override
    protected JPanel getPanelBotoes() {
        return view.getjPanelBotoes();
    }

    @Override
    protected JPanel getPanelDados() {
        return view.getjPanelDados();
    }

    @Override
    protected JButton getButtonNovo() {
        return view.getjButtonNovo();
    }

    @Override
    protected JButton getButtonCancelar() {
        return view.getjButtonCancelar();
    }

    @Override
    protected JButton getButtonGravar() {
        return view.getjButtonGravar();
    }

    @Override
    protected JButton getButtonBuscar() {
        return view.getjButtonBuscar();
    }

    @Override
    protected JButton getButtonSair() {
        return view.getjButtonSair();
    }
}
