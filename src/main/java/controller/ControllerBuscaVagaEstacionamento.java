package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.VagaEstacionamento;
import service.VagaEstacionamentoService;
import view.TelaBuscaVaga;

public final class ControllerBuscaVagaEstacionamento extends AbstractControllerBusca<VagaEstacionamento, TelaBuscaVaga> {

    private enum FiltroVaga {
        ID, DESCRICAO, OBSERVACAO, METRAGEM;

        static FiltroVaga fromIndex(int index) {
            if (index < 0 || index >= values().length) {
                throw new IllegalArgumentException("Filtro inválido: " + index);
            }
            return values()[index];
        }
    }

    public ControllerBuscaVagaEstacionamento(TelaBuscaVaga view, Consumer<Integer> atualizaCodigo) {
        super(view, new VagaEstacionamentoService(), atualizaCodigo);
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroVaga filtro = FiltroVaga.fromIndex(filtroIndex);

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
            case METRAGEM:
                carregarPorAtributo("metragem_vaga", filtroTexto, tabela);
                break;
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, VagaEstacionamento vaga) {
        tabela.addRow(new Object[]{
            vaga.getId(),
            vaga.getDescricao(),
            vaga.getObs(),
            vaga.getMetragemVaga(),
            vaga.getStatus()
        });
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
        return "A Vaga";
    }
}
