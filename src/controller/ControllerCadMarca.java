package controller;

import javax.swing.JOptionPane;
import model.Marca;
import model.Status;
import view.TelaCadastroMarca;
import view.buscas.TelaBuscaMarca;

public class ControllerCadMarca extends ControllerCadAbstract {

    public ControllerCadMarca(TelaCadastroMarca telaCadastroMarca) {
        super(telaCadastroMarca, telaCadastroMarca.getjPanelBotoes(), telaCadastroMarca.getjPanelDados());

        utilities.Utilities.ativaDesativa(this.painelBotoes, true);
        utilities.Utilities.limpaComponentes(this.painelDados, false);
    }

    @Override
    public void buscar() {
        TelaBuscaMarca telaBuscaMarca = new TelaBuscaMarca(null, true);
        ControllerBuscaMarca controllerBuscaMarca = new ControllerBuscaMarca(telaBuscaMarca);
        telaBuscaMarca.setVisible(true);
    }

    @Override
    public void preencherObjeto() {
        Marca marca = new Marca();
        TelaCadastroMarca telaMarca = (TelaCadastroMarca) this.tela;

        if (!telaMarca.getjTextFieldId().getText().isEmpty()) {
            try {
                marca.setId(Integer.parseInt(telaMarca.getjTextFieldId().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(tela, "O ID da marca é inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro de validação no ID da Marca.");
            }
        }

        marca.setDescricao(telaMarca.getjTextFieldDescricao().getText());

        if (telaMarca.getjComboBoxStatus().getSelectedIndex() == 0) {
            marca.setStatus(Status.ATIVO);
        } else {
            marca.setStatus(Status.INATIVO);
        }

        System.out.println("Marca a ser salva: " + marca);
    }

    @Override
    public void preencherTela(Object objeto) {
        if (objeto instanceof Marca marca) {
            TelaCadastroMarca tela = (TelaCadastroMarca) this.tela;

            tela.getjTextFieldId().setText(String.valueOf(marca.getId()));
            tela.getjTextFieldDescricao().setText(marca.getDescricao());

            if (marca.getStatus() == Status.ATIVO) {
                tela.getjComboBoxStatus().setSelectedIndex(0);
            } else {
                tela.getjComboBoxStatus().setSelectedIndex(1);
            }
        }
    }
}
