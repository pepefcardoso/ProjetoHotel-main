package model;

public class Modelo {

    private int id;
    private String descricao;
    private Status status;
    private Marca marca;

    public Modelo() {
        this.status = Status.ATIVO;
    }

    public Modelo(int id, String descricao, Status status, Marca marca) {
        this.id = id;
        this.setDescricao(descricao);
        this.status = status;
        this.marca = marca;
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
         if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode ser nula ou vazia.");
        }
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
    
    @Override
    public String toString() {
        return "Modelo [id=" + id + ", descricao=" + descricao + ", status=" + status + ", marca=" + marca.getDescricao() + "]";
    }
}