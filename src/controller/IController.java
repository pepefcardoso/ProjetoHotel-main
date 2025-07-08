package controller;

import java.awt.event.ActionListener;

public interface IController extends ActionListener {

    public void novo();

    public void gravar();

    public void cancelar();

    public void buscar();

    public void sair();

    public void preencherObjeto();

    public void preencherTela(Object objeto);
}
