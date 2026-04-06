package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Reserva;
import service.ReservaService;
import utilities.Utilities;
import view.TelaBuscaReserva;

public final class ControllerBuscaReserva extends AbstractControllerBusca<Reserva, TelaBuscaReserva> {

    private enum FiltroReserva {
        ID, STATUS, OBSERVACAO;

        static FiltroReserva fromIndex(int index) {
            if (index < 0 || index >= values().length) {
                throw new IllegalArgumentException("Filtro inválido: " + index);
            }
            return values()[index];
        }
    }

    public ControllerBuscaReserva(TelaBuscaReserva view, Consumer<Integer> atualizaCodigo) {
        super(view, new ReservaService(), atualizaCodigo);
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroReserva filtro = FiltroReserva.fromIndex(filtroIndex);
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
    public void adicionarLinhaTabela(DefaultTableModel tabela, Reserva reserva) {
        tabela.addRow(new Object[]{
            reserva.getId(),
            Utilities.formatarData(reserva.getDataHoraReserva()),
            Utilities.formatarData(reserva.getDataPrevistaEntrada()),
            Utilities.formatarData(reserva.getDataPrevistaSaida()),
            reserva.getStatus(),
            reserva.getObs()
        });
    }

    @Override
    protected JButton getButtonCarregar() { return view.getjButtonCarregar(); }
    @Override
    protected JButton getButtonFiltrar() { return view.getjButtonFiltar(); }
    @Override
    protected JButton getButtonSair() { return view.getjButtonSair(); }
    @Override
    protected JButton getButtonAtivar() { return view.getjButtonAtivar(); }
    @Override
    protected JButton getButtonInativar() { return view.getjButtonInativar(); }
    @Override
    protected JTable getTable() { return view.getjTableDados(); }
    @Override
    protected JTextField getTextFieldFiltro() { return view.getjTFFiltro(); }
    @Override
    protected JComboBox<?> getComboBoxFiltro() { return view.getjCBFiltro(); }
    @Override
    protected int getStatusColumnIndex() { return 4; }
    @Override
    protected String getNomeEntidade() { return "A Reserva"; }
}