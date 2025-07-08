package model;

import java.util.StringJoiner;

public class Funcionario extends PessoaFisica {

    private String usuario;
    private String senha;

    public Funcionario() {
        super();
    }

    public Funcionario(int id, String nome, String fone1, String fone2, String email, String cep, String logradouro, String bairro, String cidade, String complemento, String dataCadastro, String obs, Status status, String cpf, String rg, String usuario, String senha) {
        super(id, nome, fone1, fone2, email, cep, logradouro, bairro, cidade, complemento, dataCadastro, obs, status, cpf, rg);
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Funcionario.class.getSimpleName() + "[", "]")
                .add("pessoaFisica=" + super.toString())
                .add("usuario='" + usuario + "'")
                .toString();
    }
}