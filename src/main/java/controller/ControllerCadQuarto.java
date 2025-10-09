package controller;

import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Quarto;
import service.QuartoService;
import view.TelaBuscaQuarto;
import view.TelaCadastroQuarto;

public final class ControllerCadQuarto extends AbstractControllerCad<Quarto, TelaCadastroQuarto> {

    public ControllerCadQuarto(TelaCadastroQuarto view) {
        super(view, new QuartoService());
    }

    @Override
    public boolean isFormularioValido() {
        if (!validarCampoObrigatorio(view.getjTextFieldDescricao(), "Descrição")) {
            return false;
        }
        if (!validarCampoNumerico(view.getjFormattedTextFieldCapacidade(), "Capacidade de Hóspedes")) {
            return false;
        }
        if (!validarCampoNumerico(view.getjFormattedTextFieldMetragem(), "Metragem")) {
            return false;
        }
        if (!validarCampoNumerico(view.getjFormattedTextFieldAndar(), "Andar")) {
            return false;
        }
        return true;
    }

    @Override
    public Quarto construirDoFormulario() {
        Quarto quarto = new Quarto();
        quarto.setDescricao(view.getjTextFieldDescricao().getText());
        quarto.setIdentificacao(view.getjTextFieldDescricaoidentificacao().getText());
        quarto.setAndar(Integer.parseInt(view.getjFormattedTextFieldAndar().getText()));
        quarto.setMetragem(Float.parseFloat(view.getjFormattedTextFieldMetragem().getText()));
        quarto.setCapacidadeHospedes(Integer.parseInt(view.getjFormattedTextFieldCapacidade().getText()));
        quarto.setObs(view.getjTextFieldObservacao().getText());
        quarto.setFlagAnimais(view.getjCheckBoxFlagAnimais().isSelected());
        quarto.setStatus(getStatusDoFormulario());
        return quarto;
    }

    @Override
    protected void preencherFormulario(Quarto quarto) {
        view.getjTextFieldDescricao().setText(quarto.getDescricao());
        view.getjTextFieldDescricaoidentificacao().setText(quarto.getIdentificacao());
        view.getjFormattedTextFieldAndar().setText(String.valueOf(quarto.getAndar()));
        view.getjFormattedTextFieldMetragem().setText(String.valueOf(quarto.getMetragem()));
        view.getjFormattedTextFieldCapacidade().setText(String.valueOf(quarto.getCapacidadeHospedes()));
        view.getjTextFieldObservacao().setText(quarto.getObs());
        view.getjCheckBoxFlagAnimais().setSelected(quarto.isFlagAnimais());
        view.getjComboBoxStatus().setSelectedItem(quarto.getStatus() == 'A' ? "Ativo" : "Inativo");
    }

    @Override
    protected void setId(Quarto entidade, int id) {
        entidade.setId(id);
    }

    @Override
    protected JDialog criarTelaBusca() {
        return new TelaBuscaQuarto(null, true);
    }

    @Override
    protected void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback) {
        new ControllerBuscaQuarto((TelaBuscaQuarto) telaBusca, callback);
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
