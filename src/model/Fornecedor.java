package model;

import java.util.StringJoiner;

public class Fornecedor extends PessoaJuridica {

    private String contato;

    public Fornecedor() {
        super();
    }

    public Fornecedor(int id, String nome, String fone1, String fone2, String email, String cep, String logradouro, String bairro, String cidade, String complemento, String dataCadastro, String obs, Status status, String cnpj, String inscricaoEstadual, String razaoSocial, String contato) {
        super(id, nome, fone1, fone2, email, cep, logradouro, bairro, cidade, complemento, dataCadastro, obs, status, cnpj, inscricaoEstadual, razaoSocial);
        this.contato = contato;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Fornecedor.class.getSimpleName() + "[", "]")
                .add("pessoaJuridica=" + super.toString())
                .add("contato='" + contato + "'")
                .toString();
    }
}