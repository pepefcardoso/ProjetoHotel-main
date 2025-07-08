package controller;

import java.math.BigDecimal;
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
        produto.setId(Integer.parseInt(((TelaCadastroProduto) tela).getjTextFieldId().getText()));
        produto.setDescricao(((TelaCadastroProduto) tela).getjTextFieldDescricao().getText());
        produto.setObs(((TelaCadastroProduto) tela).getjTextFieldObservacao().getText());

        String valorText = ((TelaCadastroProduto) tela).getjFormattedTextFieldValor().getText().replace(".", "").replace(",", ".");
        produto.setValor(new BigDecimal(valorText));

        if (((TelaCadastroProduto) tela).getjComboBoxStatus().getSelectedIndex() == 0) {
            produto.setStatus(Status.ATIVO);
        } else {
            produto.setStatus(Status.INATIVO);
        }
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