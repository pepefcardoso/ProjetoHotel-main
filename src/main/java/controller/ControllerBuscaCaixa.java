package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Caixa;
import service.CaixaService;
import utilities.Utilities;
import view.TelaBuscaCaixa;

public final class ControllerBuscaCaixa extends AbstractControllerBusca<Caixa, TelaBuscaCaixa> {

    private enum FiltroCaixa {
        ID, STATUS, OBSERVACAO;

        static FiltroCaixa fromIndex(int index) {
            if (index < 0 || index >= values().length)
                throw new IllegalArgumentException("Filtro inválido: " + index);
            return values()[index];
        }
    }

    public ControllerBuscaCaixa(TelaBuscaCaixa view, Consumer<Integer> atualizaCodigo) {
        super(view, new CaixaService(), atualizaCodigo);
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroCaixa filtro = FiltroCaixa.fromIndex(filtroIndex);
        switch (filtro) {
            case ID:
                carregarPorId(Integer.parseInt(filtroTexto), tabela);
                break;
            case STATUS:
                carregarPorAtributo("status", filtroTexto, tabela);
                break;
            case OBSERVACAO:
                carregarPorAtributo("obs", filtroTexto, tabela);
                break;
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Caixa caixa) {
        tabela.addRow(new Object[]{
            caixa.getId(),
            Utilities.formatarData(caixa.getDataHoraAbertura()),
            Utilities.formatarData(caixa.getDataHoraFechamento()),
            caixa.getValorDeAbertura() != null ? String.format("R$ %.2f", caixa.getValorDeAbertura().doubleValue()) : "-",
            caixa.getValorDeFechamento() != null ? String.format("R$ %.2f", caixa.getValorDeFechamento().doubleValue()) : "-",
            caixa.getStatus(),
            caixa.getObs()
        });
    }

    // Caixa usa 'A' = Aberto, 'F' = Fechado (não 'I' = Inativo)
    @Override
    protected void handleSelecionarItem() {
        int row = getTable().getSelectedRow();
        if (row < 0) return;
        Object statusObj = getTable().getValueAt(row, getStatusColumnIndex());
        if (statusObj != null) {
            char status = statusObj.toString().charAt(0);
            getButtonAtivar().setEnabled(status == 'F');    // pode "reabrir" se fechado
            getButtonInativar().setEnabled(status == 'A');  // pode "fechar" se aberto
        }
    }

    @Override
    protected JButton getButtonCarregar()   { return view.getjButtonCarregar(); }
    @Override
    protected JButton getButtonFiltrar()    { return view.getjButtonFiltar(); }
    @Override
    protected JButton getButtonSair()       { return view.getjButtonSair(); }
    @Override
    protected JButton getButtonAtivar()     { return view.getjButtonAtivar(); }
    @Override
    protected JButton getButtonInativar()   { return view.getjButtonInativar(); }
    @Override
    protected JTable getTable()             { return view.getjTableDados(); }
    @Override
    protected JTextField getTextFieldFiltro() { return view.getjTFFiltro(); }
    @Override
    protected JComboBox<?> getComboBoxFiltro() { return view.getjCBFiltro(); }
    @Override
    protected int getStatusColumnIndex()    { return 5; }
    @Override
    protected String getNomeEntidade()      { return "O Caixa"; }
}
