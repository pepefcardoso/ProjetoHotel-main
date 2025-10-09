package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Fornecedor;

public class FornecedorDAO extends BaseDAO<Fornecedor> {

    @Override
    protected String getTableName() {
        return "fornecedor";
    }

    @Override
    protected String getCreateColumns() {
        return "nome, fone, fone2, email, cep, logradouro, bairro, cidade, complemento, " +
               "data_cadastro, cpf, rg, obs, status, sexo, razao_social, cnpj, " +
               "inscricao_estadual, contato";
    }

    @Override
    protected String getCreatePlaceholders() {
        return "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
    }

    @Override
    protected String getUpdateSetClause() {
        return "nome = ?, fone = ?, fone2 = ?, email = ?, cep = ?, logradouro = ?, bairro = ?, " +
               "cidade = ?, complemento = ?, data_cadastro = ?, cpf = ?, rg = ?, obs = ?, " +
               "status = ?, sexo = ?, razao_social = ?, cnpj = ?, inscricao_estadual = ?, contato = ?";
    }

    @Override
    protected String getSelectColumns() {
        return "id, nome, fone, fone2, email, cep, logradouro, bairro, cidade, complemento, " +
               "data_cadastro, cpf, rg, obs, status, sexo, razao_social, cnpj, " +
               "inscricao_estadual, contato";
    }

    @Override
    protected Fornecedor mapResultSetToEntity(ResultSet rst) throws SQLException {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(rst.getInt("id"));
        fornecedor.setNome(rst.getString("nome"));
        fornecedor.setFone1(rst.getString("fone"));
        fornecedor.setFone2(rst.getString("fone2"));
        fornecedor.setEmail(rst.getString("email"));
        fornecedor.setCep(rst.getString("cep"));
        fornecedor.setLogradouro(rst.getString("logradouro"));
        fornecedor.setBairro(rst.getString("bairro"));
        fornecedor.setCidade(rst.getString("cidade"));
        fornecedor.setComplemento(rst.getString("complemento"));
        fornecedor.setDataCadastro(rst.getString("data_cadastro"));
        fornecedor.setCpf(rst.getString("cpf"));
        fornecedor.setRg(rst.getString("rg"));
        fornecedor.setObs(rst.getString("obs"));
        fornecedor.setRazaoSocial(rst.getString("razao_social"));
        fornecedor.setCnpj(rst.getString("cnpj"));
        fornecedor.setInscricaoEstadual(rst.getString("inscricao_estadual"));
        fornecedor.setContato(rst.getString("contato"));
        fornecedor.setStatus(rst.getString("status").charAt(0));
        fornecedor.setSexo(rst.getString("sexo").charAt(0));
        return fornecedor;
    }

    @Override
    protected void setCreateParameters(PreparedStatement pstm, Fornecedor objeto) throws SQLException {
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
        pstm.setString(16, objeto.getRazaoSocial());
        pstm.setString(17, objeto.getCnpj());
        pstm.setString(18, objeto.getInscricaoEstadual());
        pstm.setString(19, objeto.getContato());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement pstm, Fornecedor objeto) throws SQLException {
        setCreateParameters(pstm, objeto);
        pstm.setInt(20, objeto.getId());
    }
}