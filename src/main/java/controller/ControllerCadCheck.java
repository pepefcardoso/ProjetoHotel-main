package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import service.CaixaService;
import service.CheckHospedeService;
import service.CheckQuartoService;
import service.CheckService;
import service.CopaQuartoService;
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
 * Controller de Check-in.
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

    // Serviços auxiliares para validação
    private final CaixaService caixaService = new CaixaService();
    private final CopaQuartoService copaQuartoService = new CopaQuartoService();

    private Hospede hospedePendente = null;
    private Quarto quartoPendente = null;
    private Veiculo veiculoPendente = null;
    private VagaEstacionamento vagaPendente = null;
    private Reserva reservaCheckIn = null;
    private boolean modoEdicao = false;

    private final List<Hospede> hospedesAlocados = new ArrayList<>();
    // Cada elemento: Object[]{Quarto, ObsString}
    private final List<Object[]> quartosAlocados = new ArrayList<>();
    // Cada elemento: Object[]{Veiculo, VagaEstacionamento, ObsString}
    private final List<Object[]> vagasAlocadas = new ArrayList<>();

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
    }

    private void handleCancelar() {
        modoEdicao = false;
        limparFormulario();
        setModoEdicao(false);
    }

    private void handleGravar() {
        if (!validarCheckIn()) {
            return;
        }

        try {
            LocalDateTime dtEntrada = parseData(view.getjFormattedTextFieldDataEntrada().getText());
            LocalDateTime dtSaida = parseData(view.getjFormattedTextFieldDataSaida().getText());

            // 1. ISOLATE THE FIRST ROOM to satisfy the Check model's requirement
            Object[] firstRoomInfo = quartosAlocados.get(0);
            Quarto firstQuarto = (Quarto) firstRoomInfo[0];
            String firstObsQuarto = (String) firstRoomInfo[1];

            CheckQuarto cqFirst = new CheckQuarto();
            cqFirst.setQuarto(firstQuarto);
            cqFirst.setDataHoraInicio(dtEntrada);
            cqFirst.setDataHoraFim(dtSaida);
            cqFirst.setObs(firstObsQuarto);
            cqFirst.setStatus('A');
            // Save it first so it gets an ID in the database
            checkQuartoService.Criar(cqFirst);

            // 2. CREATE THE CHECK
            Check check = new Check();
            check.setDataHoraCadastro(parseData(view.getjFormattedTextFieldDataCadastro().getText()));
            check.setDataHoraEntrada(dtEntrada);
            check.setDataHoraSaida(dtSaida);
            check.setObs(view.getjTextFieldObs().getText().trim());
            check.setStatus('A');

            if (reservaCheckIn != null) {
                check.setReserva(reservaCheckIn);
            }

            // Bind the saved room to satisfy the @NotNull constraint in your model
            check.setCheckQuarto(cqFirst);

            // Now Hibernate will allow this to save
            checkService.Criar(check);

            // 3. LINK THE FIRST ROOM BACK TO THE CHECK AND UPDATE IT
            cqFirst.setCheck(check);
            checkQuartoService.Atualizar(cqFirst); // Note: Assumes your service has an Atualizar method

            // 4. SAVE ANY ADDITIONAL ROOMS (Index 1 onwards)
            for (int i = 1; i < quartosAlocados.size(); i++) {
                Object[] qInfo = quartosAlocados.get(i);
                Quarto quarto = (Quarto) qInfo[0];
                String obsQuarto = (String) qInfo[1];

                CheckQuarto cq = new CheckQuarto();
                cq.setCheck(check);
                cq.setQuarto(quarto);
                cq.setDataHoraInicio(dtEntrada);
                cq.setDataHoraFim(dtSaida);
                cq.setObs(obsQuarto);
                cq.setStatus('A');
                checkQuartoService.Criar(cq);
            }

            // 5. SAVE HOSPEDES
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

            // 6. SAVE VAGAS/VEICULOS
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
        BigDecimal valPago = parseBD(view.getjTextFieldValorPago().getText());

        // Regra do caixa apenas se houver movimentação financeira no check-in
        if (valPago.compareTo(BigDecimal.ZERO) > 0) {
            try {
                if (!caixaService.isCaixaAberto()) {
                    throw new Exception("Não há um Caixa aberto para processar o valor recebido.");
                }
            } catch (Exception ex) {
                // Tratativa caso o método exato de serviço não exista na arquitetura atual
                throw new Exception("Problema com integração de Caixa: " + ex.getMessage());
            }
        }

        String valOrigStr = view.getjTextFieldValorOriginal().getText().trim();
        if (valOrigStr.isEmpty() || "0.00".equals(valOrigStr)) {
            return;
        }

        BigDecimal valOriginal = parseBD(valOrigStr);
        BigDecimal desconto = parseBD(view.getjTextFieldDesconto().getText());
        BigDecimal acrescimo = parseBD(view.getjTextFieldAcrescimo().getText());
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
                if (check != null) {
                    carregarCheckParaEdicao(check);
                }
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

        if (check.getDataHoraCadastro() != null) {
            view.getjFormattedTextFieldDataCadastro().setText(Utilities.formatarData(check.getDataHoraCadastro()));
        }
        if (check.getDataHoraEntrada() != null) {
            view.getjFormattedTextFieldDataEntrada().setText(Utilities.formatarData(check.getDataHoraEntrada()));
        }
        if (check.getDataHoraSaida() != null) {
            view.getjFormattedTextFieldDataSaida().setText(Utilities.formatarData(check.getDataHoraSaida()));
        }
        view.getjTextFieldObs().setText(check.getObs());

        if (check.getReserva() != null) {
            reservaCheckIn = check.getReserva();
            view.getjFormattedTextFieldReserva().setText(String.valueOf(reservaCheckIn.getId()));
        }

        // Buscar consumo automático da Copa
        try {
            // Método mock presumido, pode adaptar conforme nome de integração real
            BigDecimal totalConsumo = copaQuartoService.buscarTotalConsumo(check.getId());
            if (totalConsumo != null) {
                view.getjTextFieldValorProdutos().setText(totalConsumo.toPlainString());
            }
        } catch (Exception ignored) {
        }

        // Nota: A lógica de carregar múltiplos quartos/hóspedes do banco para tela de edição dependeria de rotinas de Listagem.
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
                if (r == null || r.getStatus() != 'A') {
                    mensagem("A reserva não foi encontrada ou não está ativa.");
                    return;
                }

                reservaCheckIn = r;
                view.getjFormattedTextFieldReserva().setText(String.valueOf(r.getId()));

                if (r.getDataPrevistaEntrada() != null) {
                    view.getjFormattedTextFieldDataEntrada().setText(Utilities.formatarData(r.getDataPrevistaEntrada()));
                }
                if (r.getDataPrevistaSaida() != null) {
                    view.getjFormattedTextFieldDataSaida().setText(Utilities.formatarData(r.getDataPrevistaSaida()));
                }

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
                    }
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
                    view.getjFormattedTextFieldHospede().setText(h.getId() + " – " + h.getNome());
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

        // Validação de Duplicidade
        boolean exists = hospedesAlocados.stream().anyMatch(h -> h.getId() == hospedePendente.getId());
        if (exists) {
            mensagem("Este hóspede já foi alocado no Check-in atual.");
            return;
        }

        String tipoSelecionado = view.getjComboBoxTipoHospede().getSelectedItem().toString();

        // Validação de Titularidade (Apenas 1 Titular)
        if (tipoSelecionado.equals("Titular")) {
            for (int i = 0; i < view.getjTableHospedes().getRowCount(); i++) {
                if (view.getjTableHospedes().getValueAt(i, 2).toString().equals("Titular")) {
                    mensagem("Não é permitido adicionar mais de um hóspede como Titular.");
                    return;
                }
            }
        }

        hospedesAlocados.add(hospedePendente);
        adicionarLinhaTabela(view.getjTableHospedes(),
                new Object[]{
                    hospedePendente.getId(),
                    hospedePendente.getNome(),
                    tipoSelecionado,
                    view.getjTextFieldObsHospede().getText().trim(),
                    hospedePendente.getStatus()
                });

        hospedePendente = null;
        view.getjFormattedTextFieldHospede().setText("");
        view.getjTextFieldObsHospede().setText("");
        view.getjComboBoxTipoHospede().setSelectedIndex(1); // Set para Acompanhante por default
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
                    view.getjFormattedTextFieldQuarto().setText(q.getIdentificacao() + " – " + q.getDescricao());
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

        // Validação de integração de datas
        LocalDateTime dtEntrada = parseData(view.getjFormattedTextFieldDataEntrada().getText());
        LocalDateTime dtSaida = parseData(view.getjFormattedTextFieldDataSaida().getText());

        if (dtEntrada == null || dtSaida == null) {
            mensagem("Preencha datas de entrada e saída válidas na aba 'Check' antes de alocar quartos.");
            return;
        }

        // Validação de Duplicidade
        boolean exists = quartosAlocados.stream().anyMatch(q -> ((Quarto) q[0]).getId() == quartoPendente.getId());
        if (exists) {
            mensagem("Este quarto já está alocado.");
            return;
        }

        String obs = view.getjTextFieldObsQuarto().getText().trim();
        quartosAlocados.add(new Object[]{quartoPendente, obs});

        adicionarLinhaTabela(view.getjTableQuartos(),
                new Object[]{
                    quartoPendente.getId(),
                    quartoPendente.getIdentificacao(),
                    quartoPendente.getDescricao(),
                    obs,
                    quartoPendente.getStatus()
                });

        quartoPendente = null;
        view.getjFormattedTextFieldQuarto().setText("");
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
                    view.getjFormattedTextFieldVeiculo().setText(v.getPlaca() + " – " + v.getCor());
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
                    view.getjFormattedTextFieldVaga().setText(vaga.getId() + " – " + vaga.getDescricao());
                }
            } catch (Exception ex) {
                erro("Erro ao carregar vaga: " + ex.getMessage());
            }
        }
    }

    private void handleAlocarVaga() {
        if (veiculoPendente == null || vagaPendente == null) {
            mensagem("Para realizar a alocação, você precisa selecionar o Veículo e a Vaga.");
            return;
        }

        // Bloqueia combinações exatas
        for (Object[] v : vagasAlocadas) {
            Veiculo veic = (Veiculo) v[0];
            VagaEstacionamento vaga = (VagaEstacionamento) v[1];
            if (veic.getId() == veiculoPendente.getId() && vaga.getId() == vagaPendente.getId()) {
                mensagem("Essa combinação exata de Veículo e Vaga já está alocada.");
                return;
            }
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
        // Valida Datas
        LocalDateTime dtCadastro = parseData(view.getjFormattedTextFieldDataCadastro().getText());
        LocalDateTime dtEntrada = parseData(view.getjFormattedTextFieldDataEntrada().getText());
        LocalDateTime dtSaida = parseData(view.getjFormattedTextFieldDataSaida().getText());

        if (dtEntrada == null || dtSaida == null || dtCadastro == null) {
            mensagem("Verifique o formato das datas preenchidas (dd/mm/aaaa).");
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

        // Valida Hóspedes Titularidade
        if (hospedesAlocados.isEmpty()) {
            mensagem("Nenhum hóspede alocado.");
            view.getjTabbedPane().setSelectedIndex(1);
            return false;
        }

        boolean hasTitular = false;
        for (int i = 0; i < view.getjTableHospedes().getRowCount(); i++) {
            if (view.getjTableHospedes().getValueAt(i, 2).toString().equals("Titular")) {
                hasTitular = true;
                break;
            }
        }
        if (!hasTitular) {
            mensagem("O check-in precisa ter obrigatoriamente um hóspede Titular.");
            view.getjTabbedPane().setSelectedIndex(1);
            return false;
        }

        // Valida Quartos
        if (quartosAlocados.isEmpty()) {
            mensagem("Adicione ao menos um Quarto na aba 'Quarto'.");
            view.getjTabbedPane().setSelectedIndex(2);
            return false;
        }

        // Valida Financeiro base (Sem valores negativos)
        BigDecimal valPago = parseBD(view.getjTextFieldValorPago().getText());
        if (valPago.compareTo(BigDecimal.ZERO) < 0) {
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
            return LocalDate.parse(nums, DateTimeFormatter.ofPattern("ddMMuuuu")).atStartOfDay();
        } catch (Exception e) {
            return null;
        }
    }

    private BigDecimal parseBD(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        String clean = texto.trim().replace("R$", "").replace(" ", "");
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

    private void mensagem(String msg) {
        JOptionPane.showMessageDialog(view, msg);
    }

    private void erro(String msg) {
        JOptionPane.showMessageDialog(view, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
