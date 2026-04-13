package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

import model.CopaQuarto;
import model.Produto;
import model.Quarto;
import service.CopaQuartoService;
import service.ProdutoService;
import service.QuartoService;
import utilities.Utilities;
import view.TelaBuscaCopaQuarto;
import view.TelaBuscaProduto;
import view.TelaBuscaQuarto;
import view.TelaCadastroCopaQuarto;

public final class ControllerCadCopaQuarto implements ActionListener {

    private final TelaCadastroCopaQuarto view;
    private final CopaQuartoService copaQuartoService = new CopaQuartoService();
    private final QuartoService quartoService = new QuartoService();
    private final ProdutoService produtoService = new ProdutoService();

    private Quarto quartoSelecionado = null;
    private Produto produtoSelecionado = null;
    private int codigoAtual = 0;
    private boolean modoEdicao = false;

    public ControllerCadCopaQuarto(TelaCadastroCopaQuarto view) {
        this.view = view;
        inicializar();
        registrarListeners();
    }

    private void inicializar() {
        view.getjTextFieldId().setEnabled(false);
        view.getjComboBoxStatus().setEnabled(false);
        view.getjFormattedTextFieldQuarto().setEditable(false);
        view.getjFormattedTextFieldProduto().setEditable(false);
        view.getjFormattedTextFieldCadastro().setEditable(false);
        setModoEdicao(false);
    }

    private void registrarListeners() {
        view.getjButtonNovo().addActionListener(this);
        view.getjButtonCancelar().addActionListener(this);
        view.getjButtonGravar().addActionListener(this);
        view.getjButtonBuscar().addActionListener(this);
        view.getjButtonSair().addActionListener(this);
        view.getjButtonRelacionarQuarto().addActionListener(this);
        view.getjButtonRelacionarProduto().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if      (src == view.getjButtonNovo())               handleNovo();
        else if (src == view.getjButtonCancelar())           handleCancelar();
        else if (src == view.getjButtonGravar())             handleGravar();
        else if (src == view.getjButtonBuscar())             handleBuscar();
        else if (src == view.getjButtonSair())               view.dispose();
        else if (src == view.getjButtonRelacionarQuarto())   handleBuscarQuarto();
        else if (src == view.getjButtonRelacionarProduto())  handleBuscarProduto();
    }

    private void handleNovo() {
        modoEdicao = true;
        codigoAtual = 0;
        limparFormulario();
        setModoEdicao(true);
        view.getjFormattedTextFieldCadastro().setText(Utilities.getDataHoje());
        view.getjComboBoxStatus().setSelectedItem("Ativo");
        view.getjTextFieldQuantidade().setText("1");
        view.getjTextFieldQuantidade().requestFocus();
    }

    private void handleCancelar() {
        modoEdicao = false;
        codigoAtual = 0;
        limparFormulario();
        setModoEdicao(false);
    }

    private void handleGravar() {
        if (!validar()) return;

        try {
            int quantidade = Integer.parseInt(view.getjTextFieldQuantidade().getText().trim());

            CopaQuarto copa = new CopaQuarto();
            copa.setQuarto(quartoSelecionado);
            copa.setProduto(produtoSelecionado);
            copa.setQuantidade(quantidade);
            copa.setObs(view.getjTextFieldObservacao().getText().trim());
            copa.setStatus(statusAtual());
            copa.setDataHoraPedido(LocalDateTime.now());

            if (codigoAtual == 0) {
                copaQuartoService.Criar(copa);

                double totalItem = produtoSelecionado.getValor().doubleValue() * quantidade;
                mensagem("Pedido registrado com sucesso!\n\n"
                        + "Produto  : " + produtoSelecionado.getDescricao() + "\n"
                        + "Quarto   : " + quartoSelecionado.getIdentificacao() + " – " + quartoSelecionado.getDescricao() + "\n"
                        + "Quantidade: " + quantidade + "\n"
                        + "Valor unit.: R$ " + String.format("%.2f", produtoSelecionado.getValor().doubleValue()) + "\n"
                        + "Total item : R$ " + String.format("%.2f", totalItem));
            } else {
                copa.setId(codigoAtual);
                copaQuartoService.Atualizar(copa);
                mensagem("Pedido atualizado com sucesso!");
            }

            handleCancelar();

        } catch (NumberFormatException ex) {
            erro("Quantidade inválida. Informe um número inteiro positivo.");
        } catch (Exception ex) {
            erro("Erro ao salvar pedido: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleBuscar() {
        int[] holder = {0};
        TelaBuscaCopaQuarto tela = new TelaBuscaCopaQuarto(null, true);
        new ControllerBuscaCopaQuarto(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                CopaQuarto copa = copaQuartoService.Carregar(holder[0]);
                if (copa != null) {
                    codigoAtual = copa.getId();
                    modoEdicao = true;
                    limparFormulario();
                    setModoEdicao(true);

                    view.getjTextFieldId().setText(String.valueOf(copa.getId()));
                    view.getjComboBoxStatus().setSelectedItem(copa.getStatus() == 'A' ? "Ativo" : "Inativo");

                    if (copa.getQuarto() != null) {
                        quartoSelecionado = copa.getQuarto();
                        view.getjFormattedTextFieldQuarto().setText(
                                copa.getQuarto().getIdentificacao() + " – " + copa.getQuarto().getDescricao());
                    }
                    if (copa.getProduto() != null) {
                        produtoSelecionado = copa.getProduto();
                        view.getjFormattedTextFieldProduto().setText(
                                copa.getProduto().getDescricao() + " – R$ " +
                                String.format("%.2f", copa.getProduto().getValor().doubleValue()));
                    }

                    view.getjTextFieldQuantidade().setText(String.valueOf(copa.getQuantidade()));
                    view.getjTextFieldObservacao().setText(copa.getObs() != null ? copa.getObs() : "");

                    if (copa.getDataHoraPedido() != null) {
                        view.getjFormattedTextFieldCadastro().setText(Utilities.formatarData(copa.getDataHoraPedido()));
                    }
                }
            } catch (Exception ex) {
                erro("Erro ao carregar pedido: " + ex.getMessage());
            }
        }
    }

    private void handleBuscarQuarto() {
        if (!modoEdicao) return;
        int[] holder = {0};
        TelaBuscaQuarto tela = new TelaBuscaQuarto(null, true);
        new ControllerBuscaQuarto(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Quarto q = quartoService.Carregar(holder[0]);
                if (q != null) {
                    quartoSelecionado = q;
                    view.getjFormattedTextFieldQuarto().setText(q.getIdentificacao() + " – " + q.getDescricao());
                }
            } catch (Exception ex) {
                erro("Erro ao carregar quarto: " + ex.getMessage());
            }
        }
    }

    private void handleBuscarProduto() {
        if (!modoEdicao) return;
        int[] holder = {0};
        TelaBuscaProduto tela = new TelaBuscaProduto(null, true);
        new ControllerBuscaProduto(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Produto p = produtoService.Carregar(holder[0]);
                if (p != null) {
                    produtoSelecionado = p;
                    view.getjFormattedTextFieldProduto().setText(
                            p.getDescricao() + " – R$ " + String.format("%.2f", p.getValor().doubleValue()));
                }
            } catch (Exception ex) {
                erro("Erro ao carregar produto: " + ex.getMessage());
            }
        }
    }

    private boolean validar() {
        if (quartoSelecionado == null) {
            mensagem("Selecione um Quarto antes de gravar.");
            return false;
        }
        if (produtoSelecionado == null) {
            mensagem("Selecione um Produto antes de gravar.");
            return false;
        }
        String qtdStr = view.getjTextFieldQuantidade().getText().trim();
        if (qtdStr.isEmpty()) {
            mensagem("Informe a Quantidade.");
            view.getjTextFieldQuantidade().requestFocus();
            return false;
        }
        try {
            int qtd = Integer.parseInt(qtdStr);
            if (qtd <= 0) {
                mensagem("A Quantidade deve ser maior que zero.");
                view.getjTextFieldQuantidade().requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mensagem("Quantidade deve ser um número inteiro.");
            view.getjTextFieldQuantidade().requestFocus();
            return false;
        }
        return true;
    }

    private char statusAtual() {
        Object sel = view.getjComboBoxStatus().getSelectedItem();
        return (sel != null && "Ativo".equals(sel.toString())) ? 'A' : 'I';
    }

    private void setModoEdicao(boolean editando) {
        view.getjButtonNovo().setEnabled(!editando);
        view.getjButtonBuscar().setEnabled(!editando);
        view.getjButtonCancelar().setEnabled(editando);
        view.getjButtonGravar().setEnabled(editando);
        view.getjComboBoxStatus().setEnabled(editando);
        view.getjButtonRelacionarQuarto().setEnabled(editando);
        view.getjButtonRelacionarProduto().setEnabled(editando);
        view.getjTextFieldObservacao().setEditable(editando);
        view.getjTextFieldQuantidade().setEditable(editando);
    }

    private void limparFormulario() {
        view.getjTextFieldId().setText("");
        view.getjFormattedTextFieldQuarto().setText("");
        view.getjFormattedTextFieldProduto().setText("");
        view.getjFormattedTextFieldCadastro().setText("");
        view.getjTextFieldObservacao().setText("");
        view.getjTextFieldQuantidade().setText("1");
        view.getjComboBoxStatus().setSelectedIndex(0);
        quartoSelecionado  = null;
        produtoSelecionado = null;
    }

    private void mensagem(String msg) {
        JOptionPane.showMessageDialog(view, msg);
    }

    private void erro(String msg) {
        JOptionPane.showMessageDialog(view, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
