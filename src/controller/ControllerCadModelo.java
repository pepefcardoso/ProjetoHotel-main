package controller;

import javax.swing.JOptionPane;
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
        TelaCadastroModelo telaModelo = (TelaCadastroModelo) this.tela;

        if (!telaModelo.getjTextFieldId().getText().isEmpty()) {
            try {
                modelo.setId(Integer.parseInt(telaModelo.getjTextFieldId().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(tela, "O ID do modelo é inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro de validação no ID do Modelo.");
            }
        }

        modelo.setDescricao(telaModelo.getjTextFieldDescricao().getText());

        if (telaModelo.getjComboBoxStatus().getSelectedIndex() == 0) {
            modelo.setStatus(Status.ATIVO);
        } else {
            modelo.setStatus(Status.INATIVO);
        }

        Object itemSelecionado = telaModelo.getjComboBoxMarca().getSelectedItem();
        if (itemSelecionado instanceof Marca) {
            modelo.setMarca((Marca) itemSelecionado);
        } else {
            JOptionPane.showMessageDialog(tela, "Por favor, selecione uma marca.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException("Marca não selecionada.");
        }

        System.out.println("Modelo a ser salvo: " + modelo);
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
