package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import model.AlocacaoVaga;
import model.Caixa;
import model.Check;
import model.CheckHospede;
import model.CheckQuarto;
import model.Hospede;
import model.MovimentoCaixa;
import model.Quarto;
import model.Receber;
import model.Reserva;
import model.ReservaQuarto;
import model.VagaEstacionamento;
import model.Veiculo;
import service.AlocacaoVagaService;
import service.CaixaService;
import service.CheckHospedeService;
import service.CheckQuartoService;
import service.CheckService;
import service.CopaQuartoService;
import service.HospedeService;
import service.MovimentoCaixaService;
import service.QuartoService;
import service.ReceberService;
import service.ReservaQuartoService;
import service.ReservaService;
import service.VagaEstacionamentoService;
import service.VeiculoService;
import utilities.Utilities;
import view.TelaBuscaCheck;
import view.TelaBuscaHospede;
import view.TelaBuscaQuarto;
import view.TelaBuscaReserva;
import view.TelaBuscaVaga;
import view.TelaBuscaVeiculo;
import view.TelaCheck;

public class ControllerCadCheck implements ActionListener {

    private final TelaCheck view;

    private final CheckService checkService               = new CheckService();
    private final CheckQuartoService checkQuartoService   = new CheckQuartoService();
    private final CheckHospedeService checkHospedeService = new CheckHospedeService();
    private final AlocacaoVagaService alocacaoVagaService = new AlocacaoVagaService();
    private final ReceberService receberService           = new ReceberService();
    private final HospedeService hospedeService           = new HospedeService();
    private final QuartoService quartoService             = new QuartoService();
    private final VeiculoService veiculoService           = new VeiculoService();
    private final VagaEstacionamentoService vagaService   = new VagaEstacionamentoService();
    private final ReservaService reservaService           = new ReservaService();
    private final ReservaQuartoService reservaQuartoService = new ReservaQuartoService();
    private final CaixaService caixaService               = new CaixaService();
    private final CopaQuartoService copaQuartoService     = new CopaQuartoService();
    private final MovimentoCaixaService movCaixaService   = new MovimentoCaixaService();

    private Hospede hospedePendente           = null;
    private Quarto quartoPendente             = null;
    private Veiculo veiculoPendente           = null;
    private VagaEstacionamento vagaPendente   = null;
    private Reserva reservaCheckIn            = null;
    private boolean modoEdicao               = false;

    private final List<Hospede>   hospedesAlocados = new ArrayList<>();
    private final List<Object[]>  quartosAlocados  = new ArrayList<>();
    private final List<Object[]>  vagasAlocadas    = new ArrayList<>();

    public ControllerCadCheck(TelaCheck view) {
        this.view = view;
        inicializar();
        registrarListeners();
        configurarCalculoRecebimento();
    }

    private void inicializar() {
        view.getjTextFieldId().setEnabled(false);
        view.getjComboBoxStatus().setEnabled(false);
        view.getjFormattedTextFieldDataCadastro().setEditable(false);
        view.getjFormattedTextFieldReserva().setEnabled(false);
        view.getjFormattedTextFieldHospede().setEnabled(false);
        view.getjFormattedTextFieldQuarto().setEnabled(false);
        view.getjFormattedTextFieldVeiculo().setEnabled(false);
        view.getjFormattedTextFieldVaga().setEnabled(false);
        setModoEdicao(false);
    }

    private void registrarListeners() {
        view.getjButtonNovo().addActionListener(this);
        view.getjButtonCancelar().addActionListener(this);
        view.getjButtonGravar().addActionListener(this);
        view.getjButtonBuscar().addActionListener(this);
        view.getjButtonSair().addActionListener(this);
        view.getjButtonRelacionarReserva().addActionListener(this);
        view.getjButtonRelacionarHospede().addActionListener(this);
        view.getjButtonAlocarHospede().addActionListener(this);
        view.getjButtonRemoverHospede().addActionListener(this);
        view.getjButtonRelacionarQuarto().addActionListener(this);
        view.getjButtonAlocarQuarto().addActionListener(this);
        view.getjButtonRemoverQuarto().addActionListener(this);
        view.getjButtonRelacionarVeiculo().addActionListener(this);
        view.getjButtonRelacionarVaga().addActionListener(this);
        view.getjButtonAlocarVaga().addActionListener(this);
        view.getjButtonRemoverVaga().addActionListener(this);

        JButton btnCheckout = obterBotaoCheckout();
        if (btnCheckout != null) btnCheckout.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if      (src == view.getjButtonNovo())               handleNovo();
        else if (src == view.getjButtonCancelar())           handleCancelar();
        else if (src == view.getjButtonGravar())             handleGravar();
        else if (src == view.getjButtonBuscar())             handleBuscar();
        else if (src == view.getjButtonSair())               view.dispose();
        else if (src == view.getjButtonRelacionarReserva())  handleBuscarReserva();
        else if (src == view.getjButtonRelacionarHospede())  handleBuscarHospede();
        else if (src == view.getjButtonAlocarHospede())      handleAlocarHospede();
        else if (src == view.getjButtonRemoverHospede())     handleRemoverHospede();
        else if (src == view.getjButtonRelacionarQuarto())   handleBuscarQuarto();
        else if (src == view.getjButtonAlocarQuarto())       handleAlocarQuarto();
        else if (src == view.getjButtonRemoverQuarto())      handleRemoverQuarto();
        else if (src == view.getjButtonRelacionarVeiculo())  handleBuscarVeiculo();
        else if (src == view.getjButtonRelacionarVaga())     handleBuscarVaga();
        else if (src == view.getjButtonAlocarVaga())         handleAlocarVaga();
        else if (src == view.getjButtonRemoverVaga())        handleRemoverVaga();
        else {
            JButton btnCheckout = obterBotaoCheckout();
            if (btnCheckout != null && src == btnCheckout) handleCheckout();
        }
    }

    private void atualizarConsumoCopaPorQuartos() {
        try {
            BigDecimal total;
            String idStr = view.getjTextFieldId().getText().trim();
            if (!idStr.isEmpty()) {
                total = copaQuartoService.buscarTotalConsumo(Integer.parseInt(idStr));
            } else {
                List<Integer> quartoIds = quartosAlocados.stream()
                        .map(q -> ((Quarto) q[0]).getId())
                        .collect(Collectors.toList());
                total = copaQuartoService.buscarTotalConsumoByQuartos(quartoIds);
            }
            view.getjTextFieldValorProdutos().setText(total != null ? total.toPlainString() : "0.00");
        } catch (Exception ignored) {
            //
        }
    }

    private void handleCheckout() {
        String idStr = view.getjTextFieldId().getText().trim();
        if (idStr.isEmpty()) {
            mensagem("Carregue um Check-in ativo antes de realizar o Check-out.\nUse 'Buscar' para localizar o check.");
            return;
        }

        int checkId;
        try {
            checkId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            erro("ID do check inválido.");
            return;
        }

        try {
            Check check = checkService.Carregar(checkId);
            if (check == null) {
                erro("Check não encontrado.");
                return;
            }
            if (check.getStatus() == 'F') {
                mensagem("Este Check-in já foi finalizado (Check-out já realizado).");
                return;
            }

            atualizarConsumoCopaPorQuartos();

            BigDecimal valOriginal = parseBD(view.getjTextFieldValorOriginal().getText());
            BigDecimal valProdutos = parseBD(view.getjTextFieldValorProdutos().getText());
            BigDecimal desconto    = parseBD(view.getjTextFieldDesconto().getText());
            BigDecimal acrescimo   = parseBD(view.getjTextFieldAcrescimo().getText());
            BigDecimal valPagar    = valOriginal.add(valProdutos).subtract(desconto).add(acrescimo);

            String msg = String.format(
                "=== RESUMO DO CHECK-OUT ===\n\n" +
                "Check #%d\n" +
                "Valor Estadia : R$ %.2f\n" +
                "Consumo Copa  : R$ %.2f\n" +
                "Desconto      : R$ %.2f\n" +
                "Acréscimo     : R$ %.2f\n" +
                "─────────────────────────\n" +
                "TOTAL A PAGAR : R$ %.2f\n\n" +
                "Confirma o Check-out?",
                checkId,
                valOriginal.doubleValue(),
                valProdutos.doubleValue(),
                desconto.doubleValue(),
                acrescimo.doubleValue(),
                valPagar.doubleValue()
            );

            int conf = JOptionPane.showConfirmDialog(view, msg, "Confirmar Check-out",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (conf != JOptionPane.YES_OPTION) return;

            String valPagoStr = view.getjTextFieldValorPago().getText().trim();
            BigDecimal valPago = parseBD(valPagoStr);

            if (valPago.compareTo(BigDecimal.ZERO) > 0 && !caixaService.isCaixaAberto()) {
                erro("Não há Caixa aberto. Abra o caixa antes de registrar um pagamento.");
                return;
            }

            Receber receber = receberService.findByCheckId(checkId);
            if (receber == null) {
                receber = new Receber();
                receber.setCheck(check);
                receber.setDataHoraCadastro(LocalDateTime.now());
            }
            receber.setValorOriginal(valOriginal.add(valProdutos));
            receber.setDesconto(desconto);
            receber.setAcrescimo(acrescimo);
            receber.setValorPago(valPago);
            receber.setObs(view.getjTextFieldObsRecebimento().getText().trim());
            receber.setStatus(valPago.compareTo(valPagar) >= 0 ? 'P' : 'A');

            if (receber.getId() == 0) {
                receberService.Criar(receber);
            } else {
                receberService.Atualizar(receber);
            }

            if (valPago.compareTo(BigDecimal.ZERO) > 0) {
                criarMovimentoCaixa(receber, valPago, checkId);
            }

            List<CheckQuarto> listaQuartos = checkQuartoService.findByCheckId(checkId);
            for (CheckQuarto cq : listaQuartos) {
                cq.setStatus('F');
                cq.setDataHoraFim(LocalDateTime.now());
                checkQuartoService.Atualizar(cq);
            }

            List<AlocacaoVaga> listaVagas = alocacaoVagaService.findByCheckId(checkId);
            for (AlocacaoVaga av : listaVagas) {
                av.setStatus('F');
                alocacaoVagaService.Atualizar(av);
            }

            check.setStatus('F');
            check.setDataHoraSaida(LocalDateTime.now());
            checkService.Atualizar(check);

            if (check.getReserva() != null) {
                Reserva reserva = check.getReserva();
                reserva.setStatus('F');
                reservaService.Atualizar(reserva);
            }

            view.getjComboBoxStatus().setSelectedItem("Inativo");
            setModoEdicao(false);
            modoEdicao = false;

            JButton btnCheckout = obterBotaoCheckout();
            if (btnCheckout != null) btnCheckout.setEnabled(false);

            String statusPag = valPago.compareTo(valPagar) >= 0 ? "QUITADO" : "PENDENTE";
            mensagem(String.format(
                "✔ Check-out realizado com sucesso!\n\n" +
                "Check #%d finalizado.\n" +
                "Total cobrado : R$ %.2f\n" +
                "Valor pago    : R$ %.2f\n" +
                "Status        : %s\n\n" +
                "Quartos e vagas foram liberados.",
                checkId,
                valPagar.doubleValue(),
                valPago.doubleValue(),
                statusPag
            ));

        } catch (Exception ex) {
            erro("Erro ao realizar check-out: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void criarMovimentoCaixa(Receber receber, BigDecimal valor, int checkId) {
        try {
            List<Caixa> todos = caixaService.listarTodos();
            Caixa caixaAberto = todos.stream()
                    .filter(c -> c.getStatus() == 'A')
                    .findFirst().orElse(null);
            if (caixaAberto == null) return;

            MovimentoCaixa mov = new MovimentoCaixa();
            mov.setCaixa(caixaAberto);
            mov.setReceber(receber);
            mov.setDataHoraMovimento(LocalDateTime.now());
            mov.setValor(valor);
            mov.setDescricao("Recebimento Check-out #" + checkId);
            mov.setObs("Pagamento automático via checkout");
            mov.setStatus('A');
            movCaixaService.Criar(mov);
        } catch (Exception e) {
            System.err.println("Aviso: não foi possível criar movimentação de caixa: " + e.getMessage());
        }
    }

    private void handleNovo() {
        modoEdicao = true;
        limparFormulario();
        setModoEdicao(true);

        view.getjFormattedTextFieldDataCadastro().setText(Utilities.getDataHoje());
        view.getjFormattedTextFieldDataEntrada().setText(Utilities.getDataHoje());
        view.getjComboBoxStatus().setSelectedItem("Ativo");
        view.getjComboBoxStatusRecebimento().setSelectedItem("Pendente");
        view.getjTextFieldValorOriginal().setText("0.00");
        view.getjTextFieldDesconto().setText("0.00");
        view.getjTextFieldAcrescimo().setText("0.00");
        view.getjTextFieldValorProdutos().setText("0.00");
        view.getjTextFieldValorPago().setText("0.00");
        view.getjTabbedPane().setSelectedIndex(0);

        JButton btnCheckout = obterBotaoCheckout();
        if (btnCheckout != null) btnCheckout.setEnabled(false);
    }

    private void handleCancelar() {
        modoEdicao = false;
        limparFormulario();
        setModoEdicao(false);
    }

    private void handleGravar() {
        if (!validarCheckIn()) return;

        try {
            LocalDateTime dtEntrada = parseData(view.getjFormattedTextFieldDataEntrada().getText());
            LocalDateTime dtSaida   = parseData(view.getjFormattedTextFieldDataSaida().getText());

            Object[] firstRoomInfo = quartosAlocados.get(0);
            Quarto firstQuarto  = (Quarto) firstRoomInfo[0];
            String firstObsQuarto = (String) firstRoomInfo[1];

            CheckQuarto cqFirst = new CheckQuarto();
            cqFirst.setQuarto(firstQuarto);
            cqFirst.setDataHoraInicio(dtEntrada);
            cqFirst.setDataHoraFim(dtSaida);
            cqFirst.setObs(firstObsQuarto);
            cqFirst.setStatus('A');
            checkQuartoService.Criar(cqFirst);

            Check check = new Check();
            check.setDataHoraCadastro(parseData(view.getjFormattedTextFieldDataCadastro().getText()));
            check.setDataHoraEntrada(dtEntrada);
            check.setDataHoraSaida(dtSaida);
            check.setObs(view.getjTextFieldObs().getText().trim());
            check.setStatus('A');
            if (reservaCheckIn != null) check.setReserva(reservaCheckIn);
            check.setCheckQuarto(cqFirst);
            checkService.Criar(check);

            cqFirst.setCheck(check);
            checkQuartoService.Atualizar(cqFirst);

            for (int i = 1; i < quartosAlocados.size(); i++) {
                Object[] qInfo  = quartosAlocados.get(i);
                CheckQuarto cq  = new CheckQuarto();
                cq.setCheck(check);
                cq.setQuarto((Quarto) qInfo[0]);
                cq.setDataHoraInicio(dtEntrada);
                cq.setDataHoraFim(dtSaida);
                cq.setObs((String) qInfo[1]);
                cq.setStatus('A');
                checkQuartoService.Criar(cq);
            }

            for (Hospede h : hospedesAlocados) {
                CheckHospede ch = new CheckHospede();
                ch.setCheck(check);
                ch.setHospede(h);
                String tipo = "Acompanhante";
                for (int i = 0; i < view.getjTableHospedes().getRowCount(); i++) {
                    if ((int) view.getjTableHospedes().getValueAt(i, 0) == h.getId()) {
                        tipo = view.getjTableHospedes().getValueAt(i, 2).toString();
                        break;
                    }
                }
                ch.setTipoHospede(tipo);
                ch.setObs("");
                ch.setStatus('A');
                checkHospedeService.Criar(ch);
            }

            for (Object[] entry : vagasAlocadas) {
                AlocacaoVaga av = new AlocacaoVaga();
                av.setCheck(check);
                av.setVeiculo((Veiculo) entry[0]);
                av.setVagaEstacionamento((VagaEstacionamento) entry[1]);
                av.setObs((String) entry[2]);
                av.setStatus('A');
                alocacaoVagaService.Criar(av);
            }

            criarReceberSePreenchido(check);

            if (reservaCheckIn != null) {
                reservaCheckIn.setCheck(check);
                reservaCheckIn.setStatus('F');
                reservaService.Atualizar(reservaCheckIn);
            }

            view.getjTextFieldId().setText(String.valueOf(check.getId()));

            JButton btnCheckout = obterBotaoCheckout();
            if (btnCheckout != null) btnCheckout.setEnabled(true);

            mensagem("Check-in realizado com sucesso!\nID do Check: " + check.getId());
            setModoEdicao(false);
            modoEdicao = false;

        } catch (Exception ex) {
            erro("Erro ao realizar check-in: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void criarReceberSePreenchido(Check check) throws Exception {
        BigDecimal valPago = parseBD(view.getjTextFieldValorPago().getText());

        if (valPago.compareTo(BigDecimal.ZERO) > 0) {
            if (!caixaService.isCaixaAberto()) {
                throw new Exception("Não há um Caixa aberto para processar o valor recebido.");
            }
        }

        String valOrigStr = view.getjTextFieldValorOriginal().getText().trim();
        if (valOrigStr.isEmpty() || "0.00".equals(valOrigStr)) return;

        BigDecimal valOriginal = parseBD(valOrigStr);
        BigDecimal desconto    = parseBD(view.getjTextFieldDesconto().getText());
        BigDecimal acrescimo   = parseBD(view.getjTextFieldAcrescimo().getText());
        BigDecimal valProdutos = parseBD(view.getjTextFieldValorProdutos().getText());

        Receber rec = new Receber();
        rec.setCheck(check);
        rec.setDataHoraCadastro(LocalDateTime.now());
        rec.setValorOriginal(valOriginal.add(valProdutos));
        rec.setDesconto(desconto);
        rec.setAcrescimo(acrescimo);
        rec.setValorPago(valPago);
        rec.setObs(view.getjTextFieldObsRecebimento().getText().trim());
        rec.setStatus(statusRecebimento(view.getjComboBoxStatusRecebimento()));
        receberService.Criar(rec);
    }

    private void handleBuscar() {
        int[] holder = {0};
        TelaBuscaCheck tela = new TelaBuscaCheck(null, true);
        new ControllerBuscaCheck(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Check check = checkService.Carregar(holder[0]);
                if (check != null) carregarCheckParaEdicao(check);
            } catch (Exception ex) {
                erro("Erro ao carregar check: " + ex.getMessage());
            }
        }
    }

    private void carregarCheckParaEdicao(Check check) {
        modoEdicao = true;
        limparFormulario();
        setModoEdicao(true);

        view.getjTextFieldId().setText(String.valueOf(check.getId()));
        view.getjComboBoxStatus().setSelectedItem(check.getStatus() == 'A' ? "Ativo" : "Inativo");

        if (check.getDataHoraCadastro() != null)
            view.getjFormattedTextFieldDataCadastro().setText(Utilities.formatarData(check.getDataHoraCadastro()));
        if (check.getDataHoraEntrada() != null)
            view.getjFormattedTextFieldDataEntrada().setText(Utilities.formatarData(check.getDataHoraEntrada()));
        if (check.getDataHoraSaida() != null)
            view.getjFormattedTextFieldDataSaida().setText(Utilities.formatarData(check.getDataHoraSaida()));
        view.getjTextFieldObs().setText(check.getObs());

        if (check.getReserva() != null) {
            reservaCheckIn = check.getReserva();
            view.getjFormattedTextFieldReserva().setText(String.valueOf(reservaCheckIn.getId()));
        }

        try {
            List<CheckHospede> listaHospedes = checkHospedeService.findByCheckId(check.getId());
            if (listaHospedes != null) {
                for (CheckHospede ch : listaHospedes) {
                    hospedesAlocados.add(ch.getHospede());
                    adicionarLinhaTabela(view.getjTableHospedes(), new Object[]{
                        ch.getHospede().getId(), ch.getHospede().getNome(),
                        ch.getTipoHospede(), ch.getObs(), ch.getStatus()
                    });
                }
            }
        } catch (Exception ignored) {}

        try {
            List<CheckQuarto> listaQuartos = checkQuartoService.findByCheckId(check.getId());
            if (listaQuartos != null) {
                for (CheckQuarto cq : listaQuartos) {
                    quartosAlocados.add(new Object[]{cq.getQuarto(), cq.getObs()});
                    adicionarLinhaTabela(view.getjTableQuartos(), new Object[]{
                        cq.getQuarto().getId(), cq.getQuarto().getIdentificacao(),
                        cq.getQuarto().getDescricao(), cq.getObs(), cq.getStatus()
                    });
                }
            }
        } catch (Exception ignored) {}

        atualizarConsumoCopaPorQuartos();

        try {
            List<AlocacaoVaga> listaVagas = alocacaoVagaService.findByCheckId(check.getId());
            if (listaVagas != null) {
                for (AlocacaoVaga av : listaVagas) {
                    vagasAlocadas.add(new Object[]{av.getVeiculo(), av.getVagaEstacionamento(), av.getObs()});
                    adicionarLinhaTabela(view.getjTableAlocacoesVagas(), new Object[]{
                        vagasAlocadas.size(), av.getVeiculo().getPlaca(),
                        av.getVagaEstacionamento().getDescricao(), av.getObs(), av.getStatus()
                    });
                }
            }
        } catch (Exception ignored) {}

        try {
            Receber receber = receberService.findByCheckId(check.getId());
            if (receber != null) {
                BigDecimal valorCopa = parseBD(view.getjTextFieldValorProdutos().getText());
                BigDecimal valorEstadia = receber.getValorOriginal().subtract(valorCopa);
                if (valorEstadia.compareTo(BigDecimal.ZERO) < 0) valorEstadia = BigDecimal.ZERO;

                view.getjTextFieldValorOriginal().setText(valorEstadia.toPlainString());
                view.getjTextFieldDesconto().setText(receber.getDesconto().toPlainString());
                view.getjTextFieldAcrescimo().setText(receber.getAcrescimo().toPlainString());
                view.getjTextFieldValorPago().setText(receber.getValorPago().toPlainString());
                view.getjTextFieldObsRecebimento().setText(receber.getObs());
                view.getjComboBoxStatusRecebimento().setSelectedItem(receber.getStatus() == 'P' ? "Pago" : "Pendente");
                recalcular();
            }
        } catch (Exception ignored) {}

        JButton btnCheckout = obterBotaoCheckout();
        if (btnCheckout != null) {
            btnCheckout.setEnabled(check.getStatus() == 'A');
        }

        view.getjTabbedPane().setSelectedIndex(0);
    }

    private void handleBuscarReserva() {
        if (!modoEdicao) return;
        int[] holder = {0};
        TelaBuscaReserva tela = new TelaBuscaReserva(null, true);
        new ControllerBuscaReserva(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Reserva r = reservaService.Carregar(holder[0]);
                if (r == null || r.getStatus() != 'A') {
                    mensagem("A reserva não foi encontrada ou não está ativa.");
                    return;
                }
                reservaCheckIn = r;
                view.getjFormattedTextFieldReserva().setText(String.valueOf(r.getId()));
                if (r.getDataPrevistaEntrada() != null)
                    view.getjFormattedTextFieldDataEntrada().setText(Utilities.formatarData(r.getDataPrevistaEntrada()));
                if (r.getDataPrevistaSaida() != null)
                    view.getjFormattedTextFieldDataSaida().setText(Utilities.formatarData(r.getDataPrevistaSaida()));

                List<ReservaQuarto> rqList = reservaQuartoService.findByReservaId(r.getId());
                if (!rqList.isEmpty()) {
                    int resp = JOptionPane.showConfirmDialog(view,
                            "Deseja importar automaticamente todos os quartos associados a esta reserva?",
                            "Importar Quartos", JOptionPane.YES_NO_OPTION);
                    if (resp == JOptionPane.YES_OPTION) {
                        for (ReservaQuarto rq : rqList) {
                            Quarto q = rq.getQuarto();
                            boolean exists = quartosAlocados.stream()
                                    .anyMatch(qInfo -> ((Quarto) qInfo[0]).getId() == q.getId());
                            if (q != null && !exists) {
                                quartosAlocados.add(new Object[]{q, ""});
                                adicionarLinhaTabela(view.getjTableQuartos(),
                                        new Object[]{q.getId(), q.getIdentificacao(), q.getDescricao(), "", q.getStatus()});
                            }
                        }
                        atualizarConsumoCopaPorQuartos();
                    }
                }
            } catch (Exception ex) {
                erro("Erro ao carregar reserva: " + ex.getMessage());
            }
        }
    }

    private void handleBuscarHospede() {
        if (!modoEdicao) return;
        int[] holder = {0};
        TelaBuscaHospede tela = new TelaBuscaHospede(null, true);
        new ControllerBuscaHospede(tela, v -> holder[0] = v);
        tela.setVisible(true);
        if (holder[0] != 0) {
            try {
                Hospede h = hospedeService.Carregar(holder[0]);
                if (h != null) {
                    hospedePendente = h;
                    view.getjFormattedTextFieldHospede().setText(h.getId() + " – " + h.getNome());
                }
            } catch (Exception ex) { erro("Erro ao carregar hóspede: " + ex.getMessage()); }
        }
    }

    private void handleAlocarHospede() {
        if (hospedePendente == null) { mensagem("Selecione um hóspede antes de alocar."); return; }
        boolean exists = hospedesAlocados.stream().anyMatch(h -> h.getId() == hospedePendente.getId());
        if (exists) { mensagem("Este hóspede já foi alocado."); return; }
        String tipoSelecionado = view.getjComboBoxTipoHospede().getSelectedItem().toString();
        if ("Titular".equals(tipoSelecionado)) {
            for (int i = 0; i < view.getjTableHospedes().getRowCount(); i++) {
                if ("Titular".equals(view.getjTableHospedes().getValueAt(i, 2).toString())) {
                    mensagem("Não é permitido mais de um hóspede Titular.");
                    return;
                }
            }
        }
        hospedesAlocados.add(hospedePendente);
        adicionarLinhaTabela(view.getjTableHospedes(), new Object[]{
            hospedePendente.getId(), hospedePendente.getNome(), tipoSelecionado,
            view.getjTextFieldObsHospede().getText().trim(), hospedePendente.getStatus()
        });
        hospedePendente = null;
        view.getjFormattedTextFieldHospede().setText("");
        view.getjTextFieldObsHospede().setText("");
        view.getjComboBoxTipoHospede().setSelectedIndex(1);
    }

    private void handleRemoverHospede() {
        int row = view.getjTableHospedes().getSelectedRow();
        if (row == -1) { mensagem("Selecione um hóspede para remover."); return; }
        hospedesAlocados.remove(row);
        ((DefaultTableModel) view.getjTableHospedes().getModel()).removeRow(row);
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
                    quartoPendente = q;
                    view.getjFormattedTextFieldQuarto().setText(q.getIdentificacao() + " – " + q.getDescricao());
                }
            } catch (Exception ex) { erro("Erro ao carregar quarto: " + ex.getMessage()); }
        }
    }

    private void handleAlocarQuarto() {
        if (quartoPendente == null) { mensagem("Selecione um quarto antes de alocar."); return; }
        LocalDateTime dtEntrada = parseData(view.getjFormattedTextFieldDataEntrada().getText());
        LocalDateTime dtSaida   = parseData(view.getjFormattedTextFieldDataSaida().getText());
        if (dtEntrada == null || dtSaida == null) {
            mensagem("Preencha as datas de entrada e saída antes de alocar quartos.");
            return;
        }
        boolean exists = quartosAlocados.stream().anyMatch(q -> ((Quarto) q[0]).getId() == quartoPendente.getId());
        if (exists) { mensagem("Este quarto já está alocado."); return; }
        String obs = view.getjTextFieldObsQuarto().getText().trim();
        quartosAlocados.add(new Object[]{quartoPendente, obs});
        adicionarLinhaTabela(view.getjTableQuartos(), new Object[]{
            quartoPendente.getId(), quartoPendente.getIdentificacao(),
            quartoPendente.getDescricao(), obs, quartoPendente.getStatus()
        });
        quartoPendente = null;
        view.getjFormattedTextFieldQuarto().setText("");
        view.getjTextFieldObsQuarto().setText("");

        atualizarConsumoCopaPorQuartos();
    }

    private void handleRemoverQuarto() {
        int row = view.getjTableQuartos().getSelectedRow();
        if (row == -1) { mensagem("Selecione um quarto para remover."); return; }
        quartosAlocados.remove(row);
        ((DefaultTableModel) view.getjTableQuartos().getModel()).removeRow(row);
        atualizarConsumoCopaPorQuartos();
    }

    private void handleBuscarVeiculo() {
        if (!modoEdicao) return;
        int[] holder = {0};
        TelaBuscaVeiculo tela = new TelaBuscaVeiculo(null, true);
        new ControllerBuscaVeiculo(tela, v -> holder[0] = v);
        tela.setVisible(true);
        if (holder[0] != 0) {
            try {
                Veiculo v = veiculoService.Carregar(holder[0]);
                if (v != null) {
                    veiculoPendente = v;
                    view.getjFormattedTextFieldVeiculo().setText(v.getPlaca() + " – " + v.getCor());
                }
            } catch (Exception ex) { erro("Erro ao carregar veículo: " + ex.getMessage()); }
        }
    }

    private void handleBuscarVaga() {
        if (!modoEdicao) return;
        int[] holder = {0};
        TelaBuscaVaga tela = new TelaBuscaVaga(null, true);
        new ControllerBuscaVagaEstacionamento(tela, v -> holder[0] = v);
        tela.setVisible(true);
        if (holder[0] != 0) {
            try {
                VagaEstacionamento vaga = vagaService.Carregar(holder[0]);
                if (vaga != null) {
                    vagaPendente = vaga;
                    view.getjFormattedTextFieldVaga().setText(vaga.getId() + " – " + vaga.getDescricao());
                }
            } catch (Exception ex) { erro("Erro ao carregar vaga: " + ex.getMessage()); }
        }
    }

    private void handleAlocarVaga() {
        if (veiculoPendente == null || vagaPendente == null) {
            mensagem("Selecione o Veículo e a Vaga para realizar a alocação.");
            return;
        }
        for (Object[] v : vagasAlocadas) {
            if (((Veiculo) v[0]).getId() == veiculoPendente.getId() &&
                ((VagaEstacionamento) v[1]).getId() == vagaPendente.getId()) {
                mensagem("Essa combinação de Veículo e Vaga já está alocada.");
                return;
            }
        }
        String obs = view.getjTextFieldObsVaga().getText().trim();
        vagasAlocadas.add(new Object[]{veiculoPendente, vagaPendente, obs});
        adicionarLinhaTabela(view.getjTableAlocacoesVagas(), new Object[]{
            vagasAlocadas.size(), veiculoPendente.getPlaca(), vagaPendente.getDescricao(), obs, 'A'
        });
        veiculoPendente = null;
        vagaPendente    = null;
        view.getjFormattedTextFieldVeiculo().setText("");
        view.getjFormattedTextFieldVaga().setText("");
        view.getjTextFieldObsVaga().setText("");
    }

    private void handleRemoverVaga() {
        int row = view.getjTableAlocacoesVagas().getSelectedRow();
        if (row == -1) { mensagem("Selecione uma alocação para remover."); return; }
        vagasAlocadas.remove(row);
        ((DefaultTableModel) view.getjTableAlocacoesVagas().getModel()).removeRow(row);
    }

    private void configurarCalculoRecebimento() {
        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { recalcular(); }
            public void removeUpdate(DocumentEvent e) { recalcular(); }
            public void changedUpdate(DocumentEvent e) { recalcular(); }
        };
        view.getjTextFieldValorOriginal().getDocument().addDocumentListener(dl);
        view.getjTextFieldValorProdutos().getDocument().addDocumentListener(dl);
        view.getjTextFieldDesconto().getDocument().addDocumentListener(dl);
        view.getjTextFieldAcrescimo().getDocument().addDocumentListener(dl);
    }

    private void recalcular() {
        try {
            BigDecimal orig   = parseBD(view.getjTextFieldValorOriginal().getText());
            BigDecimal prod   = parseBD(view.getjTextFieldValorProdutos().getText());
            BigDecimal desc   = parseBD(view.getjTextFieldDesconto().getText());
            BigDecimal acresc = parseBD(view.getjTextFieldAcrescimo().getText());
            view.getjTextFieldValorPagar().setText(orig.add(prod).subtract(desc).add(acresc).toPlainString());
        } catch (Exception ignored) {
            view.getjTextFieldValorPagar().setText("0.00");
        }
    }

    private boolean validarCheckIn() {
        LocalDateTime dtCadastro = parseData(view.getjFormattedTextFieldDataCadastro().getText());
        LocalDateTime dtEntrada  = parseData(view.getjFormattedTextFieldDataEntrada().getText());
        LocalDateTime dtSaida    = parseData(view.getjFormattedTextFieldDataSaida().getText());

        if (dtEntrada == null || dtSaida == null || dtCadastro == null) {
            mensagem("Verifique o formato das datas (dd/mm/aaaa).");
            view.getjTabbedPane().setSelectedIndex(0);
            return false;
        }
        if (dtEntrada.toLocalDate().isBefore(dtCadastro.toLocalDate())) {
            mensagem("A Data de Entrada não pode ser anterior à Data de Cadastro.");
            view.getjTabbedPane().setSelectedIndex(0);
            return false;
        }
        if (dtSaida.toLocalDate().isBefore(dtEntrada.toLocalDate())) {
            mensagem("A Data de Saída não pode ser anterior à Data de Entrada.");
            view.getjTabbedPane().setSelectedIndex(0);
            return false;
        }
        if (hospedesAlocados.isEmpty()) {
            mensagem("Nenhum hóspede alocado.");
            view.getjTabbedPane().setSelectedIndex(1);
            return false;
        }
        boolean hasTitular = false;
        for (int i = 0; i < view.getjTableHospedes().getRowCount(); i++) {
            if ("Titular".equals(view.getjTableHospedes().getValueAt(i, 2).toString())) {
                hasTitular = true;
                break;
            }
        }
        if (!hasTitular) {
            mensagem("O check-in precisa de pelo menos um hóspede Titular.");
            view.getjTabbedPane().setSelectedIndex(1);
            return false;
        }
        if (quartosAlocados.isEmpty()) {
            mensagem("Adicione ao menos um Quarto.");
            view.getjTabbedPane().setSelectedIndex(2);
            return false;
        }
        if (parseBD(view.getjTextFieldValorPago().getText()).compareTo(BigDecimal.ZERO) < 0) {
            mensagem("O Valor Pago não pode ser negativo.");
            view.getjTabbedPane().setSelectedIndex(4);
            return false;
        }
        return true;
    }

    private void setModoEdicao(boolean editando) {
        view.getjButtonNovo().setEnabled(!editando);
        view.getjButtonBuscar().setEnabled(!editando);
        view.getjButtonCancelar().setEnabled(editando);
        view.getjButtonGravar().setEnabled(editando);
        view.getjFormattedTextFieldDataEntrada().setEditable(editando);
        view.getjFormattedTextFieldDataSaida().setEditable(editando);
        view.getjTextFieldObs().setEditable(editando);
        view.getjButtonRelacionarReserva().setEnabled(editando);
        view.getjButtonRelacionarHospede().setEnabled(editando);
        view.getjButtonAlocarHospede().setEnabled(editando);
        view.getjButtonRemoverHospede().setEnabled(editando);
        view.getjComboBoxTipoHospede().setEnabled(editando);
        view.getjTextFieldObsHospede().setEditable(editando);
        view.getjButtonRelacionarQuarto().setEnabled(editando);
        view.getjButtonAlocarQuarto().setEnabled(editando);
        view.getjButtonRemoverQuarto().setEnabled(editando);
        view.getjTextFieldObsQuarto().setEditable(editando);
        view.getjButtonRelacionarVeiculo().setEnabled(editando);
        view.getjButtonRelacionarVaga().setEnabled(editando);
        view.getjButtonAlocarVaga().setEnabled(editando);
        view.getjButtonRemoverVaga().setEnabled(editando);
        view.getjTextFieldObsVaga().setEditable(editando);
        view.getjTextFieldValorOriginal().setEditable(editando);
        view.getjTextFieldDesconto().setEditable(editando);
        view.getjTextFieldAcrescimo().setEditable(editando);
        view.getjTextFieldValorPago().setEditable(editando);
        view.getjTextFieldObsRecebimento().setEditable(editando);
        view.getjComboBoxStatusRecebimento().setEnabled(editando);

        JButton btnCheckout = obterBotaoCheckout();
        if (btnCheckout != null && editando) btnCheckout.setEnabled(false);
    }

    private void limparFormulario() {
        view.getjTextFieldId().setText("");
        view.getjComboBoxStatus().setSelectedIndex(0);
        view.getjFormattedTextFieldDataCadastro().setText("");
        view.getjFormattedTextFieldDataEntrada().setText("");
        view.getjFormattedTextFieldDataSaida().setText("");
        view.getjFormattedTextFieldReserva().setText("");
        view.getjTextFieldObs().setText("");
        view.getjFormattedTextFieldHospede().setText("");
        view.getjTextFieldObsHospede().setText("");
        view.getjComboBoxTipoHospede().setSelectedIndex(0);
        limparTabela(view.getjTableHospedes());
        view.getjFormattedTextFieldQuarto().setText("");
        view.getjTextFieldObsQuarto().setText("");
        limparTabela(view.getjTableQuartos());
        view.getjFormattedTextFieldVeiculo().setText("");
        view.getjFormattedTextFieldVaga().setText("");
        view.getjTextFieldObsVaga().setText("");
        limparTabela(view.getjTableAlocacoesVagas());
        view.getjTextFieldValorOriginal().setText("0.00");
        view.getjTextFieldDesconto().setText("0.00");
        view.getjTextFieldAcrescimo().setText("0.00");
        view.getjTextFieldValorProdutos().setText("0.00");
        view.getjTextFieldValorPagar().setText("0.00");
        view.getjTextFieldValorPago().setText("0.00");
        view.getjTextFieldObsRecebimento().setText("");
        view.getjComboBoxStatusRecebimento().setSelectedIndex(0);
        hospedesAlocados.clear();
        quartosAlocados.clear();
        vagasAlocadas.clear();
        hospedePendente  = null;
        quartoPendente   = null;
        veiculoPendente  = null;
        vagaPendente     = null;
        reservaCheckIn   = null;
    }

    private void adicionarLinhaTabela(javax.swing.JTable tabela, Object[] linha) {
        ((DefaultTableModel) tabela.getModel()).addRow(linha);
    }

    private void limparTabela(javax.swing.JTable tabela) {
        ((DefaultTableModel) tabela.getModel()).setRowCount(0);
    }

    private LocalDateTime parseData(String texto) {
        if (texto == null) return null;
        String nums = Utilities.apenasNumeros(texto);
        if (nums.length() != 8) return null;
        try {
            return LocalDate.parse(nums, DateTimeFormatter.ofPattern("ddMMuuuu")).atStartOfDay();
        } catch (Exception e) { return null; }
    }

    private BigDecimal parseBD(String texto) {
        if (texto == null || texto.trim().isEmpty()) return BigDecimal.ZERO;
        String clean = texto.trim().replace("R$", "").replace(" ", "");
        if (clean.contains(",") && clean.contains(".")) {
            clean = clean.replace(".", "").replace(",", ".");
        } else if (clean.contains(",")) {
            clean = clean.replace(",", ".");
        }
        try { return new BigDecimal(clean); }
        catch (Exception e) { return BigDecimal.ZERO; }
    }

    private char statusRecebimento(javax.swing.JComboBox<String> cb) {
        Object sel = cb.getSelectedItem();
        return (sel != null && "Pago".equals(sel.toString())) ? 'P' : 'A';
    }

    private JButton obterBotaoCheckout() {
        for (java.awt.Component c : view.getjPanelBotoes().getComponents()) {
            if (c instanceof JButton && "checkout".equals(((JButton) c).getName())) {
                return (JButton) c;
            }
        }
        JButton btnCheckout = new JButton("Check-out");
        btnCheckout.setName("checkout");
        btnCheckout.setEnabled(false);
        btnCheckout.setForeground(new java.awt.Color(150, 0, 0));
        btnCheckout.setFont(btnCheckout.getFont().deriveFont(java.awt.Font.BOLD));
        try {
            btnCheckout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Exit.png")));
        } catch (Exception ignored) {}

        view.getjPanelBotoes().add(btnCheckout, view.getjPanelBotoes().getComponentCount() - 1);
        view.getjPanelBotoes().revalidate();
        view.getjPanelBotoes().repaint();
        btnCheckout.addActionListener(this);
        return btnCheckout;
    }

    private void mensagem(String msg) {
        JOptionPane.showMessageDialog(view, msg);
    }

    private void erro(String msg) {
        JOptionPane.showMessageDialog(view, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}