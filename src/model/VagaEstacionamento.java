package model;

public class VagaEstacionamento {

    private int id;
    private String descricao;
    private String obs;
    private float metragemVaga;
    private Status status;

    public VagaEstacionamento() {
        this.status = Status.ATIVO;
    }

    public VagaEstacionamento(int id, String descricao, String obs, float metragemVaga, Status status) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VagaEstacionamento [id=" + id + ", descricao=" + descricao + ", metragem=" + metragemVaga + ", status=" + status + "]";
    }
}
