package model;

import java.util.StringJoiner;

public class PessoaJuridica extends Pessoa {

    private String cnpj;
    private String inscricaoEstadual;
    private String razaoSocial;

    public PessoaJuridica() {
        super();
    }

    public PessoaJuridica(int id, String nome, String fone1, String fone2, String email, String cep, String logradouro, String bairro, String cidade, String complemento, String dataCadastro, String obs, Status status, String cnpj, String inscricaoEstadual, String razaoSocial) {
        super(id, nome, fone1, fone2, email, cep, logradouro, bairro, cidade, complemento, dataCadastro, obs, status);
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PessoaJuridica.class.getSimpleName() + "[", "]")
                .add("pessoa=" + super.toString())
                .add("cnpj='" + cnpj + "'")
                .add("razaoSocial='" + razaoSocial + "'")
                .toString();
    }
}
