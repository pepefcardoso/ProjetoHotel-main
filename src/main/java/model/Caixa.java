package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "caixa")
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "valor_de_abertura", precision = 10, scale = 2)
    private BigDecimal valorDeAbertura;

    @Column(name = "valor_de_fechamento", precision = 10, scale = 2)
    private BigDecimal valorDeFechamento;

    @Column(name = "data_hora_abertura")
    private LocalDateTime dataHoraAbertura;

    @Column(name = "data_hora_fechamento")
    private LocalDateTime dataHoraFechamento;

    @Column(columnDefinition = "TEXT")
    private String obs;

    @Column(length = 1)
    private char status;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    public Caixa() {
    }

    public Caixa(int id, BigDecimal valorDeAbertura, BigDecimal valorDeFechamento, LocalDateTime dataHoraAbertura, LocalDateTime dataHoraFechamento, String obs, char status, Funcionario funcionario) {
        this.id = id;
        this.valorDeAbertura = valorDeAbertura;
        this.valorDeFechamento = valorDeFechamento;
        this.dataHoraAbertura = dataHoraAbertura;
        this.dataHoraFechamento = dataHoraFechamento;
        this.obs = obs;
        this.status = status;
        this.funcionario = funcionario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getValorDeAbertura() {
        return valorDeAbertura;
    }

    public void setValorDeAbertura(BigDecimal valorDeAbertura) {
        this.valorDeAbertura = valorDeAbertura;
    }

    public BigDecimal getValorDeFechamento() {
        return valorDeFechamento;
    }

    public void setValorDeFechamento(BigDecimal valorDeFechamento) {
        this.valorDeFechamento = valorDeFechamento;
    }

    public LocalDateTime getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public LocalDateTime getDataHoraFechamento() {
        return dataHoraFechamento;
    }

    public void setDataHoraFechamento(LocalDateTime dataHoraFechamento) {
        this.dataHoraFechamento = dataHoraFechamento;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        return "id                   = " + this.getId()
                + "\nvalorDeAbertura    = " + this.getValorDeAbertura()
                + "\nvalorDeFechamento  = " + this.getValorDeFechamento()
                + "\ndataHoraAbertura   = " + this.getDataHoraAbertura()
                + "\ndataHoraFechamento = " + this.getDataHoraFechamento()
                + "\nobs                = " + this.getObs()
                + "\nstatus             = " + this.getStatus()
                + "\nfuncionario        = " + (this.getFuncionario() != null ? this.getFuncionario().getNome() : "null");
    }
}