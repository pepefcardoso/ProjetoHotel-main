package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Servico;
import service.ServicoService;
import view.TelaBuscaServico;

public final class ControllerBuscaServico extends AbstractControllerBusca<Servico, TelaBuscaServico> {

    private enum FiltroServico {
        ID, DESCRICAO, OBSERVACAO;

        static FiltroServico fromIndex(int index) {
            if (index < 0 || index >= values().length) {
                throw new IllegalArgumentException("Filtro inválido: " + index);
            }
            return values()[index];
        }
    }

    public ControllerBuscaServico(TelaBuscaServico view, Consumer<Integer> atualizaCodigo) {
        super(view, new ServicoService(), atualizaCodigo);
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroServico filtro = FiltroServico.fromIndex(filtroIndex);

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
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Servico servico) {
        tabela.addRow(new Object[]{
            servico.getId(),
            servico.getDescricao(),
            servico.getObs(),
            servico.getStatus()
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
        return 3;
    }

    @Override
    protected String getNomeEntidade() {
        return "O Serviço";
    }
}
