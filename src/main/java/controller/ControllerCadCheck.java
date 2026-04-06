package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import model.AlocacaoVaga;
import model.Check;
import model.CheckHospede;
import model.CheckQuarto;
import model.Hospede;
import model.Quarto;
import model.Receber;
import model.Reserva;
import model.ReservaQuarto;
import model.VagaEstacionamento;
import model.Veiculo;
import service.AlocacaoVagaService;
import service.CheckHospedeService;
import service.CheckQuartoService;
import service.CheckService;
import service.HospedeService;
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

/**
 * Controller completo de Check-in / Check-out.
 *
 * Fluxo Check-in: 1) Novo → habilita edição (abas Check, Hóspedes, Quarto,
 * Vaga, Recebimento) 2) Usuário preenche cada aba 3) Gravar → valida → persiste
 * CheckQuarto + Check + CheckHospedes + AlocacaoVagas + Receber 4) Se Reserva
 * foi vinculada → atualiza status da Reserva e ReservaQuarto para 'F'
 *
 * Fluxo Check-out (aba "Check-out"): 1) Buscar check ativo 2) Informar data
 * saída, valor pago e obs 3) Gravar → atualiza Check e CheckQuarto para 'F' →
 * cria Receber
 */
public class ControllerCadCheck implements ActionListener {

    private final TelaCheck view;

    private final CheckService checkService = new CheckService();
    private final CheckQuartoService checkQuartoService = new CheckQuartoService();
    private final CheckHospedeService checkHospedeService = new CheckHospedeService();
    private final AlocacaoVagaService alocacaoVagaService = new AlocacaoVagaService();
    private final ReceberService receberService = new ReceberService();
    private final HospedeService hospedeService = new HospedeService();
    private final QuartoService quartoService = new QuartoService();
    private final VeiculoService veiculoService = new VeiculoService();
    private final VagaEstacionamentoService vagaService = new VagaEstacionamentoService();
    private final ReservaService reservaService = new ReservaService();
    private final ReservaQuartoService reservaQuartoService = new ReservaQuartoService();

    /**
     * Hóspede sendo preparado para alocar na aba Hóspedes
     */
    private Hospede hospedePendente = null;
    /**
     * Quarto sendo preparado para alocar na aba Quarto
     */
    private Quarto quartoPendente = null;
    /**
     * Veículo sendo preparado para alocar na aba Vaga
     */
    private Veiculo veiculoPendente = null;
    /**
     * Vaga sendo preparada para alocar
     */
    private VagaEstacionamento vagaPendente = null;
    /**
     * Reserva vinculada ao check-in
     */
    private Reserva reservaCheckIn = null;
    /**
     * Check ativo selecionado para checkout
     */
    private Check checkParaCheckout = null;
    /**
     * Indica se estamos em modo de edição
     */
    private boolean modoEdicao = false;

    private final List<Hospede> hospedesAlocados = new ArrayList<>();
    private final List<Quarto> quartosAlocados = new ArrayList<>();
    private final List<Object[]> vagasAlocadas = new ArrayList<>();
    // vagasAlocadas: cada entry = { Veiculo, VagaEstacionamento, obsString }

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
        view.getjTextFieldValorProdutos().setEditable(false);
        view.getjTextFieldValorPagar().setEditable(false);
        view.getjFormattedTextFieldCheckoutId().setEnabled(false);

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

        view.getjButtonBuscarCheckout().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == view.getjButtonNovo()) {
            handleNovo();
        } else if (src == view.getjButtonCancelar()) {
            handleCancelar();
        } else if (src == view.getjButtonGravar()) {
            handleGravar();
        } else if (src == view.getjButtonBuscar()) {
            handleBuscar();
        } else if (src == view.getjButtonSair()) {
            view.dispose();
        } else if (src == view.getjButtonRelacionarReserva()) {
            handleBuscarReserva();
        } else if (src == view.getjButtonRelacionarHospede()) {
            handleBuscarHospede();
        } else if (src == view.getjButtonAlocarHospede()) {
            handleAlocarHospede();
        } else if (src == view.getjButtonRemoverHospede()) {
            handleRemoverHospede();
        } else if (src == view.getjButtonRelacionarQuarto()) {
            handleBuscarQuarto();
        } else if (src == view.getjButtonAlocarQuarto()) {
            handleAlocarQuarto();
        } else if (src == view.getjButtonRemoverQuarto()) {
            handleRemoverQuarto();
        } else if (src == view.getjButtonRelacionarVeiculo()) {
            handleBuscarVeiculo();
        } else if (src == view.getjButtonRelacionarVaga()) {
            handleBuscarVaga();
        } else if (src == view.getjButtonAlocarVaga()) {
            handleAlocarVaga();
        } else if (src == view.getjButtonRemoverVaga()) {
            handleRemoverVaga();
        } else if (src == view.getjButtonBuscarCheckout()) {
            handleBuscarCheckParaCheckout();
        }
    }

    private void handleNovo() {
        modoEdicao = true;
        limparFormulario();
        setModoEdicao(true);

        view.getjFormattedTextFieldDataEntrada()
                .setText(Utilities.getDataHoje());
        view.getjFormattedTextFieldDataCadastro()
                .setText(Utilities.getDataHoje());
        view.getjComboBoxStatus().setSelectedItem("Ativo");
        view.getjComboBoxStatusRecebimento().setSelectedItem("Pendente");

        view.getjTextFieldValorOriginal().setText("0.00");
        view.getjTextFieldDesconto().setText("0.00");
        view.getjTextFieldAcrescimo().setText("0.00");
        view.getjTextFieldValorProdutos().setText("0.00");
        view.getjTextFieldValorPago().setText("0.00");

        view.getjTabbedPane().setSelectedIndex(0);
    }

    private void handleCancelar() {
        modoEdicao = false;
        limparFormulario();
        setModoEdicao(false);
    }

    private void handleGravar() {
        int tabAtiva = view.getjTabbedPane().getSelectedIndex();
        if (tabAtiva == 5) {
            handleGravarCheckOut();
        } else {
            handleGravarCheckIn();
        }
    }

    private void handleBuscar() {
        int[] holder = {0};
        TelaBuscaCheck tela = new TelaBuscaCheck(null, true);
        new ControllerBuscaCheck(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Check check = checkService.Carregar(holder[0]);
                if (check != null) {
                    carregarCheckParaEdicao(check);
                }
            } catch (Exception ex) {
                erro("Erro ao carregar check: " + ex.getMessage());
            }
        }
    }

    private void handleGravarCheckIn() {
        if (!validarCheckIn()) {
            return;
        }

        try {
            Quarto quarto = quartosAlocados.get(0);

            CheckQuarto cq = new CheckQuarto();
            cq.setQuarto(quarto);
            cq.setDataHoraInicio(LocalDateTime.now());
            cq.setDataHoraFim(calcularDataFim());
            cq.setObs(view.getjTextFieldObsQuarto().getText().trim());
            cq.setStatus('A');

            if (reservaCheckIn != null) {
                try {
                    List<ReservaQuarto> rqList
                            = reservaQuartoService.findByReservaId(reservaCheckIn.getId());
                    if (!rqList.isEmpty()) {
                        cq.setReservaQuarto(rqList.get(0));
                    }
                } catch (Exception ignored) {
                }
            }
            checkQuartoService.Criar(cq);

            Check check = new Check();
            check.setDataHoraCadastro(LocalDateTime.now());
            check.setDataHoraEntrada(parseData(view.getjFormattedTextFieldDataEntrada().getText()));
            check.setDataHoraSaida(calcularDataFim());
            check.setObs(view.getjTextFieldObs().getText().trim());
            check.setStatus('A');
            check.setCheckQuarto(cq);
            if (reservaCheckIn != null) {
                check.setReserva(reservaCheckIn);
            }
            checkService.Criar(check);

            cq.setCheck(check);
            checkQuartoService.Atualizar(cq);

            for (Hospede h : hospedesAlocados) {
                CheckHospede ch = new CheckHospede();
                ch.setCheck(check);
                ch.setHospede(h);
                ch.setTipoHospede(obterTipoHospedeSelecionado());
                ch.setObs(view.getjTextFieldObsHospede().getText().trim());
                ch.setStatus('A');
                checkHospedeService.Criar(ch);
            }

            for (Object[] entry : vagasAlocadas) {
                Veiculo veiculo = (Veiculo) entry[0];
                VagaEstacionamento vaga = (VagaEstacionamento) entry[1];
                String obs = (String) entry[2];
                AlocacaoVaga av = new AlocacaoVaga();
                av.setCheck(check);
                av.setVeiculo(veiculo);
                av.setVagaEstacionamento(vaga);
                av.setObs(obs);
                av.setStatus('A');
                alocacaoVagaService.Criar(av);
            }

            criarReceberSePreenchido(check);

            if (reservaCheckIn != null) {
                reservaCheckIn.setCheck(check);
                reservaCheckIn.setStatus('F');
                reservaService.Atualizar(reservaCheckIn);

                if (cq.getReservaQuarto() != null) {
                    ReservaQuarto rq = cq.getReservaQuarto();
                    rq.setStatus('F');
                    reservaQuartoService.Atualizar(rq);
                }
            }

            view.getjTextFieldId().setText(String.valueOf(check.getId()));
            mensagem("Check-in realizado com sucesso!\nID do Check: " + check.getId());
            setModoEdicao(false);
            modoEdicao = false;

        } catch (Exception ex) {
            erro("Erro ao realizar check-in: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void criarReceberSePreenchido(Check check) throws Exception {
        String valOrigStr = view.getjTextFieldValorOriginal().getText().trim();
        if (valOrigStr.isEmpty() || "0.00".equals(valOrigStr)) {
            return;
        }

        BigDecimal valOriginal = parseBD(valOrigStr);
        BigDecimal desconto = parseBD(view.getjTextFieldDesconto().getText());
        BigDecimal acrescimo = parseBD(view.getjTextFieldAcrescimo().getText());
        BigDecimal valProdutos = parseBD(view.getjTextFieldValorProdutos().getText());
        BigDecimal valPago = parseBD(view.getjTextFieldValorPago().getText());

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

    private void handleGravarCheckOut() {
        if (checkParaCheckout == null) {
            mensagem("Selecione um check-in ativo na aba 'Check-out'.");
            return;
        }

        String dataSaidaStr = view.getjFormattedTextFieldDataSaidaCheckout().getText();
        if (Utilities.apenasNumeros(dataSaidaStr).length() != 8) {
            mensagem("Informe a Data de Saída (dd/mm/aaaa).");
            view.getjFormattedTextFieldDataSaidaCheckout().requestFocus();
            return;
        }

        try {
            LocalDateTime dataSaida = parseData(dataSaidaStr);
            if (dataSaida == null) {
                dataSaida = LocalDateTime.now();
            }

            BigDecimal valPago = parseBD(view.getjTextFieldValorPagoCheckout().getText());

            checkParaCheckout.setStatus('F');
            checkParaCheckout.setDataHoraSaida(dataSaida);
            checkService.Atualizar(checkParaCheckout);

            CheckQuarto cq = checkParaCheckout.getCheckQuarto();
            if (cq != null) {
                cq.setStatus('F');
                cq.setDataHoraFim(dataSaida);
                checkQuartoService.Atualizar(cq);
            }

            BigDecimal valOriginal = calcularValorDiarias(checkParaCheckout, dataSaida);

            Receber rec = new Receber();
            rec.setCheck(checkParaCheckout);
            rec.setDataHoraCadastro(LocalDateTime.now());
            rec.setValorOriginal(valOriginal);
            rec.setDesconto(BigDecimal.ZERO);
            rec.setAcrescimo(BigDecimal.ZERO);
            rec.setValorPago(valPago);
            rec.setObs(view.getjTextFieldObsCheckout().getText().trim());
            rec.setStatus(statusRecebimento(view.getjComboBoxStatusRecebimentoCheckout()));
            receberService.Criar(rec);

            mensagem("Check-out realizado com sucesso!\nCheck ID: "
                    + checkParaCheckout.getId()
                    + "\nValor pago: R$ " + valPago.toPlainString());

            limparAbaCheckout();
            checkParaCheckout = null;

        } catch (Exception ex) {
            erro("Erro ao realizar check-out: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Carrega um Check existente para edição
     */
    private void carregarCheckParaEdicao(Check check) {
        modoEdicao = true;
        limparFormulario();
        setModoEdicao(true);

        view.getjTextFieldId().setText(String.valueOf(check.getId()));
        view.getjComboBoxStatus().setSelectedItem(
                check.getStatus() == 'A' ? "Ativo" : "Inativo");
        view.getjFormattedTextFieldDataEntrada()
                .setText(Utilities.formatarData(check.getDataHoraEntrada()));
        if (check.getDataHoraSaida() != null) {
            view.getjFormattedTextFieldDataSaida()
                    .setText(Utilities.formatarData(check.getDataHoraSaida()));
        }
        view.getjTextFieldObs().setText(check.getObs());

        if (check.getReserva() != null) {
            reservaCheckIn = check.getReserva();
            view.getjFormattedTextFieldReserva()
                    .setText(String.valueOf(reservaCheckIn.getId()));
        }

        if (check.getCheckQuarto() != null && check.getCheckQuarto().getQuarto() != null) {
            Quarto q = check.getCheckQuarto().getQuarto();
            quartosAlocados.add(q);
            adicionarLinhaTabela(view.getjTableQuartos(),
                    new Object[]{q.getId(), q.getIdentificacao(), q.getDescricao(),
                        q.getObs(), q.getStatus()});
        }
        view.getjTabbedPane().setSelectedIndex(0);
    }

    private void handleBuscarReserva() {
        if (!modoEdicao) {
            return;
        }
        int[] holder = {0};
        TelaBuscaReserva tela = new TelaBuscaReserva(null, true);
        new ControllerBuscaReserva(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Reserva r = reservaService.Carregar(holder[0]);
                if (r == null) {
                    mensagem("Reserva não encontrada.");
                    return;
                }
                if (r.getStatus() != 'A') {
                    mensagem("A reserva selecionada não está ativa (status: '"
                            + r.getStatus() + "'). Selecione uma reserva ativa.");
                    return;
                }
                reservaCheckIn = r;
                view.getjFormattedTextFieldReserva()
                        .setText(String.valueOf(r.getId()));

                if (r.getDataPrevistaEntrada() != null) {
                    view.getjFormattedTextFieldDataEntrada()
                            .setText(Utilities.formatarData(r.getDataPrevistaEntrada()));
                }
                if (r.getDataPrevistaSaida() != null) {
                    view.getjFormattedTextFieldDataSaida()
                            .setText(Utilities.formatarData(r.getDataPrevistaSaida()));
                }

                try {
                    List<ReservaQuarto> rqList
                            = reservaQuartoService.findByReservaId(r.getId());
                    if (!rqList.isEmpty()) {
                        ReservaQuarto rq = rqList.get(0);
                        if (rq.getQuarto() != null && quartosAlocados.isEmpty()) {
                            quartosAlocados.add(rq.getQuarto());
                            Quarto q = rq.getQuarto();
                            adicionarLinhaTabela(view.getjTableQuartos(),
                                    new Object[]{q.getId(), q.getIdentificacao(),
                                        q.getDescricao(), q.getObs(), q.getStatus()});
                            view.getjFormattedTextFieldQuarto()
                                    .setText(q.getIdentificacao() + " – " + q.getDescricao());
                        }
                    }
                } catch (Exception ignored) {
                }

            } catch (Exception ex) {
                erro("Erro ao carregar reserva: " + ex.getMessage());
            }
        }
    }

    private void handleBuscarHospede() {
        if (!modoEdicao) {
            return;
        }
        int[] holder = {0};
        TelaBuscaHospede tela = new TelaBuscaHospede(null, true);
        new ControllerBuscaHospede(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Hospede h = hospedeService.Carregar(holder[0]);
                if (h != null) {
                    hospedePendente = h;
                    view.getjFormattedTextFieldHospede()
                            .setText(h.getId() + " – " + h.getNome());
                }
            } catch (Exception ex) {
                erro("Erro ao carregar hóspede: " + ex.getMessage());
            }
        }
    }

    private void handleAlocarHospede() {
        if (hospedePendente == null) {
            mensagem("Selecione um hóspede antes de alocar.");
            return;
        }
        hospedesAlocados.add(hospedePendente);
        adicionarLinhaTabela(view.getjTableHospedes(),
                new Object[]{
                    hospedePendente.getId(),
                    hospedePendente.getNome(),
                    obterTipoHospedeSelecionado(),
                    view.getjTextFieldObsHospede().getText().trim(),
                    hospedePendente.getStatus()
                });
        hospedePendente = null;
        view.getjFormattedTextFieldHospede().setText("");
        view.getjTextFieldObsHospede().setText("");
    }

    private void handleRemoverHospede() {
        int row = view.getjTableHospedes().getSelectedRow();
        if (row == -1) {
            mensagem("Selecione um hóspede para remover.");
            return;
        }
        hospedesAlocados.remove(row);
        ((DefaultTableModel) view.getjTableHospedes().getModel()).removeRow(row);
    }

    private void handleBuscarQuarto() {
        if (!modoEdicao) {
            return;
        }
        int[] holder = {0};
        TelaBuscaQuarto tela = new TelaBuscaQuarto(null, true);
        new ControllerBuscaQuarto(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Quarto q = quartoService.Carregar(holder[0]);
                if (q != null) {
                    quartoPendente = q;
                    view.getjFormattedTextFieldQuarto()
                            .setText(q.getIdentificacao() + " – " + q.getDescricao());
                }
            } catch (Exception ex) {
                erro("Erro ao carregar quarto: " + ex.getMessage());
            }
        }
    }

    private void handleAlocarQuarto() {
        if (quartoPendente == null) {
            mensagem("Selecione um quarto antes de alocar.");
            return;
        }
        if (!quartosAlocados.isEmpty()) {
            mensagem("Já existe um quarto alocado neste check.\n"
                    + "Remova-o antes de adicionar outro.");
            return;
        }
        quartosAlocados.add(quartoPendente);
        adicionarLinhaTabela(view.getjTableQuartos(),
                new Object[]{
                    quartoPendente.getId(),
                    quartoPendente.getIdentificacao(),
                    quartoPendente.getDescricao(),
                    view.getjTextFieldObsQuarto().getText().trim(),
                    quartoPendente.getStatus()
                });
        quartoPendente = null;
        view.getjTextFieldObsQuarto().setText("");
    }

    private void handleRemoverQuarto() {
        int row = view.getjTableQuartos().getSelectedRow();
        if (row == -1) {
            mensagem("Selecione um quarto para remover.");
            return;
        }
        quartosAlocados.remove(row);
        ((DefaultTableModel) view.getjTableQuartos().getModel()).removeRow(row);
        view.getjFormattedTextFieldQuarto().setText("");
    }

    private void handleBuscarVeiculo() {
        if (!modoEdicao) {
            return;
        }
        int[] holder = {0};
        TelaBuscaVeiculo tela = new TelaBuscaVeiculo(null, true);
        new ControllerBuscaVeiculo(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Veiculo v = veiculoService.Carregar(holder[0]);
                if (v != null) {
                    veiculoPendente = v;
                    view.getjFormattedTextFieldVeiculo()
                            .setText(v.getPlaca() + " – " + v.getCor());
                }
            } catch (Exception ex) {
                erro("Erro ao carregar veículo: " + ex.getMessage());
            }
        }
    }

    private void handleBuscarVaga() {
        if (!modoEdicao) {
            return;
        }
        int[] holder = {0};
        TelaBuscaVaga tela = new TelaBuscaVaga(null, true);
        new ControllerBuscaVagaEstacionamento(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                VagaEstacionamento vaga = vagaService.Carregar(holder[0]);
                if (vaga != null) {
                    vagaPendente = vaga;
                    view.getjFormattedTextFieldVaga()
                            .setText(vaga.getId() + " – " + vaga.getDescricao());
                }
            } catch (Exception ex) {
                erro("Erro ao carregar vaga: " + ex.getMessage());
            }
        }
    }

    private void handleAlocarVaga() {
        if (veiculoPendente == null) {
            mensagem("Selecione um veículo antes de alocar a vaga.");
            return;
        }
        if (vagaPendente == null) {
            mensagem("Selecione uma vaga antes de alocar.");
            return;
        }
        String obs = view.getjTextFieldObsVaga().getText().trim();
        vagasAlocadas.add(new Object[]{veiculoPendente, vagaPendente, obs});

        adicionarLinhaTabela(view.getjTableAlocacoesVagas(),
                new Object[]{
                    vagasAlocadas.size(),
                    veiculoPendente.getPlaca(),
                    vagaPendente.getDescricao(),
                    obs,
                    'A'
                });

        veiculoPendente = null;
        vagaPendente = null;
        view.getjFormattedTextFieldVeiculo().setText("");
        view.getjFormattedTextFieldVaga().setText("");
        view.getjTextFieldObsVaga().setText("");
    }

    private void handleRemoverVaga() {
        int row = view.getjTableAlocacoesVagas().getSelectedRow();
        if (row == -1) {
            mensagem("Selecione uma alocação para remover.");
            return;
        }
        vagasAlocadas.remove(row);
        ((DefaultTableModel) view.getjTableAlocacoesVagas().getModel()).removeRow(row);
    }

    private void handleBuscarCheckParaCheckout() {
        int[] holder = {0};
        TelaBuscaCheck tela = new TelaBuscaCheck(null, true);
        new ControllerBuscaCheck(tela, v -> holder[0] = v);
        tela.setVisible(true);

        if (holder[0] != 0) {
            try {
                Check c = checkService.Carregar(holder[0]);
                if (c == null) {
                    mensagem("Check-in não encontrado.");
                    return;
                }
                if (c.getStatus() == 'F') {
                    mensagem("Este check-in já foi finalizado (checkout realizado).");
                    return;
                }
                checkParaCheckout = c;
                preencherInfoCheckout(c);
            } catch (Exception ex) {
                erro("Erro ao carregar check: " + ex.getMessage());
            }
        }
    }

    private void preencherInfoCheckout(Check c) {
        view.getjFormattedTextFieldCheckoutId()
                .setText(String.valueOf(c.getId()));
        view.getjFormattedTextFieldDataSaidaCheckout()
                .setText(Utilities.getDataHoje());
        view.getjFormattedTextFieldDataSaidaCheckout().setEditable(true);
        view.getjTextFieldObsCheckout().setEditable(true);
        view.getjComboBoxStatusRecebimentoCheckout().setEnabled(true);
        view.getjComboBoxStatusRecebimentoCheckout().setSelectedItem("Pendente");

        BigDecimal estimativa = calcularValorDiarias(c, LocalDateTime.now());

        StringBuilder sb = new StringBuilder();
        sb.append("Check ID   : ").append(c.getId()).append('\n');
        if (c.getDataHoraEntrada() != null) {
            sb.append("Entrada    : ").append(
                    c.getDataHoraEntrada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                    .append('\n');
        }
        if (c.getCheckQuarto() != null && c.getCheckQuarto().getQuarto() != null) {
            Quarto q = c.getCheckQuarto().getQuarto();
            sb.append("Quarto     : ").append(q.getIdentificacao())
                    .append(" – ").append(q.getDescricao()).append('\n');
        }
        sb.append("Status     : ").append(c.getStatus()).append('\n');
        sb.append("Obs        : ").append(c.getObs()).append('\n');
        sb.append("─────────────────────────────────────\n");
        sb.append("Valor est. diárias: R$ ").append(estimativa.toPlainString());

        view.getjTextAreaCheckoutInfo().setText(sb.toString());
        view.getjTextFieldValorPagoCheckout().setText(estimativa.toPlainString());
    }

    private void configurarCalculoRecebimento() {
        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                recalcular();
            }

            public void removeUpdate(DocumentEvent e) {
                recalcular();
            }

            public void changedUpdate(DocumentEvent e) {
                recalcular();
            }
        };
        view.getjTextFieldValorOriginal().getDocument().addDocumentListener(dl);
        view.getjTextFieldValorProdutos().getDocument().addDocumentListener(dl);
        view.getjTextFieldDesconto().getDocument().addDocumentListener(dl);
        view.getjTextFieldAcrescimo().getDocument().addDocumentListener(dl);
    }

    private void recalcular() {
        try {
            BigDecimal orig = parseBD(view.getjTextFieldValorOriginal().getText());
            BigDecimal prod = parseBD(view.getjTextFieldValorProdutos().getText());
            BigDecimal desc = parseBD(view.getjTextFieldDesconto().getText());
            BigDecimal acresc = parseBD(view.getjTextFieldAcrescimo().getText());
            BigDecimal total = orig.add(prod).subtract(desc).add(acresc);
            view.getjTextFieldValorPagar().setText(total.toPlainString());
        } catch (Exception ignored) {
            view.getjTextFieldValorPagar().setText("0.00");
        }
    }

    private boolean validarCheckIn() {
        if (hospedesAlocados.isEmpty()) {
            int op = JOptionPane.showConfirmDialog(view,
                    "Nenhum hóspede alocado. Deseja buscar agora?",
                    "Hóspede obrigatório", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                view.getjTabbedPane().setSelectedIndex(1);
                handleBuscarHospede();
            }
            if (hospedesAlocados.isEmpty()) {
                return false;
            }
        }

        if (quartosAlocados.isEmpty()) {
            mensagem("Adicione ao menos um Quarto na aba 'Quarto'.");
            view.getjTabbedPane().setSelectedIndex(2);
            return false;
        }

        String dtEntrada = view.getjFormattedTextFieldDataEntrada().getText();
        if (Utilities.apenasNumeros(dtEntrada).length() != 8) {
            mensagem("Informe a Data de Entrada (dd/mm/aaaa).");
            view.getjTabbedPane().setSelectedIndex(0);
            view.getjFormattedTextFieldDataEntrada().requestFocus();
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
        hospedePendente = null;
        quartoPendente = null;
        veiculoPendente = null;
        vagaPendente = null;
        reservaCheckIn = null;
    }

    private void limparAbaCheckout() {
        view.getjFormattedTextFieldCheckoutId().setText("");
        view.getjFormattedTextFieldDataSaidaCheckout().setText("");
        view.getjFormattedTextFieldDataSaidaCheckout().setEditable(false);
        view.getjTextAreaCheckoutInfo().setText("");
        view.getjTextFieldValorPagoCheckout().setText("0.00");
        view.getjTextFieldObsCheckout().setText("");
        view.getjTextFieldObsCheckout().setEditable(false);
        view.getjComboBoxStatusRecebimentoCheckout().setEnabled(false);
        view.getjComboBoxStatusRecebimentoCheckout().setSelectedIndex(0);
    }

    private void adicionarLinhaTabela(javax.swing.JTable tabela, Object[] linha) {
        ((DefaultTableModel) tabela.getModel()).addRow(linha);
    }

    private void limparTabela(javax.swing.JTable tabela) {
        ((DefaultTableModel) tabela.getModel()).setRowCount(0);
    }

    private LocalDateTime parseData(String texto) {
        if (texto == null) {
            return null;
        }
        String nums = Utilities.apenasNumeros(texto);
        if (nums.length() != 8) {
            return null;
        }
        try {
            return LocalDate.parse(nums, DateTimeFormatter.ofPattern("ddMMuuuu"))
                    .atStartOfDay();
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDateTime calcularDataFim() {
        String txt = view.getjFormattedTextFieldDataSaida().getText();
        LocalDateTime dt = parseData(txt);
        return (dt != null) ? dt : LocalDateTime.now().plusDays(1);
    }

    private BigDecimal calcularValorDiarias(Check check, LocalDateTime saida) {
        if (check.getDataHoraEntrada() == null) {
            return BigDecimal.ZERO;
        }
        long dias = ChronoUnit.DAYS.between(check.getDataHoraEntrada(), saida);
        if (dias < 1) {
            dias = 1;
        }
        return BigDecimal.valueOf(dias).multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal parseBD(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        String clean = texto.trim()
                .replace("R$", "").replace(" ", "");
        if (clean.contains(",") && clean.contains(".")) {
            clean = clean.replace(".", "").replace(",", ".");
        } else if (clean.contains(",")) {
            clean = clean.replace(",", ".");
        }
        try {
            return new BigDecimal(clean);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private char statusRecebimento(javax.swing.JComboBox<String> cb) {
        Object sel = cb.getSelectedItem();
        return (sel != null && "Pago".equals(sel.toString())) ? 'P' : 'A';
    }

    private String obterTipoHospedeSelecionado() {
        Object sel = view.getjComboBoxTipoHospede().getSelectedItem();
        return (sel != null) ? sel.toString() : "Responsável";
    }

    private void mensagem(String msg) {
        JOptionPane.showMessageDialog(view, msg);
    }

    private void erro(String msg) {
        JOptionPane.showMessageDialog(view, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
