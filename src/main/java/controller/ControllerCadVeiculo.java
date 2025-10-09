package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Fornecedor;
import model.Funcionario;
import model.Hospede;
import model.Modelo;
import model.Pessoa;
import model.Veiculo;
import service.FornecedorService;
import service.FuncionarioService;
import service.HospedeService;
import service.ModeloService;
import service.VeiculoService;
import utilities.Utilities;
import view.TelaBuscaFornecedor;
import view.TelaBuscaFuncionario;
import view.TelaBuscaHospede;
import view.TelaBuscaModelo;
import view.TelaBuscaVeiculo;
import view.TelaCadastroVeiculo;

public final class ControllerCadVeiculo extends AbstractControllerCad<Veiculo, TelaCadastroVeiculo> {

    private Modelo modeloRelacionado;
    private Pessoa proprietarioRelacionado;
    private String tipoProprietarioSelecionado;
    private int codigoRelacionado;

    public ControllerCadVeiculo(TelaCadastroVeiculo view) {
        super(view, new VeiculoService());
        this.preencherTiposProprietario();
    }

    @Override
    protected void inicializarView() {
        super.inicializarView();
        Utilities.setAlwaysDisabled(view.getjFormattedTextFieldModelo(), true);
        Utilities.setAlwaysDisabled(view.getjFormattedTextFieldProprietario(), true);
    }

    @Override
    protected void configurarListenersAdicionais() {
        view.getjButtonRelacionarModelo().addActionListener(this);
        view.getjButtonRelacionarProprietario().addActionListener(this);
        view.getjComboBoxTipoProprietario().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                handleTipoProprietarioChange();
            }
        });
    }

    @Override
    protected void handleAcoesAdicionais(ActionEvent evento) {
        if (evento.getSource() == view.getjButtonRelacionarModelo()) {
            handleRelacionarModelo();
        } else if (evento.getSource() == view.getjButtonRelacionarProprietario()) {
            handleRelacionarProprietario();
        }
    }

    @Override
    public boolean isFormularioValido() {
        if (!validarCampoObrigatorio(view.getjTextFieldPlaca(), "Placa")) {
            return false;
        }
        if (!utilities.ValidadorCampos.validarPlaca(view.getjTextFieldPlaca().getText())) {
            showMessage("O campo Placa é inválido (deve ter 7 caracteres).");
            view.getjTextFieldPlaca().requestFocus();
            return false;
        }
        if (!validarCampoObrigatorio(view.getjTextFieldCor(), "Cor")) {
            return false;
        }
        if (modeloRelacionado == null) {
            showMessage("É obrigatório selecionar um Modelo para o veículo.");
            view.getjButtonRelacionarModelo().requestFocus();
            return false;
        }
        if (proprietarioRelacionado == null) {
            showMessage("É obrigatório selecionar um Proprietário para o veículo.");
            view.getjButtonRelacionarProprietario().requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public Veiculo construirDoFormulario() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(view.getjTextFieldPlaca().getText());
        veiculo.setCor(view.getjTextFieldCor().getText());
        veiculo.setStatus(getStatusDoFormulario());
        veiculo.setModelo(modeloRelacionado);
        veiculo.setProprietario(proprietarioRelacionado);
        return veiculo;
    }

    @Override
    protected void preencherFormulario(Veiculo veiculo) {
        view.getjTextFieldPlaca().setText(veiculo.getPlaca());
        view.getjTextFieldCor().setText(veiculo.getCor());
        view.getjComboBoxStatus().setSelectedItem(veiculo.getStatus() == 'A' ? "Ativo" : "Inativo");

        this.modeloRelacionado = veiculo.getModelo();
        view.getjFormattedTextFieldModelo().setText(getModeloFormat(this.modeloRelacionado));

        this.proprietarioRelacionado = veiculo.getProprietario();
        if (this.proprietarioRelacionado instanceof Hospede) {
            view.getjComboBoxTipoProprietario().setSelectedItem(Hospede.TIPO);
        } else if (this.proprietarioRelacionado instanceof Funcionario) {
            view.getjComboBoxTipoProprietario().setSelectedItem(Funcionario.TIPO);
        } else if (this.proprietarioRelacionado instanceof Fornecedor) {
            view.getjComboBoxTipoProprietario().setSelectedItem(Fornecedor.TIPO);
        }
        view.getjFormattedTextFieldProprietario().setText(getProprietarioFormat(this.proprietarioRelacionado));
    }

    @Override
    protected void limparRelacionamentos() {
        this.modeloRelacionado = null;
        this.proprietarioRelacionado = null;
        this.tipoProprietarioSelecionado = null;
        view.getjComboBoxTipoProprietario().setSelectedIndex(-1);
        view.getjFormattedTextFieldProprietario().setText("");
        view.getjFormattedTextFieldModelo().setText("");
        view.getjLabelProprietario().setText("Proprietário");
    }

    private void handleRelacionarModelo() {
        this.codigoRelacionado = 0;
        TelaBuscaModelo telaBusca = new TelaBuscaModelo(null, true);
        new ControllerBuscaModelo(telaBusca, val -> this.codigoRelacionado = val);
        telaBusca.setVisible(true);

        if (this.codigoRelacionado != 0) {
            try {
                this.modeloRelacionado = new ModeloService().Carregar(this.codigoRelacionado);
                view.getjFormattedTextFieldModelo().setText(getModeloFormat(this.modeloRelacionado));
            } catch (SQLException ex) {
                showError(ex.getMessage());
            }
        }
    }

    private void handleRelacionarProprietario() {
        if (this.tipoProprietarioSelecionado == null) {
            showMessage("Selecione o Tipo de Proprietário antes de buscar.");
            view.getjComboBoxTipoProprietario().requestFocus();
            return;
        }

        this.codigoRelacionado = 0;

        switch (this.tipoProprietarioSelecionado) {
            case Hospede.TIPO:
                abrirBuscaProprietario(new TelaBuscaHospede(null, true));
                break;
            case Funcionario.TIPO:
                abrirBuscaProprietario(new TelaBuscaFuncionario(null, true));
                break;
            case Fornecedor.TIPO:
                abrirBuscaProprietario(new TelaBuscaFornecedor(null, true));
                break;
        }

        if (this.codigoRelacionado != 0) {
            try {
                switch (this.tipoProprietarioSelecionado) {
                    case Hospede.TIPO:
                        this.proprietarioRelacionado = new HospedeService().Carregar(this.codigoRelacionado);
                        break;
                    case Funcionario.TIPO:
                        this.proprietarioRelacionado = new FuncionarioService().Carregar(this.codigoRelacionado);
                        break;
                    case Fornecedor.TIPO:
                        this.proprietarioRelacionado = new FornecedorService().Carregar(this.codigoRelacionado);
                        break;
                }
                view.getjFormattedTextFieldProprietario().setText(getProprietarioFormat(this.proprietarioRelacionado));
            } catch (SQLException ex) {
                showError(ex.getMessage());
            }
        }
    }

    private void abrirBuscaProprietario(JDialog telaBusca) {
        if (telaBusca instanceof TelaBuscaHospede) {
            new ControllerBuscaHospede((TelaBuscaHospede) telaBusca, val -> this.codigoRelacionado = val);
        } else if (telaBusca instanceof TelaBuscaFuncionario) {
            new ControllerBuscaFuncionario((TelaBuscaFuncionario) telaBusca, val -> this.codigoRelacionado = val);
        } else if (telaBusca instanceof TelaBuscaFornecedor) {
            new ControllerBuscaFornecedor((TelaBuscaFornecedor) telaBusca, val -> this.codigoRelacionado = val);
        }
        telaBusca.setVisible(true);
    }

    private void preencherTiposProprietario() {
        view.getjComboBoxTipoProprietario().removeAllItems();
        view.getjComboBoxTipoProprietario().addItem(Hospede.TIPO);
        view.getjComboBoxTipoProprietario().addItem(Funcionario.TIPO);
        view.getjComboBoxTipoProprietario().addItem(Fornecedor.TIPO);
        view.getjComboBoxTipoProprietario().setSelectedIndex(-1);
    }

    private void handleTipoProprietarioChange() {
        Object selectedItem = view.getjComboBoxTipoProprietario().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        String tipoSelecionado = selectedItem.toString();
        if (!tipoSelecionado.equals(this.tipoProprietarioSelecionado)) {
            this.proprietarioRelacionado = null;
            view.getjFormattedTextFieldProprietario().setText("");
            this.tipoProprietarioSelecionado = tipoSelecionado;
            view.getjLabelProprietario().setText(this.tipoProprietarioSelecionado);
        }
    }

    private String getModeloFormat(Modelo modelo) {
        return (modelo == null) ? "" : String.format("%d - %s", modelo.getId(), modelo.getDescricao());
    }

    private String getProprietarioFormat(Pessoa proprietario) {
        return (proprietario == null) ? "" : String.format("%d - %s", proprietario.getId(), proprietario.getNome());
    }

    @Override
    protected void setId(Veiculo entidade, int id) {
        entidade.setId(id);
    }

    @Override
    protected JDialog criarTelaBusca() {
        return new TelaBuscaVeiculo(null, true);
    }

    @Override
    protected void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback) {
        new ControllerBuscaVeiculo((TelaBuscaVeiculo) telaBusca, callback);
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
        view.getjTextFieldPlaca().requestFocus();
    }
}
