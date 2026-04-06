package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Check;
import service.CheckService;
import utilities.Utilities;
import view.TelaBuscaCheck;

public final class ControllerBuscaCheck extends AbstractControllerBusca<Check, TelaBuscaCheck> {

    private enum FiltroCheck {
        ID, OBSERVACAO;

        static FiltroCheck fromIndex(int index) {
            if (index < 0 || index >= values().length) {
                throw new IllegalArgumentException("Filtro inválido: " + index);
            }
            return values()[index];
        }
    }

    public ControllerBuscaCheck(TelaBuscaCheck view, Consumer<Integer> atualizaCodigo) {
        super(view, new CheckService(), atualizaCodigo);
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroCheck filtro = FiltroCheck.fromIndex(filtroIndex);
        switch (filtro) {
            case ID:
                carregarPorId(Integer.parseInt(filtroTexto), tabela);
                break;
            case OBSERVACAO:
                carregarPorAtributo("obs", filtroTexto, tabela);
                break;
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Check check) {
        tabela.addRow(new Object[]{
            check.getId(),
            Utilities.formatarData(check.getDataHoraEntrada()),
            check.getStatus(),
            check.getObs()
        });
    }

    @Override
    protected JButton getButtonCarregar()  { return view.getjButtonCarregar(); }
    @Override
    protected JButton getButtonFiltrar()   { return view.getjButtonFiltar();   }
    @Override
    protected JButton getButtonSair()      { return view.getjButtonSair();     }
    @Override
    protected JButton getButtonAtivar()    { return view.getjButtonAtivar();   }
    @Override
    protected JButton getButtonInativar()  { return view.getjButtonInativar(); }
    @Override
    protected JTable getTable()            { return view.getjTableDados();     }
    @Override
    protected JTextField getTextFieldFiltro() { return view.getjTFFiltro();   }
    @Override
    protected JComboBox<?> getComboBoxFiltro() { return view.getjCBFiltro();  }
    @Override
    protected int getStatusColumnIndex()   { return 2; }
    @Override
    protected String getNomeEntidade()     { return "O Check"; }
}