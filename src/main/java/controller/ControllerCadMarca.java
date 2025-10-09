package controller;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Marca;
import service.MarcaService;
import view.TelaBuscaMarca;
import view.TelaCadastroMarca;

public final class ControllerCadMarca extends AbstractControllerCad<Marca, TelaCadastroMarca> {

    public ControllerCadMarca(TelaCadastroMarca view) {
        super(view, new MarcaService());
    }

    @Override
    public boolean isFormularioValido() {
        return validarCampoObrigatorio(view.getjTextFieldDescricao(), "Descrição");
    }

    @Override
    public Marca construirDoFormulario() {
        Marca marca = new Marca();
        marca.setDescricao(view.getjTextFieldDescricao().getText());
        marca.setStatus(getStatusDoFormulario());
        return marca;
    }

    @Override
    protected void preencherFormulario(Marca marca) {
        view.getjTextFieldDescricao().setText(marca.getDescricao());
        view.getjComboBoxStatus().setSelectedItem(marca.getStatus() == 'A' ? "Ativo" : "Inativo");
    }

    @Override
    protected void setId(Marca entidade, int id) {
        entidade.setId(id);
    }

    @Override
    protected JDialog criarTelaBusca() {
        return new TelaBuscaMarca(null, true);
    }

    @Override
    protected void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback) {
        new ControllerBuscaMarca((TelaBuscaMarca) telaBusca, callback);
    }

    @Override
    protected void focarPrimeiroCampo() {
        view.getjTextFieldDescricao().requestFocus();
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
