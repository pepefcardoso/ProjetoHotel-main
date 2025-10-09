package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Marca;

public class MarcaDAO extends BaseDAO<Marca> {

    @Override
    protected String getTableName() {
        return "marca";
    }

    @Override
    protected String getCreateColumns() {
        return "descricao, status";
    }

    @Override
    protected String getCreatePlaceholders() {
        return "?, ?";
    }

    @Override
    protected String getUpdateSetClause() {
        return "descricao = ?, status = ?";
    }

    @Override
    protected String getSelectColumns() {
        return "id, descricao, status";
    }

    @Override
    protected Marca mapResultSetToEntity(ResultSet rst) throws SQLException {
        Marca marca = new Marca();
        marca.setId(rst.getInt("id"));
        marca.setDescricao(rst.getString("descricao"));
        marca.setStatus(rst.getString("status").charAt(0));
        return marca;
    }

    @Override
    protected void setCreateParameters(PreparedStatement pstm, Marca objeto) throws SQLException {
        pstm.setString(1, objeto.getDescricao());
        pstm.setString(2, String.valueOf(objeto.getStatus()));
    }

    @Override
    protected void setUpdateParameters(PreparedStatement pstm, Marca objeto) throws SQLException {
        pstm.setString(1, objeto.getDescricao());
        pstm.setString(2, String.valueOf(objeto.getStatus()));
        pstm.setInt(3, objeto.getId());
    }
}
