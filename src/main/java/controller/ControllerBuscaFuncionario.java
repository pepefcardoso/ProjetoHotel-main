package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Funcionario;
import service.FuncionarioService;
import view.TelaBuscaFuncionario;

public final class ControllerBuscaFuncionario extends AbstractControllerBusca<Funcionario, TelaBuscaFuncionario> {
    public ControllerBuscaFuncionario(TelaBuscaFuncionario view, Consumer<Integer> atualizaCodigo) {
        super(view, new FuncionarioService(), atualizaCodigo);
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Funcionario funcionario) {
        tabela.addRow(new Object[]{
            funcionario.getId(),
            funcionario.getNome(),
            funcionario.getCpf(),
            funcionario.getStatus(),
            funcionario.getUsuario(),
            funcionario.getObs(),
            funcionario.getFone1(),
            funcionario.getEmail(),
            funcionario.getCidade(),});
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroFuncionario filtro = FiltroFuncionario.fromIndex(filtroIndex);

        switch (filtro) {
            case ID:
                carregarPorId(Integer.parseInt(filtroTexto), tabela);
                break;
            case USUARIO:
                carregarPorAtributo("usuario", filtroTexto, tabela);
                break;
            case NOME:
                carregarPorAtributo("nome", filtroTexto, tabela);
                break;
            case CPF:
                carregarPorAtributo("cpf", filtroTexto, tabela);
                break;
            case OBSERVACAO:
                carregarPorAtributo("obs", filtroTexto, tabela);
                break;
            case TELEFONE:
                carregarPorAtributo("fone", filtroTexto, tabela);
                break;
            case EMAIL:
                carregarPorAtributo("email", filtroTexto, tabela);
                break;
            case CEP:
                carregarPorAtributo("cep", filtroTexto, tabela);
                break;
            case CIDADE:
                carregarPorAtributo("cidade", filtroTexto, tabela);
                break;
            case BAIRRO:
                carregarPorAtributo("bairro", filtroTexto, tabela);
                break;
            case LOGRADOURO:
                carregarPorAtributo("logradouro", filtroTexto, tabela);
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
        return 3;
    }

    @Override
    protected String getNomeEntidade() {
        return "Funcionário";
    }

    private enum FiltroFuncionario {
        ID, USUARIO, NOME, CPF, OBSERVACAO, TELEFONE, EMAIL, CEP, CIDADE, BAIRRO, LOGRADOURO;

        public static FiltroFuncionario fromIndex(int index) {
            if (index >= 0 && index < values().length) {
                return values()[index];
            }
            throw new IllegalArgumentException("Índice de filtro inválido: " + index);
        }
    }
}
