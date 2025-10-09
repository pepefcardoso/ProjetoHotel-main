package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Hospede;

public class HospedeDAO extends BaseDAO<Hospede> {

    @Override
    protected String getTableName() {
        return "hospede";
    }

    @Override
    protected String getSelectColumns() {
        return "id, nome, fone, fone2, email, cep, logradouro, bairro, cidade, "
                + "complemento, data_cadastro, cpf, rg, obs, status, sexo, "
                + "razao_social, cnpj, inscricao_estadual, contato";
    }

    @Override
    protected String getCreateColumns() {
        return "nome, fone, fone2, email, cep, logradouro, bairro, cidade, "
                + "complemento, data_cadastro, cpf, rg, obs, status, sexo, "
                + "razao_social, cnpj, inscricao_estadual, contato";
    }

    @Override
    protected String getCreatePlaceholders() {
        return "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
    }

    @Override
    protected String getUpdateSetClause() {
        return "nome = ?, fone = ?, fone2 = ?, email = ?, cep = ?, logradouro = ?, "
                + "bairro = ?, cidade = ?, complemento = ?, data_cadastro = ?, "
                + "cpf = ?, rg = ?, obs = ?, status = ?, sexo = ?, razao_social = ?, "
                + "cnpj = ?, inscricao_estadual = ?, contato = ?";
    }

    @Override
    protected Hospede mapResultSetToEntity(ResultSet rst) throws SQLException {
        Hospede hospede = new Hospede();
        hospede.setId(rst.getInt("id"));
        hospede.setNome(rst.getString("nome"));
        hospede.setFone1(rst.getString("fone"));
        hospede.setFone2(rst.getString("fone2"));
        hospede.setEmail(rst.getString("email"));
        hospede.setCep(rst.getString("cep"));
        hospede.setLogradouro(rst.getString("logradouro"));
        hospede.setBairro(rst.getString("bairro"));
        hospede.setCidade(rst.getString("cidade"));
        hospede.setComplemento(rst.getString("complemento"));
        hospede.setDataCadastro(rst.getString("data_cadastro"));
        hospede.setCpf(rst.getString("cpf"));
        hospede.setRg(rst.getString("rg"));
        hospede.setObs(rst.getString("obs"));
        hospede.setStatus(rst.getString("status").charAt(0));
        hospede.setSexo(rst.getString("sexo").charAt(0));
        hospede.setRazaoSocial(rst.getString("razao_social"));
        hospede.setCnpj(rst.getString("cnpj"));
        hospede.setInscricaoEstadual(rst.getString("inscricao_estadual"));
        hospede.setContato(rst.getString("contato"));
        return hospede;
    }

    @Override
    protected void setCreateParameters(PreparedStatement pstm, Hospede objeto) throws SQLException {
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
    protected void setUpdateParameters(PreparedStatement pstm, Hospede objeto) throws SQLException {
        setCreateParameters(pstm, objeto);
        pstm.setInt(20, objeto.getId());
    }
}
