package controller;

import javax.swing.JOptionPane;
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
        TelaCadastroQuarto telaQuarto = (TelaCadastroQuarto) this.tela;

        if (!telaQuarto.getjTextFieldId().getText().isEmpty()) {
            try {
                quarto.setId(Integer.parseInt(telaQuarto.getjTextFieldId().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(tela, "O ID do quarto é inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro de validação no ID do Quarto.");
            }
        }

        quarto.setDescricao(telaQuarto.getjTextFieldDescricao().getText());
        quarto.setObs(telaQuarto.getjTextFieldObservacao().getText());
        quarto.setIdentificacao(telaQuarto.getjTextFieldDescricaoidentificacao().getText());
        quarto.setFlagAnimais(telaQuarto.getjCheckBoxFlagAnimais().isSelected());
        try {
            String capacidadeTexto = telaQuarto.getjFormattedTextFieldCapacidade().getText();
            quarto.setCapacidadeHospedes(!capacidadeTexto.isEmpty() ? Integer.parseInt(capacidadeTexto) : 0);

            String metragemTexto = telaQuarto.getjFormattedTextFieldMetragem().getText();
            quarto.setMetragem(!metragemTexto.isEmpty() ? Float.parseFloat(metragemTexto.replace(",", ".")) : 0.0f);

            String andarTexto = telaQuarto.getjFormattedTextFieldAndar().getText();
            quarto.setAndar(!andarTexto.isEmpty() ? Integer.parseInt(andarTexto) : 0);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(tela, "Os campos 'Capacidade', 'Metragem' e 'Andar' devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro de validação nos campos numéricos do Quarto.");
        }

        if (telaQuarto.getjComboBoxStatus().getSelectedIndex() == 0) {
            quarto.setStatus(Status.ATIVO);
        } else {
            quarto.setStatus(Status.INATIVO);
        }

        System.out.println("Quarto a ser salvo: " + quarto);
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
