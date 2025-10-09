package model;

public class CheckHospede {
    private int id;
    private String tipoHospede;
    private String obs;
    private Status status;
    private Check check;
    private Hospede hospede;

    public CheckHospede() {
        this.status = Status.ATIVO;
    }

    public CheckHospede(int id, String tipoHospede, String obs, Status status, Check check, Hospede hospede) {
        this.id = id;
        this.tipoHospede = tipoHospede;
        this.obs = obs;
        this.status = status;
        this.check = check;
        this.hospede = hospede;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoHospede() {
        return tipoHospede;
    }

    public void setTipoHospede(String tipoHospede) {
        this.tipoHospede = tipoHospede;
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

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    @Override
    public String toString() {
        return "CheckHospede [id=" + id + ", tipoHospede=" + tipoHospede + ", obs=" + obs + ", status=" + status + ", check=" + check + ", hospede=" + hospede + "]";
    }
}