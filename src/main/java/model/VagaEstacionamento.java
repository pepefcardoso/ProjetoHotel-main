package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vaga_estacionamento")
public class VagaEstacionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descricao;

    @Column(columnDefinition = "TEXT")
    private String obs;

    @Column(name = "metragem_vaga")
    private float metragemVaga;

    @Column(length = 1)
    private char status;

    public VagaEstacionamento() {
    }

    public VagaEstacionamento(int id, String descricao, String obs, float metragemVaga, char status) {
        this.id = id;
        this.descricao = descricao;
        this.obs = obs;
        this.metragemVaga = metragemVaga;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public float getMetragemVaga() {
        return metragemVaga;
    }

    public void setMetragemVaga(float metragemVaga) {
        this.metragemVaga = metragemVaga;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "id             = " + id
                + "\ndescricao    = " + descricao
                + "\nobs          = " + obs
                + "\nmetragemVaga = " + metragemVaga
                + "\nstatus       = " + status;
    }
}
