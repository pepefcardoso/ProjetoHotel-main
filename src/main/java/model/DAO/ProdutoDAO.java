package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Produto;

public class ProdutoDAO extends BaseDAO<Produto> {

    @Override
    protected String getTableName() {
        return "produto_copa";
    }

    @Override
    protected String getSelectColumns() {
        return "id, descricao, valor, obs, status";
    }

    @Override
    protected String getCreateColumns() {
        return "descricao, valor, obs, status";
    }

    @Override
    protected String getCreatePlaceholders() {
        return "?, ?, ?, ?";
    }

    @Override
    protected String getUpdateSetClause() {
        return "descricao = ?, valor = ?, obs = ?, status = ?";
    }

    @Override
    protected Produto mapResultSetToEntity(ResultSet rst) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rst.getInt("id"));
        produto.setDescricao(rst.getString("descricao"));
        produto.setValor(rst.getFloat("valor"));
        produto.setObs(rst.getString("obs"));
        produto.setStatus(rst.getString("status").charAt(0));
        return produto;
    }

    @Override
    protected void setCreateParameters(PreparedStatement pstm, Produto objeto) throws SQLException {
        pstm.setString(1, objeto.getDescricao());
        pstm.setFloat(2, objeto.getValor());
        pstm.setString(3, objeto.getObs());
        pstm.setString(4, String.valueOf(objeto.getStatus()));
    }

    @Override
    protected void setUpdateParameters(PreparedStatement pstm, Produto objeto) throws SQLException {
        setCreateParameters(pstm, objeto);
        pstm.setInt(5, objeto.getId());
    }
}
