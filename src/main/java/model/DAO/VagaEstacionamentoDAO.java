package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.VagaEstacionamento;

public class VagaEstacionamentoDAO extends BaseDAO<VagaEstacionamento> {

    @Override
    protected String getTableName() {
        return "vaga_estacionamento";
    }

    @Override
    protected String getSelectColumns() {
        return "id, descricao, obs, metragem_vaga, status";
    }

    @Override
    protected String getCreateColumns() {
        return "descricao, obs, metragem_vaga, status";
    }

    @Override
    protected String getCreatePlaceholders() {
        return "?, ?, ?, ?";
    }

    @Override
    protected String getUpdateSetClause() {
        return "descricao = ?, obs = ?, metragem_vaga = ?, status = ?";
    }

    @Override
    protected VagaEstacionamento mapResultSetToEntity(ResultSet rst) throws SQLException {
        VagaEstacionamento vaga = new VagaEstacionamento();
        vaga.setId(rst.getInt("id"));
        vaga.setDescricao(rst.getString("descricao"));
        vaga.setObs(rst.getString("obs"));
        vaga.setMetragemVaga(rst.getFloat("metragem_vaga"));
        vaga.setStatus(rst.getString("status").charAt(0));
        return vaga;
    }

    @Override
    protected void setCreateParameters(PreparedStatement pstm, VagaEstacionamento objeto) throws SQLException {
        pstm.setString(1, objeto.getDescricao());
        pstm.setString(2, objeto.getObs());
        pstm.setFloat(3, objeto.getMetragemVaga());
        pstm.setString(4, String.valueOf(objeto.getStatus()));
    }

    @Override
    protected void setUpdateParameters(PreparedStatement pstm, VagaEstacionamento objeto) throws SQLException {
        setCreateParameters(pstm, objeto);
        pstm.setInt(5, objeto.getId());
    }
}
