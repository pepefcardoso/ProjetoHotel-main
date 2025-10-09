package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Marca;
import service.MarcaService;
import view.TelaBuscaMarca;

public final class ControllerBuscaMarca extends AbstractControllerBusca<Marca, TelaBuscaMarca> {

    public ControllerBuscaMarca(TelaBuscaMarca view, Consumer<Integer> atualizaCodigo) {
        super(view, new MarcaService(), atualizaCodigo);
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Marca marca) {
        tabela.addRow(new Object[]{
            marca.getId(),
            marca.getDescricao(),
            marca.getStatus()
        });
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroMarca filtro = FiltroMarca.fromIndex(filtroIndex);

        switch (filtro) {
            case ID:
                carregarPorId(Integer.parseInt(filtroTexto), tabela);
                break;
            case DESCRICAO:
                carregarPorAtributo("descricao", filtroTexto, tabela);
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
        return 2;
    }

    @Override
    protected String getNomeEntidade() {
        return "Marca";
    }

    private enum FiltroMarca {
        ID, DESCRICAO;

        public static FiltroMarca fromIndex(int index) {
            if (index >= 0 && index < values().length) {
                return values()[index];
            }
            throw new IllegalArgumentException("Índice de filtro inválido: " + index);
        }
    }
}
