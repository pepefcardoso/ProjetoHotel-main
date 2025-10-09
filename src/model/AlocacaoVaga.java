package model;

public class AlocacaoVaga {
    private int id;
    private String obs;
    private Status status;
    private Check check;
    private Veiculo veiculo;
    private VagaEstacionamento vagaEstacionamento;

    public AlocacaoVaga() {
        this.status = Status.ATIVO;
    }

    public AlocacaoVaga(int id, String obs, Status status, Check check, Veiculo veiculo, VagaEstacionamento vagaEstacionamento) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
        return "AlocacaoVaga [id=" + id + ", obs=" + obs + ", status=" + status + ", check=" + check + ", veiculo=" + veiculo + ", vagaEstacionamento=" + vagaEstacionamento + "]";
    }
}