package controller;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Servico;
import service.ServicoService;
import view.TelaBuscaServico;
import view.TelaCadastroServico;

public final class ControllerCadServico extends AbstractControllerCad<Servico, TelaCadastroServico> {

    public ControllerCadServico(TelaCadastroServico view) {
        super(view, new ServicoService());
    }

    @Override
    public boolean isFormularioValido() {
        return validarCampoObrigatorio(view.getjTextFieldDescricao(), "Descrição");
    }

    @Override
    public Servico construirDoFormulario() {
        Servico servico = new Servico();
        servico.setDescricao(view.getjTextFieldDescricao().getText());
        servico.setObs(view.getjTextFieldObservacao().getText());
        servico.setStatus(getStatusDoFormulario());
        return servico;
    }

    @Override
    protected void preencherFormulario(Servico servico) {
        view.getjTextFieldDescricao().setText(servico.getDescricao());
        view.getjTextFieldObservacao().setText(servico.getObs());
        view.getjComboBoxStatus().setSelectedItem(
                servico.getStatus() == 'A' ? "Ativo" : "Inativo"
        );
    }

    @Override
    protected void setId(Servico servico, int id) {
        servico.setId(id);
    }

    @Override
    protected JDialog criarTelaBusca() {
        return new TelaBuscaServico(null, true);
    }

    @Override
    protected void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback) {
        ControllerBuscaServico controller = new ControllerBuscaServico((TelaBuscaServico) telaBusca, callback);
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
        view.getjTextFieldDescricao().requestFocus();
    }
}
