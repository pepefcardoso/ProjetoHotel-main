package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Modelo;

public class ModeloDAO extends BaseDAO<Modelo> {

    @Override
    protected String getTableName() {
        return "modelo";
    }

    @Override
    protected String getSelectColumns() {
        return "id, descricao, marca_id, status";
    }

    @Override
    protected String getCreateColumns() {
        return "descricao, marca_id, status";
    }

    @Override
    protected String getCreatePlaceholders() {
        return "?, ?, ?";
    }

    @Override
    protected String getUpdateSetClause() {
        return "descricao = ?, marca_id = ?, status = ?";
    }

    @Override
    protected Modelo mapResultSetToEntity(ResultSet rst) throws SQLException {
        Modelo modelo = new Modelo();
        modelo.setId(rst.getInt("id"));
        modelo.setDescricao(rst.getString("descricao"));
        modelo.setStatus(rst.getString("status").charAt(0));

        MarcaDAO marcaDAO = new MarcaDAO();
        modelo.setMarca(marcaDAO.Retrieve(rst.getInt("marca_id")));

        return modelo;
    }

    @Override
    protected void setCreateParameters(PreparedStatement pstm, Modelo objeto) throws SQLException {
        pstm.setString(1, objeto.getDescricao());
        pstm.setInt(2, objeto.getMarca().getId());
        pstm.setString(3, String.valueOf(objeto.getStatus()));
    }

    @Override
    protected void setUpdateParameters(PreparedStatement pstm, Modelo objeto) throws SQLException {
        setCreateParameters(pstm, objeto);
        pstm.setInt(4, objeto.getId());
    }
}
