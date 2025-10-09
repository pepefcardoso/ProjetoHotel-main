package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import service.InterfaceService;
import utilities.Utilities;

public abstract class AbstractControllerCad<T, V extends JDialog> implements ActionListener, InterfaceControllerCad<T> {

    protected final V view;
    protected final InterfaceService<T> service;
    protected int codigo;

    protected AbstractControllerCad(V view, InterfaceService<T> service) {
        this.view = view;
        this.service = service;
        this.codigo = 0;
        inicializarView();
        initListeners();
    }

    protected void inicializarView() {
        Utilities.setAlwaysDisabled(getTextFieldId(), true);
        Utilities.setAlwaysDisabled(getComboBoxStatus(), true);
        Utilities.ativaDesativa(getPanelBotoes(), true);
        Utilities.limpaComponentes(getPanelDados(), false);
    }

    @Override
    public void initListeners() {
        getButtonNovo().addActionListener(this);
        getButtonCancelar().addActionListener(this);
        getButtonGravar().addActionListener(this);
        getButtonBuscar().addActionListener(this);
        getButtonSair().addActionListener(this);
        configurarListenersAdicionais();
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();

        if (source == getButtonNovo()) {
            handleNovo();
        } else if (source == getButtonCancelar()) {
            handleCancelar();
        } else if (source == getButtonGravar()) {
            handleGravar();
        } else if (source == getButtonBuscar()) {
            handleBuscar();
        } else if (source == getButtonSair()) {
            handleSair();
        } else {
            handleAcoesAdicionais(evento);
        }
    }

    @Override
    public void handleNovo() {
        Utilities.ativaDesativa(getPanelBotoes(), false);
        Utilities.limpaComponentes(getPanelDados(), true);
        focarPrimeiroCampo();
        getComboBoxStatus().setSelectedItem("Ativo");
        limparRelacionamentos();
    }

    @Override
    public void handleCancelar() {
        Utilities.ativaDesativa(getPanelBotoes(), true);
        Utilities.limpaComponentes(getPanelDados(), false);
        limparRelacionamentos();
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }

        T entidade = construirDoFormulario();
        boolean isNovoCadastro = getTextFieldId().getText().trim().isEmpty();

        try {
            if (isNovoCadastro) {
                service.Criar(entidade);
                showMessage("Registro criado com sucesso!");
            } else {
                setId(entidade, Integer.parseInt(getTextFieldId().getText()));
                service.Atualizar(entidade);
                showMessage("Registro atualizado com sucesso!");
            }

            Utilities.ativaDesativa(getPanelBotoes(), true);
            Utilities.limpaComponentes(getPanelDados(), false);
            limparRelacionamentos();

        } catch (SQLException ex) {
            showError(ex.getMessage());
        }
    }

    @Override
    public void handleBuscar() {
        codigo = 0;
        JDialog telaBusca = criarTelaBusca();
        criarControllerBusca(telaBusca, valor -> this.codigo = valor);
        telaBusca.setVisible(true);

        if (codigo != 0) {
            carregarEntidadeParaEdicao(codigo);
        }
    }

    protected void carregarEntidadeParaEdicao(int codigo) {
        Utilities.ativaDesativa(getPanelBotoes(), false);
        Utilities.limpaComponentes(getPanelDados(), true);
        getTextFieldId().setText(String.valueOf(codigo));

        try {
            T entidade = service.Carregar(codigo);
            preencherFormulario(entidade);
            focarPrimeiroCampo();
        } catch (SQLException ex) {
            showError(ex.getMessage());
            handleCancelar();
        }
    }

    @Override
    public void handleSair() {
        view.dispose();
    }

    protected void showMessage(String message) {
        JOptionPane.showMessageDialog(view, message);
    }

    protected void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    protected boolean validarCampoObrigatorio(JTextField campo, String nomeCampo) {
        if (!utilities.ValidadorCampos.validarCampoTexto(campo.getText())) {
            showMessage("O campo " + nomeCampo + " é obrigatório.");
            campo.requestFocus();
            return false;
        }
        return true;
    }

    protected boolean validarCampoNumerico(JTextField campo, String nomeCampo) {
        if (!utilities.ValidadorCampos.validarCampoNumero(campo.getText())) {
            showMessage("O campo " + nomeCampo + " é inválido.");
            campo.requestFocus();
            return false;
        }
        return true;
    }

    protected char getStatusDoFormulario() {
        Object statusSelecionado = getComboBoxStatus().getSelectedItem();
        return statusSelecionado != null && statusSelecionado.equals("Ativo") ? 'A' : 'I';
    }

    protected abstract JTextField getTextFieldId();

    protected abstract JComboBox<String> getComboBoxStatus();

    protected abstract JPanel getPanelBotoes();

    protected abstract JPanel getPanelDados();

    protected abstract JButton getButtonNovo();

    protected abstract JButton getButtonCancelar();

    protected abstract JButton getButtonGravar();

    protected abstract JButton getButtonBuscar();

    protected abstract JButton getButtonSair();

    protected abstract void focarPrimeiroCampo();

    protected abstract void preencherFormulario(T entidade);

    protected abstract void setId(T entidade, int id);

    protected abstract JDialog criarTelaBusca();

    protected abstract void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback);

    protected void configurarListenersAdicionais() {
        // Implementação opcional para subclasses
    }

    protected void handleAcoesAdicionais(ActionEvent evento) {
        // Implementação opcional para subclasses
    }

    protected void limparRelacionamentos() {
        // Implementação opcional para subclasses com relacionamentos
    }
}
