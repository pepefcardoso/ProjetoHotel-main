package controller;

import java.math.BigDecimal;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Produto;
import service.ProdutoService;
import utilities.Utilities;
import view.TelaBuscaProduto;
import view.TelaCadastroProduto;

public final class ControllerCadProduto extends AbstractControllerCad<Produto, TelaCadastroProduto> {

    public ControllerCadProduto(TelaCadastroProduto view) {
        super(view, new ProdutoService());
        Utilities.permiteLimparFormattedField(this.view.getjFormattedTextFieldValor());
    }

    @Override
    public boolean isFormularioValido() {
        if (!validarCampoObrigatorio(view.getjTextFieldDescricao(), "Descrição")) {
            return false;
        } else if (!validarCampoObrigatorio(view.getjFormattedTextFieldValor(), "Valor")) {
            return false;
        }
        return true;
    }

    @Override
    public Produto construirDoFormulario() {
        Produto produto = new Produto();
        
        String valorText = view.getjFormattedTextFieldValor().getText()
                               .replaceAll("\\.", "")
                               .replaceAll(",", ".");

        produto.setDescricao(view.getjTextFieldDescricao().getText());
        produto.setObs(view.getjTextFieldObservacao().getText());
        produto.setValor(new BigDecimal(valorText)); 
        produto.setStatus(getStatusDoFormulario());
        return produto;
    }

    @Override
    protected void preencherFormulario(Produto produto) {
        view.getjTextFieldDescricao().setText(produto.getDescricao());
        view.getjTextFieldObservacao().setText(produto.getObs());
        view.getjFormattedTextFieldValor().setText(String.valueOf(produto.getValor()));
        view.getjComboBoxStatus().setSelectedItem(produto.getStatus() == 'A' ? "Ativo" : "Inativo");
    }

    @Override
    protected void setId(Produto entidade, int id) {
        entidade.setId(id);
    }
    
    @Override
    protected JDialog criarTelaBusca() {
        return new TelaBuscaProduto(null, true);
    }

    @Override
    protected void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback) {
        new ControllerBuscaProduto((TelaBuscaProduto) telaBusca, callback);
    }

    @Override
    protected JTextField getTextFieldId() {
        return view.getjTextFieldId();
    }

    @Override
    protected JComboBox<String> getComboBoxStatus() {
        return view.getjComboBoxStatus();
    }

    @Override
    protected JPanel getPanelBotoes() {
        return view.getjPanelBotoes();
    }

    @Override
    protected JPanel getPanelDados() {
        return view.getjPanelDados();
    }

    @Override
    protected JButton getButtonNovo() {
        return view.getjButtonNovo();
    }

    @Override
    protected JButton getButtonCancelar() {
        return view.getjButtonCancelar();
    }

    @Override
    protected JButton getButtonGravar() {
        return view.getjButtonGravar();
    }

    @Override
    protected JButton getButtonBuscar() {
        return view.getjButtonBuscar();
    }

    @Override
    protected JButton getButtonSair() {
        return view.getjButtonSair();
    }

    @Override
    protected void focarPrimeiroCampo() {
        view.getjTextFieldDescricao().requestFocus();
    }
}