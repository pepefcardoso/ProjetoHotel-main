package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.CopaQuarto;
import service.CopaQuartoService;
import utilities.Utilities;
import view.TelaBuscaCopaQuarto;

public final class ControllerBuscaCopaQuarto extends AbstractControllerBusca<CopaQuarto, TelaBuscaCopaQuarto> {

    private enum FiltroCopaQuarto {
        ID, QUARTO_ID, PRODUTO_ID, OBSERVACAO;

        static FiltroCopaQuarto fromIndex(int index) {
            if (index < 0 || index >= values().length)
                throw new IllegalArgumentException("Filtro inválido: " + index);
            return values()[index];
        }
    }

    public ControllerBuscaCopaQuarto(TelaBuscaCopaQuarto view, Consumer<Integer> atualizaCodigo) {
        super(view, new CopaQuartoService(), atualizaCodigo);
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroCopaQuarto filtro = FiltroCopaQuarto.fromIndex(filtroIndex);
        switch (filtro) {
            case ID:
                carregarPorId(Integer.parseInt(filtroTexto), tabela);
                break;
            case QUARTO_ID:
                // Busca por quarto.id via JPQL nativo - simplificado via obs
                carregarPorAtributo("quarto.id", filtroTexto, tabela);
                break;
            case PRODUTO_ID:
                carregarPorAtributo("produto.id", filtroTexto, tabela);
                break;
            case OBSERVACAO:
                carregarPorAtributo("obs", filtroTexto, tabela);
                break;
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, CopaQuarto copa) {
        String quarto  = copa.getQuarto()  != null ? copa.getQuarto().getIdentificacao()  : "-";
        String produto = copa.getProduto() != null ? copa.getProduto().getDescricao()     : "-";
        String valor   = copa.getProduto() != null
                ? String.format("R$ %.2f", copa.getProduto().getValor().doubleValue()) : "-";
        String data    = Utilities.formatarData(copa.getDataHoraPedido());

        tabela.addRow(new Object[]{
            copa.getId(), quarto, produto, copa.getQuantidade(), valor, data, copa.getStatus()
        });
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
    protected int getStatusColumnIndex()    { return 6; }
    @Override
    protected String getNomeEntidade()      { return "O Pedido de Copa"; }
}
