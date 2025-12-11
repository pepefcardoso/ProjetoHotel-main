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
@Table(name = "ordem_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_cadastro")
    private LocalDateTime dataHoraCadastro;

    @Column(name = "data_hora_prevista_inicio")
    private LocalDateTime dataHoraPrevistaInicio;

    @Column(name = "data_hora_prevista_termino")
    private LocalDateTime dataHoraPrevistaTermino;

    @Column(columnDefinition = "TEXT")
    private String obs;

    @Column(length = 1)
    private char status;

    @ManyToOne
    @JoinColumn(name = "check_id")
    private Check check;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    public OrdemServico() {
    }

    public OrdemServico(int id, LocalDateTime dataHoraCadastro, LocalDateTime dataHoraPrevistaInicio,
            LocalDateTime dataHoraPrevistaTermino, String obs, char status,
            Check check, Servico servico, Quarto quarto) {
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.dataHoraPrevistaInicio = dataHoraPrevistaInicio;
        this.dataHoraPrevistaTermino = dataHoraPrevistaTermino;
        this.obs = obs;
        this.status = status;
        this.check = check;
        this.servico = servico;
        this.quarto = quarto;
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

    public LocalDateTime getDataHoraPrevistaInicio() {
        return dataHoraPrevistaInicio;
    }

    public void setDataHoraPrevistaInicio(LocalDateTime dataHoraPrevistaInicio) {
        this.dataHoraPrevistaInicio = dataHoraPrevistaInicio;
    }

    public LocalDateTime getDataHoraPrevistaTermino() {
        return dataHoraPrevistaTermino;
    }

    public void setDataHoraPrevistaTermino(LocalDateTime dataHoraPrevistaTermino) {
        this.dataHoraPrevistaTermino = dataHoraPrevistaTermino;
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

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    @Override
    public String toString() {
        return "id                      = " + this.getId()
                + "\nData/Hora Cadastro     = " + this.getDataHoraCadastro()
                + "\nData/Hora Prev. Início = " + this.getDataHoraPrevistaInicio()
                + "\nData/Hora Prev. Término= " + this.getDataHoraPrevistaTermino()
                + "\nObs                    = " + this.getObs()
                + "\nStatus                 = " + this.getStatus()
                + "\nCheck ID               = " + (this.getCheck() != null ? this.getCheck().getId() : "null")
                + "\nServico                = " + (this.getServico() != null ? this.getServico().getDescricao() : "null")
                + "\nQuarto                 = " + (this.getQuarto() != null ? this.getQuarto().getIdentificacao() : "null");
    }
}
