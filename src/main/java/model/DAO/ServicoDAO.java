package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Servico;

public class ServicoDAO extends BaseDAO<Servico> {

    @Override
    protected String getTableName() {
        return "servico";
    }

    @Override
    protected String getSelectColumns() {
        return "id, descricao, obs, status";
    }

    @Override
    protected String getCreateColumns() {
        return "descricao, obs, status";
    }

    @Override
    protected String getCreatePlaceholders() {
        return "?, ?, ?";
    }

    @Override
    protected String getUpdateSetClause() {
        return "descricao = ?, obs = ?, status = ?";
    }

    @Override
    protected Servico mapResultSetToEntity(ResultSet rst) throws SQLException {
        Servico servico = new Servico();
        servico.setId(rst.getInt("id"));
        servico.setDescricao(rst.getString("descricao"));
        servico.setObs(rst.getString("obs"));
        servico.setStatus(rst.getString("status").charAt(0));
        return servico;
    }

    @Override
    protected void setCreateParameters(PreparedStatement pstm, Servico objeto) throws SQLException {
        pstm.setString(1, objeto.getDescricao());
        pstm.setString(2, objeto.getObs());
        pstm.setString(3, String.valueOf(objeto.getStatus()));
    }

    @Override
    protected void setUpdateParameters(PreparedStatement pstm, Servico objeto) throws SQLException {
        setCreateParameters(pstm, objeto);
        pstm.setInt(4, objeto.getId());
    }
}
