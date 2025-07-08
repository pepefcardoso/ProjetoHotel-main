package controller;

import model.Marca;
import model.Modelo;
import model.Status;
import view.TelaCadastroModelo;
import view.buscas.TelaBuscaModelo;

public class ControllerCadModelo extends ControllerCadAbstract {

    public ControllerCadModelo(TelaCadastroModelo telaCadastroModelo) {
        super(telaCadastroModelo, telaCadastroModelo.getjPanelBotoes(), telaCadastroModelo.getjPanelDados());
    }

    @Override
    public void buscar() {
        TelaBuscaModelo telaBuscaModelo = new TelaBuscaModelo(null, true);
        ControllerBuscaModelo controllerBuscaModelo = new ControllerBuscaModelo(telaBuscaModelo);
        telaBuscaModelo.setVisible(true);
    }

    @Override
    public void preencherObjeto() {
        Modelo modelo = new Modelo();
        modelo.setId(Integer.parseInt(((TelaCadastroModelo) tela).getjTextFieldId().getText()));
        modelo.setDescricao(((TelaCadastroModelo) tela).getjTextFieldDescricao().getText());
        
        if (((TelaCadastroModelo) tela).getjComboBoxStatus().getSelectedIndex() == 0) {
            modelo.setStatus(Status.ATIVO);
        } else {
            modelo.setStatus(Status.INATIVO);
        }
        
        Marca marca = (Marca) ((TelaCadastroModelo) tela).getjComboBoxMarca().getSelectedItem();
        modelo.setMarca(marca);
    }

    @Override
    public void preencherTela(Object objeto) {
        Modelo modelo = (Modelo) objeto;
        ((TelaCadastroModelo) tela).getjTextFieldId().setText(String.valueOf(modelo.getId()));
        ((TelaCadastroModelo) tela).getjTextFieldDescricao().setText(modelo.getDescricao());

        if (modelo.getStatus() == Status.ATIVO) {
            ((TelaCadastroModelo) tela).getjComboBoxStatus().setSelectedIndex(0);
        } else {
            ((TelaCadastroModelo) tela).getjComboBoxStatus().setSelectedIndex(1);
        }
        
        ((TelaCadastroModelo) tela).getjComboBoxMarca().setSelectedItem(modelo.getMarca());
    }
}