package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Quarto;

public class QuartoDAO extends BaseDAO<Quarto> {

    @Override
    protected String getTableName() {
        return "quarto";
    }

    @Override
    protected String getSelectColumns() {
        return "id, descricao, capacidade_hospedes, metragem, identificacao, "
                + "andar, flag_animais, obs, status";
    }

    @Override
    protected String getCreateColumns() {
        return "descricao, capacidade_hospedes, metragem, identificacao, "
                + "andar, flag_animais, obs, status";
    }

    @Override
    protected String getCreatePlaceholders() {
        return "?, ?, ?, ?, ?, ?, ?, ?";
    }

    @Override
    protected String getUpdateSetClause() {
        return "descricao = ?, capacidade_hospedes = ?, metragem = ?, "
                + "identificacao = ?, andar = ?, flag_animais = ?, obs = ?, status = ?";
    }

    @Override
    protected Quarto mapResultSetToEntity(ResultSet rst) throws SQLException {
        Quarto quarto = new Quarto();
        quarto.setId(rst.getInt("id"));
        quarto.setDescricao(rst.getString("descricao"));
        quarto.setCapacidadeHospedes(rst.getInt("capacidade_hospedes"));
        quarto.setMetragem(rst.getFloat("metragem"));
        quarto.setIdentificacao(rst.getString("identificacao"));
        quarto.setAndar(rst.getInt("andar"));
        quarto.setFlagAnimais(rst.getBoolean("flag_animais"));
        quarto.setObs(rst.getString("obs"));
        quarto.setStatus(rst.getString("status").charAt(0));
        return quarto;
    }

    @Override
    protected void setCreateParameters(PreparedStatement pstm, Quarto objeto) throws SQLException {
        pstm.setString(1, objeto.getDescricao());
        pstm.setInt(2, objeto.getCapacidadeHospedes());
        pstm.setFloat(3, objeto.getMetragem());
        pstm.setString(4, objeto.getIdentificacao());
        pstm.setInt(5, objeto.getAndar());
        pstm.setBoolean(6, objeto.isFlagAnimais());
        pstm.setString(7, objeto.getObs());
        pstm.setString(8, String.valueOf(objeto.getStatus()));
    }

    @Override
    protected void setUpdateParameters(PreparedStatement pstm, Quarto objeto) throws SQLException {
        setCreateParameters(pstm, objeto);
        pstm.setInt(9, objeto.getId());
    }

    @Override
    public void Delete(Quarto objeto) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE id = ?", getTableName());
        executeUpdate(sql, pstm -> pstm.setInt(1, objeto.getId()), "Erro ao deletar " + getTableName());
    }
}
