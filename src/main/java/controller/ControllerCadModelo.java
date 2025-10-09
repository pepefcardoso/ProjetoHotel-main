package controller;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Marca;
import model.Modelo;
import service.MarcaService;
import service.ModeloService;
import view.TelaBuscaMarca;
import view.TelaBuscaModelo;
import view.TelaCadastroModelo;

public final class ControllerCadModelo extends AbstractControllerCad<Modelo, TelaCadastroModelo> {

    private Marca marcaRelacionada;

    public ControllerCadModelo(TelaCadastroModelo view) {
        super(view, new ModeloService());
        view.getjFormattedTextFieldMarca().putClientProperty(utilities.Utilities.ALWAYS_DISABLED, true);
    }

    @Override
    protected void configurarListenersAdicionais() {
        view.getjButtonRelacionarMarca().addActionListener(this);
    }

    @Override
    protected void handleAcoesAdicionais(ActionEvent evento) {
        if (evento.getSource() == view.getjButtonRelacionarMarca()) {
            handleRelacionarMarca();
        }
    }

    @Override
    protected void limparRelacionamentos() {
        this.marcaRelacionada = null;
        view.getjFormattedTextFieldMarca().setText("");
    }

    @Override
    public boolean isFormularioValido() {
        if (!validarCampoObrigatorio(view.getjTextFieldDescricao(), "Descrição")) {
            return false;
        }

        if (this.marcaRelacionada == null) {
            showMessage("É necessário relacionar uma marca ao modelo.");
            view.getjButtonRelacionarMarca().requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public Modelo construirDoFormulario() {
        Modelo modelo = new Modelo();
        modelo.setDescricao(view.getjTextFieldDescricao().getText());
        modelo.setStatus(getStatusDoFormulario());
        modelo.setMarca(this.marcaRelacionada);
        return modelo;
    }

    @Override
    protected void preencherFormulario(Modelo modelo) {
        view.getjTextFieldDescricao().setText(modelo.getDescricao());
        view.getjComboBoxStatus().setSelectedItem(modelo.getStatus() == 'A' ? "Ativo" : "Inativo");

        this.marcaRelacionada = modelo.getMarca();
        view.getjFormattedTextFieldMarca().setText(getMarcaFormat(this.marcaRelacionada));
    }

    @Override
    protected void setId(Modelo entidade, int id) {
        entidade.setId(id);
    }

    @Override
    protected JDialog criarTelaBusca() {
        return new TelaBuscaModelo(null, true);
    }

    @Override
    protected void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback) {
        new ControllerBuscaModelo((TelaBuscaModelo) telaBusca, callback);
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

    private void handleRelacionarMarca() {
        int codigoMarca = 0;
        TelaBuscaMarca telaBuscaMarca = new TelaBuscaMarca(null, true);
        ControllerBuscaMarca controllerBuscaMarca = new ControllerBuscaMarca(telaBuscaMarca, valor -> {
            if (valor != 0) {
                try {
                    Marca marca = new MarcaService().Carregar(valor);
                    this.marcaRelacionada = marca;
                    view.getjFormattedTextFieldMarca().setText(getMarcaFormat(this.marcaRelacionada));
                } catch (SQLException ex) {
                    showError(ex.getMessage());
                }
            }
        });
        telaBuscaMarca.setVisible(true);
    }

    private String getMarcaFormat(Marca marca) {
        if (marca == null) {
            return "";
        }
        return String.format("%d - %s", marca.getId(), marca.getDescricao());
    }
}
