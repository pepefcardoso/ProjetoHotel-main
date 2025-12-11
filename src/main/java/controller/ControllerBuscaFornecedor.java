package controller;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Fornecedor;
import service.FornecedorService;
import view.TelaBuscaFornecedor;

public final class ControllerBuscaFornecedor extends AbstractControllerBusca<Fornecedor, TelaBuscaFornecedor> {

    public ControllerBuscaFornecedor(TelaBuscaFornecedor view, Consumer<Integer> atualizaCodigo) {
        super(view, new FornecedorService(), atualizaCodigo);
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Fornecedor fornecedor) {
        tabela.addRow(new Object[]{
            fornecedor.getId(),
            fornecedor.getNome(),
            fornecedor.getCpf(),
            fornecedor.getStatus(),
            fornecedor.getRazaoSocial(),
            fornecedor.getCnpj(),
            fornecedor.getFone1(),
            fornecedor.getEmail(),
            fornecedor.getCidade(),
            fornecedor.getObs()
        });
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws Exception {
        FiltroFornecedor filtro = FiltroFornecedor.fromIndex(filtroIndex);

        switch (filtro) {
            case ID:
                carregarPorId(Integer.parseInt(filtroTexto), tabela);
                break;
            case NOME:
                carregarPorAtributo("nome", filtroTexto, tabela);
                break;
            case CPF:
                carregarPorAtributo("cpf", filtroTexto, tabela);
                break;
            case RAZAO_SOCIAL:
                carregarPorAtributo("razaoSocial", filtroTexto, tabela);
                break;
            case CNPJ:
                carregarPorAtributo("cnpj", filtroTexto, tabela);
                break;
            case OBSERVACAO:
                carregarPorAtributo("obs", filtroTexto, tabela);
                break;
            case TELEFONE:
                carregarPorAtributo("fone1", filtroTexto, tabela);
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
        return "Fornecedor";
    }

    private enum FiltroFornecedor {
        ID, NOME, CPF, RAZAO_SOCIAL, CNPJ, OBSERVACAO, TELEFONE, EMAIL, CEP, CIDADE, BAIRRO, LOGRADOURO;

        public static FiltroFornecedor fromIndex(int index) {
            if (index >= 0 && index < values().length) {
                return values()[index];
            }
            throw new IllegalArgumentException("Índice de filtro inválido: " + index);
        }
    }
}
