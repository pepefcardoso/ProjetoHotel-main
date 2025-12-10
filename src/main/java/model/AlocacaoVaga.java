package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alocacao_vaga")
public class AlocacaoVaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String obs;

    @Column(length = 1)
    private char status;

    @ManyToOne
    @JoinColumn(name = "check_id")
    private Check check;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "vaga_estacionamento_id")
    private VagaEstacionamento vagaEstacionamento;

    public AlocacaoVaga() {
    }

    public AlocacaoVaga(int id, String obs, char status, Check check, Veiculo veiculo, VagaEstacionamento vagaEstacionamento) {
        this.id = id;
        this.obs = obs;
        this.status = status;
        this.check = check;
        this.veiculo = veiculo;
        this.vagaEstacionamento = vagaEstacionamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public VagaEstacionamento getVagaEstacionamento() {
        return vagaEstacionamento;
    }

    public void setVagaEstacionamento(VagaEstacionamento vagaEstacionamento) {
        this.vagaEstacionamento = vagaEstacionamento;
    }

    @Override
    public String toString() {
        return "id             = " + this.getId()
                + "\nObs            = " + this.getObs()
                + "\nStatus         = " + this.getStatus()
                + "\nCheck ID       = " + (this.getCheck() != null ? this.getCheck().getId() : "null")
                + "\nPlaca Veículo  = " + (this.getVeiculo() != null ? this.getVeiculo().getPlaca() : "null")
                + "\nVaga Estac. ID = " + (this.getVagaEstacionamento() != null ? this.getVagaEstacionamento().getId() : "null");
    }
}
