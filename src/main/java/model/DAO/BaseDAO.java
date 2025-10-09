package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utilities.AppLogger;

public abstract class BaseDAO<T> implements InterfaceDAO<T> {
    protected abstract String getTableName();
    protected abstract T mapResultSetToEntity(ResultSet rst) throws SQLException;
    protected abstract void setCreateParameters(PreparedStatement pstm, T objeto) throws SQLException;
    protected abstract void setUpdateParameters(PreparedStatement pstm, T objeto) throws SQLException;
    protected abstract String getCreateColumns();
    protected abstract String getCreatePlaceholders();
    protected abstract String getUpdateSetClause();
    protected abstract String getSelectColumns();

    @Override
    public void Create(T objeto) throws SQLException {
        String sql = String.format("INSERT INTO %s(%s) VALUES (%s)",
                getTableName(), getCreateColumns(), getCreatePlaceholders());

        executeUpdate(sql, pstm -> setCreateParameters(pstm, objeto),
                "Erro ao criar " + getTableName());
    }

    @Override
    public T Retrieve(int id) throws SQLException {
        String sql = String.format("SELECT %s FROM %s WHERE id = ?",
                getSelectColumns(), getTableName());

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
            try (ResultSet rst = pstm.executeQuery()) {
                if (rst.next()) {
                    return mapResultSetToEntity(rst);
                }
                return null;
            }
        } catch (SQLException ex) {
            AppLogger.error("Erro ao carregar " + getTableName(), ex);
            throw new SQLException("Erro ao carregar " + getTableName());
        }
    }

    @Override
    public List<T> Retrieve(String atributo, String valor) throws SQLException {
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                getSelectColumns(), getTableName(), atributo);

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, "%" + valor + "%");
            try (ResultSet rst = pstm.executeQuery()) {
                List<T> lista = new ArrayList<>();
                while (rst.next()) {
                    lista.add(mapResultSetToEntity(rst));
                }
                return lista;
            }
        } catch (SQLException ex) {
            AppLogger.error("Erro ao buscar " + getTableName(), ex);
            throw new SQLException("Erro ao buscar " + getTableName());
        }
    }

    @Override
    public void Update(T objeto) throws SQLException {
        String sql = String.format("UPDATE %s SET %s WHERE id = ?",
                getTableName(), getUpdateSetClause());

        executeUpdate(sql, pstm -> setUpdateParameters(pstm, objeto),
                "Erro ao atualizar " + getTableName());
    }

    @Override
    public void Delete(T objeto) throws SQLException {
        throw new UnsupportedOperationException("Operação de exclusão não implementada");
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws SQLException {
        String sql = String.format("UPDATE %s SET status = ? WHERE id = ?", getTableName());

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, ativar ? "A" : "I");
            pstm.setInt(2, id);
            pstm.execute();
        } catch (SQLException ex) {
            AppLogger.error("Erro ao ativar/inativar " + getTableName(), ex);
            throw new SQLException("Erro ao ativar/inativar " + getTableName());
        }
    }

    protected void executeUpdate(String sql, PreparedStatementSetter setter, String errorMsg)
            throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            setter.setParameters(pstm);
            pstm.execute();
        } catch (SQLException ex) {
            AppLogger.error(errorMsg, ex);
            throw new SQLException(errorMsg);
        }
    }

    @FunctionalInterface
    protected interface PreparedStatementSetter {

        void setParameters(PreparedStatement pstm) throws SQLException;
    }
}
