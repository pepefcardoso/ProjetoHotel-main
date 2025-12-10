package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String placa;
    private String cor;

    @Column(length = 1)
    private char status;

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "hospede_id")
    private Hospede hospede;

    public Veiculo() {
    }

    public Veiculo(int id, String placa, String cor, char status, Modelo modelo, Pessoa proprietario) {
        this.id = id;
        this.placa = placa;
        this.cor = cor;
        this.status = status;
        this.modelo = modelo;
        this.setProprietario(proprietario);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Transient
    public Pessoa getProprietario() {
        if (this.funcionario != null) {
            return this.funcionario;
        }
        if (this.fornecedor != null) {
            return this.fornecedor;
        }
        if (this.hospede != null) {
            return this.hospede;
        }
        return null;
    }

    @Transient
    public void setProprietario(Pessoa proprietario) {
        this.funcionario = null;
        this.fornecedor = null;
        this.hospede = null;

        if (proprietario instanceof Funcionario) {
            this.funcionario = (Funcionario) proprietario;
        } else if (proprietario instanceof Fornecedor) {
            this.fornecedor = (Fornecedor) proprietario;
        } else if (proprietario instanceof Hospede) {
            this.hospede = (Hospede) proprietario;
        }
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    @Override
    public String toString() {
        String proprietarioStr = getProprietario() != null
                ? getProprietario().getClass().getSimpleName() + " (ID: " + getProprietario().getId() + ")"
                : "null";

        return "id       = " + id
                + "\nplaca  = " + placa
                + "\ncor    = " + cor
                + "\nstatus = " + status
                + "\nmodelo = " + (modelo != null ? modelo.getDescricao() : "null")
                + "\nProprietário = " + proprietarioStr;
    }
}
