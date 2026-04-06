package service;

import model.DAO.ReservaDAO;
import model.Reserva;

public class ReservaService extends AbstractService<Reserva, ReservaDAO> {

    public ReservaService() {
        super(new ReservaDAO());
    }
}