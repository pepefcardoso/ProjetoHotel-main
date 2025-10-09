package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Funcionario;

public class FuncionarioDAO extends BaseDAO<Funcionario> {

    @Override
    protected String getTableName() {
        return "funcionario";
    }

    @Override
    protected String getCreateColumns() {
        return "nome, fone, fone2, email, cep, logradouro, bairro, cidade, complemento, " +
               "data_cadastro, cpf, rg, obs, status, sexo, usuario, senha";
    }

    @Override
    protected String getCreatePlaceholders() {
        return "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
    }

    @Override
    protected String getUpdateSetClause() {
        return "nome = ?, fone = ?, fone2 = ?, email = ?, cep = ?, logradouro = ?, bairro = ?, " +
               "cidade = ?, complemento = ?, data_cadastro = ?, cpf = ?, rg = ?, obs = ?, " +
               "status = ?, sexo = ?, usuario = ?, senha = ?";
    }

    @Override
    protected String getSelectColumns() {
        return "id, nome, fone, fone2, email, cep, logradouro, bairro, cidade, complemento, " +
               "data_cadastro, cpf, rg, obs, status, sexo, usuario, senha";
    }

    @Override
    protected Funcionario mapResultSetToEntity(ResultSet rst) throws SQLException {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(rst.getInt("id"));
        funcionario.setNome(rst.getString("nome"));
        funcionario.setFone1(rst.getString("fone"));
        funcionario.setFone2(rst.getString("fone2"));
        funcionario.setEmail(rst.getString("email"));
        funcionario.setCep(rst.getString("cep"));
        funcionario.setLogradouro(rst.getString("logradouro"));
        funcionario.setBairro(rst.getString("bairro"));
        funcionario.setCidade(rst.getString("cidade"));
        funcionario.setComplemento(rst.getString("complemento"));
        funcionario.setDataCadastro(rst.getString("data_cadastro"));
        funcionario.setCpf(rst.getString("cpf"));
        funcionario.setRg(rst.getString("rg"));
        funcionario.setObs(rst.getString("obs"));
        funcionario.setUsuario(rst.getString("usuario"));
        funcionario.setSenha(rst.getString("senha"));
        funcionario.setStatus(rst.getString("status").charAt(0));
        funcionario.setSexo(rst.getString("sexo").charAt(0));
        return funcionario;
    }

    @Override
    protected void setCreateParameters(PreparedStatement pstm, Funcionario objeto) throws SQLException {
        pstm.setString(1, objeto.getNome());
        pstm.setString(2, objeto.getFone1());
        pstm.setString(3, objeto.getFone2());
        pstm.setString(4, objeto.getEmail());
        pstm.setString(5, objeto.getCep());
        pstm.setString(6, objeto.getLogradouro());
        pstm.setString(7, objeto.getBairro());
        pstm.setString(8, objeto.getCidade());
        pstm.setString(9, objeto.getComplemento());
        pstm.setString(10, objeto.getDataCadastro());
        pstm.setString(11, objeto.getCpf());
        pstm.setString(12, objeto.getRg());
        pstm.setString(13, objeto.getObs());
        pstm.setString(14, String.valueOf(objeto.getStatus()));
        pstm.setString(15, String.valueOf(objeto.getSexo()));
        pstm.setString(16, objeto.getUsuario());
        pstm.setString(17, objeto.getSenha());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement pstm, Funcionario objeto) throws SQLException {
        // Reutiliza a lógica da criação para definir os primeiros 17 parâmetros
        setCreateParameters(pstm, objeto);
        // Define o ID como último parâmetro para a cláusula WHERE
        pstm.setInt(18, objeto.getId());
    }
}