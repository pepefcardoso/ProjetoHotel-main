package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "fornecedor")
public class Fornecedor extends Pessoa {

    @Column(name = "razao_social")
    private String razaoSocial;

    private String cnpj;

    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

    private String contato;

    public static final String TIPO = "Fornecedor";

    public Fornecedor() {
    }

    public Fornecedor(int id, String nome, String fone1, String fone2, String email, String cep, String logradouro, String bairro, String cidade, String complemento, LocalDateTime dataCadastro, String cpf, String rg, String razaoSocial, String cnpj, String inscricaoEstadual, String contato, String obs, char status, char sexo) {
        super(id, nome, fone1, fone2, email, cep, logradouro, bairro, cidade, complemento, dataCadastro, cpf, rg, obs, status, sexo);
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.contato = contato;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
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

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\ncnpj   = " + this.getCnpj()
                + "\nRazão Social = " + this.getRazaoSocial()
                + "\nContato    = " + this.getContato()
                + "\nStatus     = " + this.getStatus();
    }
}
