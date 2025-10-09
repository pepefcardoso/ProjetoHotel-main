package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Veiculo;

public class VeiculoDAO extends BaseDAO<Veiculo> {

    @Override
    protected String getTableName() {
        return "veiculo";
    }

    @Override
    protected String getSelectColumns() {
        return "id, placa, modelo_id, cor, status, funcionario_id, fornecedor_id, hospede_id";
    }

    @Override
    protected String getCreateColumns() {
        return "placa, modelo_id, cor, status, funcionario_id, fornecedor_id, hospede_id";
    }

    @Override
    protected String getCreatePlaceholders() {
        return "?, ?, ?, ?, ?, ?, ?";
    }

    @Override
    protected String getUpdateSetClause() {
        return "placa = ?, modelo_id = ?, cor = ?, status = ?, "
                + "funcionario_id = ?, fornecedor_id = ?, hospede_id = ?";
    }

    @Override
    protected Veiculo mapResultSetToEntity(ResultSet rst) throws SQLException {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(rst.getInt("id"));
        veiculo.setPlaca(rst.getString("placa"));
        veiculo.setModelo(new ModeloDAO().Retrieve(rst.getInt("modelo_id")));
        veiculo.setCor(rst.getString("cor"));
        veiculo.setStatus(rst.getString("status").charAt(0));

        int funcionarioId = rst.getInt("funcionario_id");
        int fornecedorId = rst.getInt("fornecedor_id");
        int hospedeId = rst.getInt("hospede_id");

        if (funcionarioId > 0) {
            veiculo.setProprietario(new service.FuncionarioService().Carregar(funcionarioId));
        } else if (fornecedorId > 0) {
            veiculo.setProprietario(new service.FornecedorService().Carregar(fornecedorId));
        } else if (hospedeId > 0) {
            veiculo.setProprietario(new service.HospedeService().Carregar(hospedeId));
        } else {
            throw new SQLException("Veículo de id " + veiculo.getId() + " sem proprietário associado.");
        }

        return veiculo;
    }

    @Override
    protected void setCreateParameters(PreparedStatement pstm, Veiculo objeto) throws SQLException {
        pstm.setString(1, objeto.getPlaca());
        pstm.setInt(2, objeto.getModelo().getId());
        pstm.setString(3, objeto.getCor());
        pstm.setString(4, String.valueOf(objeto.getStatus()));

        switch (objeto.getProprietario().getClass().getSimpleName()) {
            case "Funcionario":
                pstm.setInt(5, objeto.getProprietario().getId());
                pstm.setNull(6, java.sql.Types.INTEGER);
                pstm.setNull(7, java.sql.Types.INTEGER);
                break;
            case "Fornecedor":
                pstm.setNull(5, java.sql.Types.INTEGER);
                pstm.setInt(6, objeto.getProprietario().getId());
                pstm.setNull(7, java.sql.Types.INTEGER);
                break;
            case "Hospede":
                pstm.setNull(5, java.sql.Types.INTEGER);
                pstm.setNull(6, java.sql.Types.INTEGER);
                pstm.setInt(7, objeto.getProprietario().getId());
                break;
            default:
                throw new SQLException("Tipo de proprietário desconhecido: " + objeto.getProprietario().getClass().getSimpleName());
        }
    }

    @Override
    protected void setUpdateParameters(PreparedStatement pstm, Veiculo objeto) throws SQLException {
        setCreateParameters(pstm, objeto);
        pstm.setInt(8, objeto.getId());
    }
}
