package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Quarto;
import service.QuartoService;
import view.TelaBuscaQuarto;

public final class ControllerBuscaQuarto extends AbstractControllerBusca<Quarto, TelaBuscaQuarto> {

    public ControllerBuscaQuarto(TelaBuscaQuarto view, Consumer<Integer> atualizaCodigo) {
        super(view, new QuartoService(), atualizaCodigo);
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Quarto quarto) {
        tabela.addRow(new Object[]{
            quarto.getId(), quarto.getDescricao(), quarto.getCapacidadeHospedes(),
            quarto.getMetragem(), quarto.getIdentificacao(), quarto.getAndar(),
            quarto.isFlagAnimais(), quarto.getObs(), quarto.getStatus()
        });
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroQuarto filtro = FiltroQuarto.fromIndex(filtroIndex);

        switch (filtro) {
            case ID:
                carregarPorId(Integer.parseInt(filtroTexto), tabela);
                break;
            case DESCRICAO:
                carregarPorAtributo("descricao", filtroTexto, tabela);
                break;
            case CAPACIDADE_HOSPEDES:
                carregarPorAtributo("capacidade_hospedes", filtroTexto, tabela);
                break;
            case METRAGEM:
                carregarPorAtributo("metragem", filtroTexto, tabela);
                break;
            case IDENTIFICACAO:
                carregarPorAtributo("identificacao", filtroTexto, tabela);
                break;
            case ANDAR:
                carregarPorAtributo("andar", filtroTexto, tabela);
                break;
            case OBS:
                carregarPorAtributo("obs", filtroTexto, tabela);
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
        return 8;
    }

    @Override
    protected String getNomeEntidade() {
        return "Quarto";
    }

    private enum FiltroQuarto {
        ID, DESCRICAO, CAPACIDADE_HOSPEDES, METRAGEM, IDENTIFICACAO, ANDAR, OBS;

        public static FiltroQuarto fromIndex(int index) {
            if (index >= 0 && index < values().length) {
                return values()[index];
            }
            throw new IllegalArgumentException("Índice de filtro inválido: " + index);
        }
    }
}
