package model;

import java.util.StringJoiner;

public class Hospede extends PessoaFisica {
    
    private String contato;

    public Hospede() {
        super();
    }

    public Hospede(int id, String nome, String fone1, String fone2, String email, String cep, String logradouro, String bairro, String cidade, String complemento, String dataCadastro, String obs, Status status, String cpf, String rg, String contato) {
        super(id, nome, fone1, fone2, email, cep, logradouro, bairro, cidade, complemento, dataCadastro, obs, status, cpf, rg);
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
        return new StringJoiner(", ", Hospede.class.getSimpleName() + "[", "]")
                .add("pessoaFisica=" + super.toString())
                .add("contato='" + contato + "'")
                .toString();
    }
}