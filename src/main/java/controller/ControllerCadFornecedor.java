package controller;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Fornecedor;
import service.FornecedorService;
import utilities.Utilities;
import view.TelaBuscaFornecedor;
import view.TelaCadastroFornecedor;

public final class ControllerCadFornecedor extends AbstractControllerCad<Fornecedor, TelaCadastroFornecedor> {

    public ControllerCadFornecedor(TelaCadastroFornecedor view) {
        super(view, new FornecedorService());
    }

    @Override
    protected void inicializarView() {
        super.inicializarView();
        Utilities.setAlwaysDisabled(view.getjFormattedTextFieldDataCadastro(), true);
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldCpf());
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldCep());
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldCnpj());
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
        if (!validarCampoObrigatorio(view.getjTextFieldNomeFantasia(), "Nome Fantasia")) {
            return false;
        }

        if (!utilities.ValidadorCampos.validarSexo(view.getjComboBoxSexo().getSelectedItem())) {
            showMessage("Selecione um Sexo válido.");
            view.getjComboBoxSexo().requestFocus();
            return false;
        }

        boolean cpfPreenchido = Utilities.apenasNumeros(view.getjFormattedTextFieldCpf().getText()).length() > 0;
        boolean cnpjPreenchido = Utilities.apenasNumeros(view.getjFormattedTextFieldCnpj().getText()).length() > 0;

        if (!cpfPreenchido && !cnpjPreenchido) {
            showMessage("Preencha ao menos um dos campos: CPF ou CNPJ.");
            view.getjFormattedTextFieldCpf().requestFocus();
            return false;
        }

        if (cpfPreenchido && !utilities.ValidadorCampos.validarCpf(view.getjFormattedTextFieldCpf().getText())) {
            showMessage("O campo CPF é inválido.");
            view.getjFormattedTextFieldCpf().requestFocus();
            return false;
        }

        if (cnpjPreenchido && !utilities.ValidadorCampos.validarCnpj(view.getjFormattedTextFieldCnpj().getText())) {
            showMessage("O campo CNPJ é inválido.");
            view.getjFormattedTextFieldCnpj().requestFocus();
            return false;
        }

        if (!utilities.ValidadorCampos.validarFone(view.getjFormattedTextFieldFone1().getText())) {
            showMessage("O campo Fone 1 é obrigatório e inválido.");
            view.getjFormattedTextFieldFone1().requestFocus();
            return false;
        }

        if (!utilities.ValidadorCampos.validarCampoEmail(view.getjTextFieldEmail().getText())) {
            showMessage("O campo Email é inválido.");
            view.getjTextFieldEmail().requestFocus();
            return false;
        }

        if (!utilities.ValidadorCampos.validarCep(view.getjFormattedTextFieldCep().getText())) {
            showMessage("O campo CEP é obrigatório.");
            view.getjFormattedTextFieldCep().requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public Fornecedor construirDoFormulario() {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setNome(view.getjTextFieldNomeFantasia().getText());
        fornecedor.setRazaoSocial(view.getjTextFieldRazaoSocial().getText());
        fornecedor.setRg(view.getjTextFieldRg().getText());
        fornecedor.setInscricaoEstadual(view.getjTextFieldInscricaoEstadual().getText());
        fornecedor.setEmail(view.getjTextFieldEmail().getText());
        fornecedor.setCidade(view.getjTextFieldCidade().getText());
        fornecedor.setBairro(view.getjTextFieldBairro().getText());
        fornecedor.setLogradouro(view.getjTextFieldLogradouro().getText());
        fornecedor.setComplemento(view.getjTextFieldComplemento().getText());
        fornecedor.setObs(view.getjTextFieldObs().getText());
        fornecedor.setContato(view.getjTextFieldContato().getText());

        fornecedor.setCpf(Utilities.apenasNumeros(view.getjFormattedTextFieldCpf().getText()));
        fornecedor.setCnpj(Utilities.apenasNumeros(view.getjFormattedTextFieldCnpj().getText()));
        fornecedor.setFone1(Utilities.apenasNumeros(view.getjFormattedTextFieldFone1().getText()));
        fornecedor.setFone2(Utilities.apenasNumeros(view.getjFormattedTextFieldFone2().getText()));
        fornecedor.setCep(Utilities.apenasNumeros(view.getjFormattedTextFieldCep().getText()));

        Object sexoSelecionado = view.getjComboBoxSexo().getSelectedItem();
        fornecedor.setSexo(sexoSelecionado != null && sexoSelecionado.equals("Masculino") ? 'M' : 'F');

        fornecedor.setStatus(getStatusDoFormulario());

        return fornecedor;
    }

    @Override
    protected void preencherFormulario(Fornecedor fornecedor) {
        view.getjFormattedTextFieldDataCadastro().setText(Utilities.formatarDataFromSqlData(fornecedor.getDataCadastro()));
        view.getjTextFieldNomeFantasia().setText(fornecedor.getNome());
        view.getjTextFieldRazaoSocial().setText(fornecedor.getRazaoSocial());
        view.getjFormattedTextFieldCpf().setText(Utilities.formatarCpf(fornecedor.getCpf()));
        view.getjFormattedTextFieldCnpj().setText(Utilities.formatarCnpj(fornecedor.getCnpj()));
        view.getjTextFieldRg().setText(fornecedor.getRg());
        view.getjTextFieldInscricaoEstadual().setText(fornecedor.getInscricaoEstadual());
        view.getjFormattedTextFieldFone1().setText(Utilities.formatarFone(fornecedor.getFone1()));
        view.getjFormattedTextFieldFone2().setText(Utilities.formatarFone(fornecedor.getFone2()));
        view.getjTextFieldEmail().setText(fornecedor.getEmail());
        view.getjFormattedTextFieldCep().setText(Utilities.formatarCep(fornecedor.getCep()));
        view.getjTextFieldBairro().setText(fornecedor.getBairro());
        view.getjTextFieldCidade().setText(fornecedor.getCidade());
        view.getjTextFieldLogradouro().setText(fornecedor.getLogradouro());
        view.getjTextFieldComplemento().setText(fornecedor.getComplemento());
        view.getjTextFieldObs().setText(fornecedor.getObs());
        view.getjTextFieldContato().setText(fornecedor.getContato());
        view.getjComboBoxStatus().setSelectedItem(fornecedor.getStatus() == 'A' ? "Ativo" : "Inativo");
        view.getjComboBoxSexo().setSelectedItem(fornecedor.getSexo() == 'M' ? "Masculino" : "Feminino");
    }

    @Override
    protected void setId(Fornecedor entidade, int id) {
        entidade.setId(id);
    }

    @Override
    protected JDialog criarTelaBusca() {
        return new TelaBuscaFornecedor(null, true);
    }

    @Override
    protected void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback) {
        new ControllerBuscaFornecedor((TelaBuscaFornecedor) telaBusca, callback);
    }

    @Override
    protected void focarPrimeiroCampo() {
        view.getjTextFieldNomeFantasia().requestFocus();
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
