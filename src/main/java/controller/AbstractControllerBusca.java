package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import service.InterfaceService;

public abstract class AbstractControllerBusca<T, V extends JDialog> implements ActionListener, InterfaceControllerBusca<T> {

    protected final V view;
    protected final InterfaceService<T> service;
    protected final Consumer<Integer> atualizaCodigo;

    protected AbstractControllerBusca(V view, InterfaceService<T> service, Consumer<Integer> atualizaCodigo) {
        this.view = view;
        this.service = service;
        this.atualizaCodigo = atualizaCodigo;
        initListeners();
    }

    @Override
    public void initListeners() {
        getButtonCarregar().addActionListener(this);
        getButtonFiltrar().addActionListener(this);
        getButtonSair().addActionListener(this);
        getButtonAtivar().addActionListener(this);
        getButtonInativar().addActionListener(this);

        getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && getTable().getSelectedRow() != -1) {
                handleSelecionarItem();
            }
        });
    }

    protected void handleSelecionarItem() {
        int row = getTable().getSelectedRow();
        int statusColumnIndex = getStatusColumnIndex();
        Object statusObj = getTable().getValueAt(row, statusColumnIndex);

        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            getButtonAtivar().setEnabled(status == 'I');
            getButtonInativar().setEnabled(status == 'A');
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object source = evento.getSource();

        if (source == getButtonCarregar()) {
            handleCarregar();
        } else if (source == getButtonFiltrar()) {
            handleFiltrar();
        } else if (source == getButtonSair()) {
            handleSair();
        } else if (source == getButtonAtivar()) {
            handleAtivarInativar(true);
        } else if (source == getButtonInativar()) {
            handleAtivarInativar(false);
        }
    }

    @Override
    public void handleCarregar() {
        if (getTable().getRowCount() == 0) {
            showMessage("Não Existem Dados Selecionados para Edição!");
            return;
        }

        int codigo = (int) getTable().getValueAt(getTable().getSelectedRow(), 0);
        atualizaCodigo.accept(codigo);
        view.dispose();
    }

    @Override
    public void handleFiltrar() {
        String filtroTexto = getTextFieldFiltro().getText().trim();

        if (filtroTexto.isEmpty()) {
            showMessage("Sem Dados para a Seleção...");
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) getTable().getModel();
        tabela.setRowCount(0);

        int filtroIndex = getComboBoxFiltro().getSelectedIndex();

        try {
            executarFiltro(filtroIndex, filtroTexto, tabela);
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    @Override
    public void carregarPorAtributo(String atributo, String valor, DefaultTableModel tabela) {
        try {
            List<T> lista = service.Carregar(atributo, valor);
            lista.forEach(item -> adicionarLinhaTabela(tabela, item));
        } catch (Exception e) {
            showError("Erro ao carregar dados: " + e.getMessage());
        }
    }

    @Override
    public void handleSair() {
        view.dispose();
    }

    @Override
    public void handleAtivarInativar(boolean ativar) {
        if (getTable().getRowCount() == 0) {
            showMessage("Não Existem Dados Selecionados para Edição!");
            return;
        }

        int selectedRow = getTable().getSelectedRow();
        int codigo = (int) getTable().getValueAt(selectedRow, 0);
        char statusAtual = (char) getTable().getValueAt(selectedRow, getStatusColumnIndex());

        if (statusAtual == (ativar ? 'A' : 'I')) {
            showMessage(String.format("%s já está %s.",
                    getNomeEntidade(), ativar ? "Ativo" : "Inativo"));
            return;
        }

        try {
            service.AtivarInativar(codigo, ativar);
            atualizarStatusNaTabela(selectedRow, ativar);
            showMessage(String.format("%s %s com sucesso!",
                    getNomeEntidade(), ativar ? "ativado" : "inativado"));
        } catch (Exception ex) {
            showError("Erro ao alterar status: " + ex.getMessage());
        }
    }

    protected void atualizarStatusNaTabela(int row, boolean ativar) {
        DefaultTableModel tabela = (DefaultTableModel) getTable().getModel();
        tabela.setValueAt(ativar ? 'A' : 'I', row, getStatusColumnIndex());
        getButtonAtivar().setEnabled(!ativar);
        getButtonInativar().setEnabled(ativar);
    }

    protected void carregarPorId(int id, DefaultTableModel tabela) {
        try {
            T entidade = service.Carregar(id);
            if (entidade != null) {
                adicionarLinhaTabela(tabela, entidade);
            }
        } catch (Exception e) {
            showError("Erro ao carregar por ID: " + e.getMessage());
        }
    }

    protected void showMessage(String message) {
        JOptionPane.showMessageDialog(view, message);
    }

    protected void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    protected abstract JButton getButtonCarregar();

    protected abstract JButton getButtonFiltrar();

    protected abstract JButton getButtonSair();

    protected abstract JButton getButtonAtivar();

    protected abstract JButton getButtonInativar();

    protected abstract JTable getTable();

    protected abstract JTextField getTextFieldFiltro();

    protected abstract JComboBox<?> getComboBoxFiltro();

    protected abstract int getStatusColumnIndex();

    protected abstract String getNomeEntidade();

    protected abstract void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws Exception;
}
