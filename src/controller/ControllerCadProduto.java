package controller;

import java.math.BigDecimal;
import javax.swing.JOptionPane;
import model.Produto;
import model.Status;
import view.TelaCadastroProduto;
import view.buscas.TelaBuscaProduto;

public class ControllerCadProduto extends ControllerCadAbstract {

    public ControllerCadProduto(TelaCadastroProduto telaCadastroProduto) {
        super(telaCadastroProduto, telaCadastroProduto.getjPanelBotoes(), telaCadastroProduto.getjPanelDados());
    }

    @Override
    public void buscar() {
        TelaBuscaProduto telaBuscaProduto = new TelaBuscaProduto(null, true);
        ControllerBuscaProduto controllerBuscaProduto = new ControllerBuscaProduto(telaBuscaProduto);
        telaBuscaProduto.setVisible(true);
    }

    @Override
    public void preencherObjeto() {
        Produto produto = new Produto();
        TelaCadastroProduto telaProduto = (TelaCadastroProduto) this.tela;

        if (!telaProduto.getjTextFieldId().getText().isEmpty()) {
            try {
                produto.setId(Integer.parseInt(telaProduto.getjTextFieldId().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(tela, "O ID do produto é inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Erro de validação no ID do Produto.");
            }
        }

        produto.setDescricao(telaProduto.getjTextFieldDescricao().getText());
        produto.setObs(telaProduto.getjTextFieldObservacao().getText());

        try {
            String valorTexto = telaProduto.getjFormattedTextFieldValor().getText().replace(".", "").replace(",", ".");

            if (!valorTexto.isEmpty()) {
                produto.setValor(new BigDecimal(valorTexto));
            } else {
                produto.setValor(BigDecimal.ZERO);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(tela, "O campo 'Valor' deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro de validação no campo Valor.");
        }

        if (telaProduto.getjComboBoxStatus().getSelectedIndex() == 0) {
            produto.setStatus(Status.ATIVO);
        } else {
            produto.setStatus(Status.INATIVO);
        }

        System.out.println("Produto a ser salvo: " + produto);
    }

    @Override
    public void preencherTela(Object objeto) {
        Produto produto = (Produto) objeto;
        ((TelaCadastroProduto) tela).getjTextFieldId().setText(String.valueOf(produto.getId()));
        ((TelaCadastroProduto) tela).getjTextFieldDescricao().setText(produto.getDescricao());
        ((TelaCadastroProduto) tela).getjTextFieldObservacao().setText(produto.getObs());
        ((TelaCadastroProduto) tela).getjFormattedTextFieldValor().setText(produto.getValor().toPlainString());

        if (produto.getStatus() == Status.ATIVO) {
            ((TelaCadastroProduto) tela).getjComboBoxStatus().setSelectedIndex(0);
        } else {
            ((TelaCadastroProduto) tela).getjComboBoxStatus().setSelectedIndex(1);
        }
    }
}
