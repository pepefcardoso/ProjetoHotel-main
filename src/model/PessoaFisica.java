package model;

import java.util.StringJoiner;

public class PessoaFisica extends Pessoa {

    private String cpf;
    private String rg;

    public PessoaFisica() {
        super();
    }

    public PessoaFisica(int id, String nome, String fone1, String fone2, String email, String cep, String logradouro, String bairro, String cidade, String complemento, String dataCadastro, String obs, Status status, String cpf, String rg) {
        super(id, nome, fone1, fone2, email, cep, logradouro, bairro, cidade, complemento, dataCadastro, obs, status);
        this.cpf = cpf;
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PessoaFisica.class.getSimpleName() + "[", "]")
                .add("pessoa=" + super.toString())
                .add("cpf='" + cpf + "'")
                .add("rg='" + rg + "'")
                .toString();
    }
}
