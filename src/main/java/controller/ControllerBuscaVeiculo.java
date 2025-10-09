package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Veiculo;
import service.VeiculoService;
import view.TelaBuscaVeiculo;

public final class ControllerBuscaVeiculo extends AbstractControllerBusca<Veiculo, TelaBuscaVeiculo> {

    private enum FiltroVeiculo {
        ID, PLACA, COR, MODELO, HOSPEDE, FUNCIONARIO, FORNECEDOR;

        static FiltroVeiculo fromIndex(int index) {
            if (index < 0 || index >= values().length) {
                throw new IllegalArgumentException("Filtro inválido: " + index);
            }
            return values()[index];
        }
    }

    public ControllerBuscaVeiculo(TelaBuscaVeiculo view, Consumer<Integer> atualizaCodigo) {
        super(view, new VeiculoService(), atualizaCodigo);
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroVeiculo filtro = FiltroVeiculo.fromIndex(filtroIndex);

        switch (filtro) {
            case ID:
                carregarPorId(Integer.parseInt(filtroTexto), tabela);
                break;
            case PLACA:
                carregarPorAtributo("placa", filtroTexto, tabela);
                break;
            case COR:
                carregarPorAtributo("cor", filtroTexto, tabela);
                break;
            case MODELO:
                carregarPorAtributo("modelo_id", filtroTexto, tabela);
                break;
            case HOSPEDE:
                carregarPorAtributo("hospede_id", filtroTexto, tabela);
                break;
            case FUNCIONARIO:
                carregarPorAtributo("funcionario_id", filtroTexto, tabela);
                break;
            case FORNECEDOR:
                carregarPorAtributo("fornecedor_id", filtroTexto, tabela);
                break;
        }
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Veiculo veiculo) {
        tabela.addRow(new Object[]{
            veiculo.getId(),
            veiculo.getPlaca(),
            veiculo.getCor(),
            veiculo.getModelo().getId(),
            veiculo.getProprietario().getId() + " - " + veiculo.getProprietario().getNome(),
            veiculo.getStatus()
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
        return 5;
    }

    @Override
    protected String getNomeEntidade() {
        return "O Veículo";
    }
}
