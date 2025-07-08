package controller;

import model.Quarto;
import model.Status;
import view.TelaCadastroQuarto;
import view.buscas.TelaBuscaQuarto;

public class ControllerCadQuarto extends ControllerCadAbstract {

    public ControllerCadQuarto(TelaCadastroQuarto telaCadastroQuarto) {
        super(telaCadastroQuarto, telaCadastroQuarto.getjPanelBotoes(), telaCadastroQuarto.getjPanelDados());
    }

    @Override
    public void buscar() {
        TelaBuscaQuarto telaBuscaQuarto = new TelaBuscaQuarto(null, true);
        ControllerBuscaQuarto controllerBuscaQuarto = new ControllerBuscaQuarto(telaBuscaQuarto);
        telaBuscaQuarto.setVisible(true);
    }

    @Override
    public void preencherObjeto() {
        Quarto quarto = new Quarto();
        quarto.setId(Integer.parseInt(((TelaCadastroQuarto) tela).getjTextFieldId().getText()));
        quarto.setDescricao(((TelaCadastroQuarto) tela).getjTextFieldDescricao().getText());
        quarto.setObs(((TelaCadastroQuarto) tela).getjTextFieldObservacao().getText());
        quarto.setCapacidadeHospedes(Integer.parseInt(((TelaCadastroQuarto) tela).getjFormattedTextFieldCapacidade().getText()));
        quarto.setMetragem(Float.parseFloat(((TelaCadastroQuarto) tela).getjFormattedTextFieldMetragem().getText()));
        quarto.setAndar(Integer.parseInt(((TelaCadastroQuarto) tela).getjFormattedTextFieldAndar().getText()));
        quarto.setIdentificacao(((TelaCadastroQuarto) tela).getjTextFieldDescricaoidentificacao().getText());
        quarto.setFlagAnimais(((TelaCadastroQuarto) tela).getjCheckBoxFlagAnimais().isSelected());

        if (((TelaCadastroQuarto) tela).getjComboBoxStatus().getSelectedIndex() == 0) {
            quarto.setStatus(Status.ATIVO);
        } else {
            quarto.setStatus(Status.INATIVO);
        }
    }

    @Override
    public void preencherTela(Object objeto) {
        Quarto quarto = (Quarto) objeto;
        ((TelaCadastroQuarto) tela).getjTextFieldId().setText(String.valueOf(quarto.getId()));
        ((TelaCadastroQuarto) tela).getjTextFieldDescricao().setText(quarto.getDescricao());
        ((TelaCadastroQuarto) tela).getjTextFieldObservacao().setText(quarto.getObs());
        ((TelaCadastroQuarto) tela).getjFormattedTextFieldCapacidade().setText(String.valueOf(quarto.getCapacidadeHospedes()));
        ((TelaCadastroQuarto) tela).getjFormattedTextFieldMetragem().setText(String.valueOf(quarto.getMetragem()));
        ((TelaCadastroQuarto) tela).getjFormattedTextFieldAndar().setText(String.valueOf(quarto.getAndar()));
        ((TelaCadastroQuarto) tela).getjTextFieldDescricaoidentificacao().setText(quarto.getIdentificacao());
        ((TelaCadastroQuarto) tela).getjCheckBoxFlagAnimais().setSelected(quarto.isFlagAnimais());

        if (quarto.getStatus() == Status.ATIVO) {
            ((TelaCadastroQuarto) tela).getjComboBoxStatus().setSelectedIndex(0);
        } else {
            ((TelaCadastroQuarto) tela).getjComboBoxStatus().setSelectedIndex(1);
        }
    }
}
