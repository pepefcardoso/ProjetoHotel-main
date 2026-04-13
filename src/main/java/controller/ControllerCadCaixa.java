package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Caixa;
import model.CopaQuarto;
import model.Funcionario;
import model.MovimentoCaixa;
import service.CaixaService;
import service.CopaQuartoService;
import service.FuncionarioService;
import service.MovimentoCaixaService;
import utilities.Utilities;
import view.TelaBuscaCaixa;
import view.TelaBuscaFuncionario;
import view.TelaCadastroCaixa;

public final class ControllerCadCaixa implements ActionListener {

    private static final DateTimeFormatter DT_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final TelaCadastroCaixa view;
    private final CaixaService caixaService             = new CaixaService();
    private final FuncionarioService funcionarioService  = new FuncionarioService();
    private final MovimentoCaixaService movimentoCaixaService = new MovimentoCaixaService();
    private final CopaQuartoService copaQuartoService   = new CopaQuartoService();

    private Funcionario funcionarioSelecionado = null;
    private Caixa caixaAtual = null;
    private boolean modoEdicao = false;

    public ControllerCadCaixa(TelaCadastroCaixa view) {
        this.view = view;
        inicializar();
        registrarListeners();
        verificarStatusCaixaAtual();
    }

    private void inicializar() {
        view.getjTextFieldId().setEnabled(false);
        view.getjTextFieldValorFechamento().setEditable(false);
        view.getjFormattedTextFieldDataAbertura().setEditable(false);
        view.getjFormattedTextFieldDataFechamento().setEditable(false);
        view.getjComboBoxStatus().setEnabled(false);
        view.getjTextFieldFuncionario().setEditable(false);
        setModoEdicao(false);
    }

    private void registrarListeners() {
        view.getjButtonNovo().addActionListener(this);
        view.getjButtonCancelar().addActionListener(this);
        view.getjButtonGravar().addActionListener(this);
        view.getjButtonBuscar().addActionListener(this);
        view.getjButtonSair().addActionListener(this);
        view.getjButtonAbrirCaixa().addActionListener(this);
        view.getjButtonFecharCaixa().addActionListener(this);
        view.getjButtonRelacionarFuncionario().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if      (src == view.getjButtonNovo())                   handleNovo();
        else if (src == view.getjButtonCancelar())               handleCancelar();
        else if (src == view.getjButtonGravar())                 handleGravar();
        else if (src == view.getjButtonBuscar())                 handleBuscar();
        else if (src == view.getjButtonSair())                   view.dispose();
        else if (src == view.getjButtonAbrirCaixa())             handleAbrirCaixa();
        else if (src == view.getjButtonFecharCaixa())            handleFecharCaixa();
        else if (src == view.getjButtonRelacionarFuncionario())  handleBuscarFuncionario();
    }

    private void verificarStatusCaixaAtual() {
        try {
            if (caixaService.isCaixaAberto()) {
                List<Caixa> todos = caixaService.listarTodos();
                Caixa aberto = todos.stream()
                        .filter(c -> c.getStatus() == 'A')
                        .findFirst()
                        .orElse(null);
                if (aberto != null) {
                    caixaAtual = aberto;
                    carregarCaixaParaVisualizacao(aberto);
                    carregarMovimentos(aberto.getId());
                    view.getjButtonFecharCaixa().setEnabled(true);
                    view.getjButtonAbrirCaixa().setEnabled(false);
                }
            }
        } catch (Exception ex) {
            //
        }
    }

    private void handleNovo() {
        modoEdicao = true;
        caixaAtual = null;
        limparFormulario();
        setModoEdicao(true);
        view.getjTextFieldValorAbertura().setText("0.00");
        view.getjComboBoxStatus().setSelectedItem("Aberto");
        view.getjTextFieldValorAbertura().requestFocus();
    }

    private void handleCancelar() {
        modoEdicao = false;
        caixaAtual = null;
        limparFormulario();
        setModoEdicao(false);
        verificarStatusCaixaAtual();
    }

    private void handleGravar() {
        if (!validar()) return;
        try {
            BigDecimal valorAbertura = parseBD(view.getjTextFieldValorAbertura().getText());

            Caixa caixa = new Caixa();
            caixa.setValorDeAbertura(valorAbertura);
            caixa.setValorDeFechamento(BigDecimal.ZERO);
            caixa.setDataHoraAbertura(LocalDateTime.now());
            caixa.setDataHoraFechamento(LocalDateTime.now());
            caixa.setObs(view.getjTextFieldObs().getText().trim());
            caixa.setStatus('A');
            caixa.setFuncionario(funcionarioSelecionado);

            if (caixaAtual == null) {
                if (caixaService.isCaixaAberto()) {
                    erro("Já existe um Caixa aberto. Feche-o antes de abrir um novo.");
                    return;
                }
                caixaService.Criar(caixa);
                mensagem("Caixa aberto com sucesso!\nValor de abertura: R$ " +
                        String.format("%.2f", valorAbertura.doubleValue()));
            } else {
                caixa.setId(caixaAtual.getId());
                caixa.setDataHoraAbertura(caixaAtual.getDataHoraAbertura());
                caixa.setStatus(caixaAtual.getStatus());
                caixaService.Atualizar(caixa);
                mensagem("Dados do caixa atualizados.");
            }

            handleCancelar();
        } catch (Exception ex) {
            erro("Erro ao salvar caixa: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleBuscar() {
        int[] holder = {0};
        TelaBuscaCaixa tela = new TelaBuscaCaixa(null, true);
        new ControllerBuscaCaixa(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Caixa caixa = caixaService.Carregar(holder[0]);
                if (caixa != null) {
                    modoEdicao = true;
                    limparFormulario();
                    caixaAtual = caixa;
                    setModoEdicao(true);
                    carregarCaixaParaVisualizacao(caixa);
                    carregarMovimentos(caixa.getId());

                    view.getjButtonFecharCaixa().setEnabled(caixa.getStatus() == 'A');
                    view.getjButtonAbrirCaixa().setEnabled(false);
                }
            } catch (Exception ex) {
                erro("Erro ao carregar caixa: " + ex.getMessage());
            }
        }
    }

    private void handleAbrirCaixa() {
        if (view.getjTextFieldId().getText().trim().isEmpty()) {
            handleNovo();
            mensagem("Preencha o Valor de Abertura e o Funcionário Responsável,\nem seguida clique em 'Gravar' para abrir o caixa.");
        } else {
            mensagem("Um caixa já está carregado. Use 'Gravar' para salvar alterações.");
        }
    }

    private void handleFecharCaixa() {
        if (caixaAtual == null) {
            erro("Carregue um caixa aberto antes de fechar.");
            return;
        }
        if (caixaAtual.getStatus() != 'A') {
            mensagem("Este caixa já está fechado.");
            return;
        }

        int conf = JOptionPane.showConfirmDialog(view,
                "Confirma o fechamento do Caixa #" + caixaAtual.getId() + "?\n" +
                "Esta operação calculará o saldo final e encerrará o caixa.",
                "Confirmar Fechamento", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (conf != JOptionPane.YES_OPTION) return;

        try {
            BigDecimal saldo = calcularSaldo(caixaAtual.getId());

            caixaAtual.setStatus('F');
            caixaAtual.setDataHoraFechamento(LocalDateTime.now());
            caixaAtual.setValorDeFechamento(saldo);
            caixaService.Atualizar(caixaAtual);

            view.getjTextFieldValorFechamento().setText(String.format("%.2f", saldo.doubleValue()));
            view.getjFormattedTextFieldDataFechamento().setText(
                    LocalDateTime.now().format(DT_FORMATTER));
            view.getjComboBoxStatus().setSelectedItem("Fechado");
            view.getjButtonFecharCaixa().setEnabled(false);
            view.getjButtonAbrirCaixa().setEnabled(true);
            carregarMovimentos(caixaAtual.getId());

            mensagem("Caixa #" + caixaAtual.getId() + " fechado com sucesso!\n" +
                    "Saldo final: R$ " + String.format("%.2f", saldo.doubleValue()));
        } catch (Exception ex) {
            erro("Erro ao fechar caixa: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleBuscarFuncionario() {
        if (!modoEdicao) return;
        int[] holder = {0};
        TelaBuscaFuncionario tela = new TelaBuscaFuncionario(null, true);
        new ControllerBuscaFuncionario(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Funcionario f = funcionarioService.Carregar(holder[0]);
                if (f != null) {
                    funcionarioSelecionado = f;
                    view.getjTextFieldFuncionario().setText(f.getNome());
                }
            } catch (Exception ex) {
                erro("Erro ao carregar funcionário: " + ex.getMessage());
            }
        }
    }

    private void carregarCaixaParaVisualizacao(Caixa caixa) {
        view.getjTextFieldId().setText(String.valueOf(caixa.getId()));
        view.getjComboBoxStatus().setSelectedItem(caixa.getStatus() == 'A' ? "Aberto" : "Fechado");
        view.getjTextFieldObs().setText(caixa.getObs() != null ? caixa.getObs() : "");

        if (caixa.getValorDeAbertura() != null)
            view.getjTextFieldValorAbertura().setText(
                    String.format("%.2f", caixa.getValorDeAbertura().doubleValue()));
        if (caixa.getValorDeFechamento() != null)
            view.getjTextFieldValorFechamento().setText(
                    String.format("%.2f", caixa.getValorDeFechamento().doubleValue()));
        if (caixa.getDataHoraAbertura() != null)
            view.getjFormattedTextFieldDataAbertura().setText(
                    caixa.getDataHoraAbertura().format(DT_FORMATTER));
        if (caixa.getDataHoraFechamento() != null && caixa.getStatus() == 'F')
            view.getjFormattedTextFieldDataFechamento().setText(
                    caixa.getDataHoraFechamento().format(DT_FORMATTER));
        if (caixa.getFuncionario() != null) {
            funcionarioSelecionado = caixa.getFuncionario();
            view.getjTextFieldFuncionario().setText(caixa.getFuncionario().getNome());
        }
    }

    private void carregarMovimentos(int caixaId) {
        DefaultTableModel tabela = (DefaultTableModel) view.getjTableMovimentos().getModel();
        tabela.setRowCount(0);

        BigDecimal totalEntradas = BigDecimal.ZERO;
        BigDecimal totalSaidas   = BigDecimal.ZERO;

        try {
            List<MovimentoCaixa> movimentos = movimentoCaixaService.findByCaixaId(caixaId);
            for (MovimentoCaixa mov : movimentos) {
                String dataStr = mov.getDataHoraMovimento() != null
                        ? mov.getDataHoraMovimento().format(DT_FORMATTER) : "-";
                tabela.addRow(new Object[]{
                    mov.getId(),
                    dataStr,
                    mov.getDescricao(),
                    String.format("R$ %.2f", mov.getValor().doubleValue()),
                    mov.getStatus()
                });
                if (mov.getValor().compareTo(BigDecimal.ZERO) >= 0)
                    totalEntradas = totalEntradas.add(mov.getValor());
                else
                    totalSaidas = totalSaidas.add(mov.getValor().abs());
            }
        } catch (Exception ex) {
            //
        }

        try {
            List<CopaQuarto> copaItems = copaQuartoService.findByCaixaId(caixaId);
            for (CopaQuarto copa : copaItems) {
                if (copa.getProduto() == null) continue;

                BigDecimal valorItem = copa.getProduto().getValor()
                        .multiply(BigDecimal.valueOf(copa.getQuantidade()));
                String dataStr = copa.getDataHoraPedido() != null
                        ? copa.getDataHoraPedido().format(DT_FORMATTER) : "-";
                String descricao = String.format("Copa – %s x%d (Qto %s)",
                        copa.getProduto().getDescricao(),
                        copa.getQuantidade(),
                        copa.getQuarto() != null ? copa.getQuarto().getIdentificacao() : "?");

                tabela.addRow(new Object[]{
                    "Copa",
                    dataStr,
                    descricao,
                    String.format("R$ %.2f", valorItem.doubleValue()),
                    copa.getStatus()
                });
            }
        } catch (Exception ex) {
            //
        }

        BigDecimal saldo = totalEntradas.subtract(totalSaidas);
        view.getjTextFieldTotalEntradas().setText(String.format("%.2f", totalEntradas.doubleValue()));
        view.getjTextFieldTotalSaidas().setText(String.format("%.2f", totalSaidas.doubleValue()));
        view.getjTextFieldSaldo().setText(String.format("%.2f", saldo.doubleValue()));

        Color corSaldo = saldo.compareTo(BigDecimal.ZERO) >= 0
                ? new Color(0, 100, 0) : new Color(180, 0, 0);
        view.getjTextFieldSaldo().setForeground(corSaldo);
    }

    private BigDecimal calcularSaldo(int caixaId) {
        try {
            List<MovimentoCaixa> movimentos = movimentoCaixaService.findByCaixaId(caixaId);
            BigDecimal soma = BigDecimal.ZERO;
            for (MovimentoCaixa mov : movimentos) {
                soma = soma.add(mov.getValor());
            }
            if (caixaAtual != null && caixaAtual.getValorDeAbertura() != null) {
                soma = soma.add(caixaAtual.getValorDeAbertura());
            }
            return soma;
        } catch (Exception e) {
            return caixaAtual != null && caixaAtual.getValorDeAbertura() != null
                    ? caixaAtual.getValorDeAbertura() : BigDecimal.ZERO;
        }
    }

    private boolean validar() {
        String valorStr = view.getjTextFieldValorAbertura().getText().trim();
        if (valorStr.isEmpty()) {
            mensagem("Informe o Valor de Abertura do caixa.");
            view.getjTextFieldValorAbertura().requestFocus();
            return false;
        }
        try {
            BigDecimal val = parseBD(valorStr);
            if (val.compareTo(BigDecimal.ZERO) < 0) {
                mensagem("O Valor de Abertura não pode ser negativo.");
                return false;
            }
        } catch (Exception e) {
            mensagem("Valor de Abertura inválido. Use formato numérico (ex: 100.00).");
            return false;
        }
        if (funcionarioSelecionado == null) {
            mensagem("Selecione o Funcionário Responsável pelo caixa.");
            return false;
        }
        return true;
    }

    private void setModoEdicao(boolean editando) {
        view.getjButtonNovo().setEnabled(!editando);
        view.getjButtonBuscar().setEnabled(!editando);
        view.getjButtonCancelar().setEnabled(editando);
        view.getjButtonGravar().setEnabled(editando);
        view.getjTextFieldValorAbertura().setEditable(editando);
        view.getjTextFieldObs().setEditable(editando);
        view.getjButtonRelacionarFuncionario().setEnabled(editando);
    }

    private void limparFormulario() {
        view.getjTextFieldId().setText("");
        view.getjTextFieldValorAbertura().setText("0.00");
        view.getjTextFieldValorFechamento().setText("0.00");
        view.getjTextFieldObs().setText("");
        view.getjTextFieldFuncionario().setText("");
        view.getjFormattedTextFieldDataAbertura().setText("");
        view.getjFormattedTextFieldDataFechamento().setText("");
        view.getjComboBoxStatus().setSelectedItem("Aberto");
        view.getjTextFieldTotalEntradas().setText("0.00");
        view.getjTextFieldTotalSaidas().setText("0.00");
        view.getjTextFieldSaldo().setText("0.00");
        ((DefaultTableModel) view.getjTableMovimentos().getModel()).setRowCount(0);
        funcionarioSelecionado = null;
        caixaAtual = null;
    }

    private BigDecimal parseBD(String texto) {
        if (texto == null || texto.trim().isEmpty()) return BigDecimal.ZERO;
        String clean = texto.trim().replace("R$", "").replace(" ", "");
        if (clean.contains(",") && clean.contains("."))
            clean = clean.replace(".", "").replace(",", ".");
        else if (clean.contains(","))
            clean = clean.replace(",", ".");
        try { return new BigDecimal(clean); }
        catch (Exception e) { return BigDecimal.ZERO; }
    }

    private void mensagem(String msg) { JOptionPane.showMessageDialog(view, msg); }
    private void erro(String msg)     { JOptionPane.showMessageDialog(view, msg, "Erro", JOptionPane.ERROR_MESSAGE); }
}