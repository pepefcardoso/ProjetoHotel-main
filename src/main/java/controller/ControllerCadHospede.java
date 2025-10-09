package controller;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Hospede;
import service.HospedeService;
import utilities.Utilities;
import view.TelaBuscaHospede;
import view.TelaCadastroHospede;

public final class ControllerCadHospede extends AbstractControllerCad<Hospede, TelaCadastroHospede> {

    public ControllerCadHospede(TelaCadastroHospede view) {
        super(view, new HospedeService());
    }

    @Override
    protected void inicializarView() {
        super.inicializarView();
        Utilities.setAlwaysDisabled(view.getjFormattedTextFieldDataCadastro(), true);
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldCpf());
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldCnpj());
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldFone1());
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldFone2());
        Utilities.permiteLimparFormattedField(view.getjFormattedTextFieldCep());
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

        // Adicione outras validações conforme necessário
        return true;
    }

    @Override
    public Hospede construirDoFormulario() {
        Hospede hospede = new Hospede();
        hospede.setNome(view.getjTextFieldNomeFantasia().getText());
        hospede.setRazaoSocial(view.getjTextFieldRazaoSocial().getText());
        hospede.setCpf(Utilities.apenasNumeros(view.getjFormattedTextFieldCpf().getText()));
        hospede.setCnpj(Utilities.apenasNumeros(view.getjFormattedTextFieldCnpj().getText()));
        hospede.setRg(view.getjTextFieldRg().getText());
        hospede.setInscricaoEstadual(view.getjTextFieldInscricaoEstadual().getText());
        hospede.setFone1(Utilities.apenasNumeros(view.getjFormattedTextFieldFone1().getText()));
        hospede.setFone2(Utilities.apenasNumeros(view.getjFormattedTextFieldFone2().getText()));
        hospede.setEmail(view.getjTextFieldEmail().getText());
        hospede.setCep(Utilities.apenasNumeros(view.getjFormattedTextFieldCep().getText()));
        hospede.setBairro(view.getjTextFieldBairro().getText());
        hospede.setCidade(view.getjTextFieldCidade().getText());
        hospede.setLogradouro(view.getjTextFieldLogradouro().getText());
        hospede.setComplemento(view.getjTextFieldComplemento().getText());
        hospede.setContato(view.getjTextFieldContato().getText());
        hospede.setObs(view.getjTextFieldObs().getText());

        Object sexoSelecionado = view.getjComboBoxSexo().getSelectedItem();
        hospede.setSexo(sexoSelecionado != null && sexoSelecionado.equals("Masculino") ? 'M' : 'F');

        hospede.setStatus(getStatusDoFormulario());

        return hospede;
    }

    @Override
    protected void preencherFormulario(Hospede hospede) {
        view.getjFormattedTextFieldDataCadastro().setText(Utilities.formatarDataFromSqlData(hospede.getDataCadastro()));
        view.getjTextFieldNomeFantasia().setText(hospede.getNome());
        view.getjTextFieldRazaoSocial().setText(hospede.getRazaoSocial());
        view.getjFormattedTextFieldCpf().setText(Utilities.formatarCpf(hospede.getCpf()));
        view.getjFormattedTextFieldCnpj().setText(Utilities.formatarCnpj(hospede.getCnpj()));
        view.getjTextFieldRg().setText(hospede.getRg());
        view.getjTextFieldInscricaoEstadual().setText(hospede.getInscricaoEstadual());
        view.getjFormattedTextFieldFone1().setText(Utilities.formatarFone(hospede.getFone1()));
        view.getjFormattedTextFieldFone2().setText(Utilities.formatarFone(hospede.getFone2()));
        view.getjTextFieldEmail().setText(hospede.getEmail());
        view.getjFormattedTextFieldCep().setText(Utilities.formatarCep(hospede.getCep()));
        view.getjTextFieldBairro().setText(hospede.getBairro());
        view.getjTextFieldCidade().setText(hospede.getCidade());
        view.getjTextFieldLogradouro().setText(hospede.getLogradouro());
        view.getjTextFieldComplemento().setText(hospede.getComplemento());
        view.getjTextFieldContato().setText(hospede.getContato());
        view.getjTextFieldObs().setText(hospede.getObs());
        view.getjComboBoxSexo().setSelectedItem(hospede.getSexo() == 'M' ? "Masculino" : "Feminino");
        view.getjComboBoxStatus().setSelectedItem(hospede.getStatus() == 'A' ? "Ativo" : "Inativo");
    }

    @Override
    protected void setId(Hospede entidade, int id) {
        entidade.setId(id);
    }

    @Override
    protected JDialog criarTelaBusca() {
        return new TelaBuscaHospede(null, true);
    }

    @Override
    protected void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback) {
        new ControllerBuscaHospede((TelaBuscaHospede) telaBusca, callback);
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
