package model;

public class Veiculo {

    private int id;
    private String placa;
    private String cor;
    private Status status;
    private Modelo modelo;

    public Veiculo() {
        this.status = Status.ATIVO;
    }

    public Veiculo(int id, String placa, String cor, Status status, Modelo modelo) {
        this.id = id;
        this.placa = placa;
        this.cor = cor;
        this.status = status;
        this.modelo = modelo;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "Veiculo [id=" + id + ", placa=" + placa + ", cor=" + cor + ", status=" + status + ", modelo=" + modelo.getDescricao() + "]";
    }
}
