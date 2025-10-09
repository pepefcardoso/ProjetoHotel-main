package controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Hospede;
import service.HospedeService;
import view.TelaBuscaHospede;

public final class ControllerBuscaHospede extends AbstractControllerBusca<Hospede, TelaBuscaHospede> {

    public ControllerBuscaHospede(TelaBuscaHospede view, Consumer<Integer> atualizaCodigo) {
        super(view, new HospedeService(), atualizaCodigo);
    }

    @Override
    public void adicionarLinhaTabela(DefaultTableModel tabela, Hospede hospede) {
        tabela.addRow(new Object[]{
            hospede.getId(),
            hospede.getNome(),
            hospede.getCpf(),
            hospede.getStatus(),
            hospede.getRazaoSocial(),
            hospede.getCnpj(),
            hospede.getFone1(),
            hospede.getEmail(),
            hospede.getCidade(),
            hospede.getObs()
        });
    }

    @Override
    protected void executarFiltro(int filtroIndex, String filtroTexto, DefaultTableModel tabela) throws SQLException {
        FiltroHospede filtro = FiltroHospede.fromIndex(filtroIndex);

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
                carregarPorAtributo("razao_social", filtroTexto, tabela);
                break;
            case CNPJ:
                carregarPorAtributo("cnpj", filtroTexto, tabela);
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
        return "Hóspede";
    }

    private enum FiltroHospede {
        ID, NOME, CPF, RAZAO_SOCIAL, CNPJ, OBSERVACAO, TELEFONE, EMAIL, CEP, CIDADE, BAIRRO, LOGRADOURO;

        public static FiltroHospede fromIndex(int index) {
            if (index >= 0 && index < values().length) {
                return values()[index];
            }
            throw new IllegalArgumentException("Índice de filtro inválido: " + index);
        }
    }
}
