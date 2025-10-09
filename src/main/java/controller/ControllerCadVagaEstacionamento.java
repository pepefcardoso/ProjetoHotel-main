package controller;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.VagaEstacionamento;
import service.VagaEstacionamentoService;
import view.TelaBuscaVaga;
import view.TelaCadastroVagaEstacionamento;

public final class ControllerCadVagaEstacionamento extends AbstractControllerCad<VagaEstacionamento, TelaCadastroVagaEstacionamento> {

    public ControllerCadVagaEstacionamento(TelaCadastroVagaEstacionamento view) {
        super(view, new VagaEstacionamentoService());
    }

    @Override
    public boolean isFormularioValido() {
        if (!validarCampoNumerico(view.getjFormattedTextFieldMetragem(), "Metragem")) {
            return false;
        }
        if (!validarCampoObrigatorio(view.getjTextFieldDescricao(), "Descrição")) {
            return false;
        }
        if (!utilities.ValidadorCampos.validarStatus(view.getjComboBoxStatus().getSelectedItem().toString())) {
            showMessage("Selecione um Status válido.");
            view.getjComboBoxStatus().requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public VagaEstacionamento construirDoFormulario() {
        VagaEstacionamento vaga = new VagaEstacionamento();
        vaga.setDescricao(view.getjTextFieldDescricao().getText());
        vaga.setObs(view.getjTextFieldObservacao().getText());
        vaga.setMetragemVaga(Float.parseFloat(view.getjFormattedTextFieldMetragem().getText()));
        vaga.setStatus(getStatusDoFormulario());
        return vaga;
    }

    @Override
    protected void preencherFormulario(VagaEstacionamento vaga) {
        view.getjTextFieldDescricao().setText(vaga.getDescricao());
        view.getjTextFieldObservacao().setText(vaga.getObs());
        view.getjFormattedTextFieldMetragem().setText(String.valueOf(vaga.getMetragemVaga()));
        view.getjComboBoxStatus().setSelectedItem(vaga.getStatus() == 'A' ? "Ativo" : "Inativo");
    }

    @Override
    protected void setId(VagaEstacionamento entidade, int id) {
        entidade.setId(id);
    }

    @Override
    protected JDialog criarTelaBusca() {
        return new TelaBuscaVaga(null, true);
    }

    @Override
    protected void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback) {
        new ControllerBuscaVagaEstacionamento((TelaBuscaVaga) telaBusca, callback);
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

    @Override
    protected void focarPrimeiroCampo() {
        view.getjFormattedTextFieldMetragem().requestFocus();
    }
}
