package model;

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
@Table(name = "receber")
public class Receber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_cadastro")
    private LocalDateTime dataHoraCadastro;

    @Column(name = "valor_original")
    private Double valorOriginal;

    private Double desconto;

    private Double acrescimo;

    @Column(name = "valor_pago")
    private Double valorPago;

    @Column(columnDefinition = "TEXT")
    private String obs;

    @Column(length = 1)
    private char status;

    @ManyToOne
    @JoinColumn(name = "check_id")
    private Check check;

    public Receber() {
    }

    public Receber(int id, LocalDateTime dataHoraCadastro, Double valorOriginal, Double desconto, Double acrescimo, Double valorPago, String obs, char status, Check check) {
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.valorOriginal = valorOriginal;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.valorPago = valorPago;
        this.obs = obs;
        this.status = status;
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public Double getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(Double valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(Double acrescimo) {
        this.acrescimo = acrescimo;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
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

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "id               = " + this.getId()
                + "\nData/Hora Cad.  = " + this.getDataHoraCadastro()
                + "\nValor Original  = " + this.getValorOriginal()
                + "\nDesconto        = " + this.getDesconto()
                + "\nAcréscimo       = " + this.getAcrescimo()
                + "\nValor Pago      = " + this.getValorPago()
                + "\nObs             = " + this.getObs()
                + "\nStatus          = " + this.getStatus()
                + "\nCheck ID        = " + (this.getCheck() != null ? this.getCheck().getId() : "null");
    }
}
