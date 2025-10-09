package service;

import java.sql.SQLException;
import java.util.List;
import model.DAO.OrdemServicoDAO;
import model.OrdemServico;

public class OrdemServicoService {

    public static void Criar(OrdemServico objeto) throws SQLException {
        OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
        ordemServicoDAO.Create(objeto);
    }

    public static OrdemServico Carregar(int id) throws SQLException {
        OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
        return ordemServicoDAO.Retrieve(id);
    }

    public static List<OrdemServico> Carregar(String atributo, String valor) throws SQLException {
        OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
        return ordemServicoDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(OrdemServico objeto) throws SQLException {
        OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
        ordemServicoDAO.Update(objeto);
    }

    public static void Apagar(OrdemServico objeto) throws SQLException {
        OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
        ordemServicoDAO.Delete(objeto);
    }
}