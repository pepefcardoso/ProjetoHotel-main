package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Produto;
import service.ProdutoService;
import view.TelaBuscaProduto;

public final class ControllerBuscaProduto extends AbstractControllerBusca<Produto, TelaBuscaProduto> {

    public ControllerBuscaProduto(TelaBuscaProduto view, Consumer<Integer> atualizaCodigo) {
        super(view, new ProdutoService(), atualizaCodigo);
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Produto produto) {
        tabela.addRow(new Object[]{
            produto.getId(),
            produto.getDescricao(),
            produto.getObs(),
            produto.getValor(),
            produto.getStatus()
        });
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroProduto filtro = FiltroProduto.fromIndex(filtroIndex);

        switch (filtro) {
            case ID:
                carregarPorId(Integer.parseInt(filtroTexto), tabela);
                break;
            case DESCRICAO:
                carregarPorAtributo("descricao", filtroTexto, tabela);
                break;
            case OBSERVACAO:
                carregarPorAtributo("obs", filtroTexto, tabela);
                break;
            case VALOR:
                carregarPorAtributo("valor", filtroTexto, tabela);
                break;
        }
    }

    @Override
    protected JButton getButtonCarregar() {
        return view.getjButtonCarregar();
    }

    @Override
    protected JButton getButtonFiltrar() {
        return view.getjButtonFiltar();
    }

    @Override
    protected JButton getButtonSair() {
        return view.getjButtonSair();
    }

    @Override
    protected JButton getButtonAtivar() {
        return view.getjButtonAtivar();
    }

    @Override
    protected JButton getButtonInativar() {
        return view.getjButtonInativar();
    }

    @Override
    protected JTable getTable() {
        return view.getjTableDados();
    }

    @Override
    protected JTextField getTextFieldFiltro() {
        return view.getjTFFiltro();
    }

    @Override
    protected JComboBox<?> getComboBoxFiltro() {
        return view.getjCBFiltro();
    }

    @Override
    protected int getStatusColumnIndex() {
        return 4;
    }

    @Override
    protected String getNomeEntidade() {
        return "Produto";
    }

    private enum FiltroProduto {
        ID, DESCRICAO, OBSERVACAO, VALOR;

        public static FiltroProduto fromIndex(int index) {
            if (index >= 0 && index < values().length) {
                return values()[index];
            }
            throw new IllegalArgumentException("Índice de filtro inválido: " + index);
        }
    }
}
