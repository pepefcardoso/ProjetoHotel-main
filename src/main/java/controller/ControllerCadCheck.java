package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;

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

public class ControllerCadCheck implements ActionListener {

    private final TelaCheck view;

    private final CheckService               checkService            = new CheckService();
    private final CheckQuartoService         checkQuartoService      = new CheckQuartoService();
    private final CheckHospedeService        checkHospedeService     = new CheckHospedeService();
    private final AlocacaoVagaService        alocacaoVagaService     = new AlocacaoVagaService();
    private final ReceberService             receberService          = new ReceberService();
    private final HospedeService             hospedeService          = new HospedeService();
    private final QuartoService              quartoService           = new QuartoService();
    private final VeiculoService             veiculoService          = new VeiculoService();
    private final VagaEstacionamentoService  vagaService             = new VagaEstacionamentoService();
    private final ReservaService             reservaService          = new ReservaService();
    private final ReservaQuartoService       reservaQuartoService    = new ReservaQuartoService();

    private Hospede            hospedeCheckIn   = null;
    private Quarto             quartoCheckIn    = null;
    private Veiculo            veiculoCheckIn   = null;
    private VagaEstacionamento vagaCheckIn      = null;
    private Reserva            reservaCheckIn   = null;
    private Check              checkParaCheckout = null;
    private boolean            modoEdicao       = false;

    public ControllerCadCheck(TelaCheck view) {
        this.view = view;
        inicializar();
        registrarListeners();
    }

    private void inicializar() {
        view.getjTextFieldId().setEnabled(false);
        view.getjTextFieldHospede().setEnabled(false);
        view.getjTextFieldReservaId().setEnabled(false);
        view.getjFormattedTextFieldDataEntrada().setEditable(false);
        view.getjTextFieldQuarto().setEnabled(false);
        view.getjTextFieldVeiculo().setEnabled(false);
        view.getjTextFieldVaga().setEnabled(false);
        view.getjTextFieldValorPagar().setEditable(false);
        view.getjComboBoxStatusRecebimento().setEnabled(false);
        view.getjFormattedTextFieldDataSaida().setEditable(false);

        definirEstadoBotoes(false);
    }

    private void registrarListeners() {
        view.getjButtonNovo().addActionListener(this);
        view.getjButtonCancelar().addActionListener(this);
        view.getjButtonGravar().addActionListener(this);
        view.getjButtonBuscar().addActionListener(this);
        view.getjButtonSair().addActionListener(this);

        view.getjButtonReservaId().addActionListener(this);
        view.getjButtonQuarto().addActionListener(this);
        view.getjButtonVeiculo().addActionListener(this);
        view.getjButtonVaga().addActionListener(this);
        view.getjButtonQuarto1().addActionListener(this);

        view.getjTextFieldHospede().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (modoEdicao) {
                    handleBuscarHospede();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if      (src == view.getjButtonNovo())       handleNovo();
        else if (src == view.getjButtonCancelar())   handleCancelar();
        else if (src == view.getjButtonGravar())     handleGravar();
        else if (src == view.getjButtonBuscar())     handleBuscar();
        else if (src == view.getjButtonSair())       view.dispose();
        else if (src == view.getjButtonReservaId())  handleBuscarReserva();
        else if (src == view.getjButtonQuarto())     handleBuscarQuartoCheckIn();
        else if (src == view.getjButtonVeiculo())    handleBuscarVeiculo();
        else if (src == view.getjButtonVaga())       handleBuscarVaga();
        else if (src == view.getjButtonQuarto1())    handleBuscarCheckParaCheckout();
    }

    private void handleNovo() {
        modoEdicao = true;
        limparCamposCheckIn();
        definirEstadoBotoes(true);
        view.getjTabbedPane4().setSelectedIndex(0);
        view.getjComboBoxStatus().setSelectedItem("Ativo");
        view.getjFormattedTextFieldDataEntrada().setEditable(true);
        view.getjFormattedTextFieldDataEntrada().setText(Utilities.getDataHoje());
        view.getjTextFieldHospede().setToolTipText("Clique para buscar um hóspede");
    }

    private void handleCancelar() {
        modoEdicao = false;
        limparCamposCheckIn();
        limparCamposCheckout();
        definirEstadoBotoes(false);
        view.getjFormattedTextFieldDataEntrada().setEditable(false);
        view.getjTextFieldHospede().setToolTipText(null);
    }

    private void handleGravar() {
        int tabAtiva = view.getjTabbedPane4().getSelectedIndex();
        if (tabAtiva == 0) {
            handleGravarCheckIn();
        } else {
            handleGravarCheckOut();
        }
    }

    private void handleGravarCheckIn() {
        if (!validarCheckIn()) return;

        try {
            CheckQuarto checkQuarto = new CheckQuarto();
            checkQuarto.setQuarto(quartoCheckIn);
            checkQuarto.setDataHoraInicio(LocalDateTime.now());

            LocalDateTime dataFimPrevista = (reservaCheckIn != null
                    && reservaCheckIn.getDataPrevistaSaida() != null)
                    ? reservaCheckIn.getDataPrevistaSaida()
                    : LocalDateTime.now().plusDays(1);
            checkQuarto.setDataHoraFim(dataFimPrevista);
            checkQuarto.setObs(view.getjTextFieldObservacao().getText());
            checkQuarto.setStatus('A');

            if (reservaCheckIn != null) {
                try {
                    List<ReservaQuarto> rqList =
                            reservaQuartoService.findByReservaId(reservaCheckIn.getId());
                    if (!rqList.isEmpty()) {
                        checkQuarto.setReservaQuarto(rqList.get(0));
                    }
                } catch (Exception ex) {
                    // Reserva sem quarto vinculado, prosseguir sem ReservaQuarto
                }
            }
            checkQuartoService.Criar(checkQuarto);

            Check check = new Check();
            check.setDataHoraCadastro(LocalDateTime.now());
            check.setDataHoraEntrada(parseData(view.getjFormattedTextFieldDataEntrada().getText()));
            check.setDataHoraSaida(dataFimPrevista);
            check.setObs(view.getjTextFieldObservacao().getText());
            check.setStatus('A');
            check.setCheckQuarto(checkQuarto);

            if (reservaCheckIn != null) {
                check.setReserva(reservaCheckIn);
            }
            checkService.Criar(check);

            checkQuarto.setCheck(check);
            checkQuartoService.Atualizar(checkQuarto);

            CheckHospede checkHospede = new CheckHospede();
            checkHospede.setCheck(check);
            checkHospede.setHospede(hospedeCheckIn);
            checkHospede.setTipoHospede("Responsável");
            checkHospede.setObs("");
            checkHospede.setStatus('A');
            checkHospedeService.Criar(checkHospede);

            if (veiculoCheckIn != null && vagaCheckIn != null) {
                AlocacaoVaga alocacao = new AlocacaoVaga();
                alocacao.setCheck(check);
                alocacao.setVeiculo(veiculoCheckIn);
                alocacao.setVagaEstacionamento(vagaCheckIn);
                alocacao.setObs("");
                alocacao.setStatus('A');
                alocacaoVagaService.Criar(alocacao);
            }

            if (reservaCheckIn != null) {
                reservaCheckIn.setCheck(check);
                reservaCheckIn.setStatus('F');
                reservaService.Atualizar(reservaCheckIn);

                if (checkQuarto.getReservaQuarto() != null) {
                    ReservaQuarto rq = checkQuarto.getReservaQuarto();
                    rq.setStatus('F');
                    reservaQuartoService.Atualizar(rq);
                }
            }

            view.getjTextFieldId().setText(String.valueOf(check.getId()));
            JOptionPane.showMessageDialog(view,
                    "Check-in realizado com sucesso!\nID do Check: " + check.getId());
            handleCancelar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                    "Erro ao realizar check-in: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void handleGravarCheckOut() {
        if (checkParaCheckout == null) {
            JOptionPane.showMessageDialog(view,
                    "Selecione um Check-in ativo para realizar o checkout.");
            return;
        }

        String dataSaidaStr = view.getjFormattedTextFieldDataSaida().getText();
        if (dataSaidaStr == null || Utilities.apenasNumeros(dataSaidaStr).length() != 8) {
            JOptionPane.showMessageDialog(view, "Informe a Data de Saída (dd/mm/aaaa).");
            view.getjFormattedTextFieldDataSaida().requestFocus();
            return;
        }

        String valorPagoRaw = view.getjTextFieldValorPago().getText()
                .replace("R$", "").replace(".", "").replace(",", ".").trim();
        BigDecimal valorPago;
        try {
            valorPago = new BigDecimal(valorPagoRaw.isEmpty() ? "0" : valorPagoRaw);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Valor Pago inválido.");
            view.getjTextFieldValorPago().requestFocus();
            return;
        }

        try {
            LocalDateTime dataSaida = parseData(dataSaidaStr);
            if (dataSaida == null) dataSaida = LocalDateTime.now();

            String valorPagarRaw = view.getjTextFieldValorPagar().getText()
                    .replace("R$", "").replace(".", "").replace(",", ".").trim();
            BigDecimal valorOriginal;
            try {
                valorOriginal = new BigDecimal(valorPagarRaw.isEmpty() ? "0" : valorPagarRaw);
            } catch (NumberFormatException ex) {
                valorOriginal = BigDecimal.ZERO;
            }

            checkParaCheckout.setStatus('F');
            checkParaCheckout.setDataHoraSaida(dataSaida);
            checkService.Atualizar(checkParaCheckout);

            CheckQuarto cq = checkParaCheckout.getCheckQuarto();
            if (cq != null) {
                cq.setStatus('F');
                cq.setDataHoraFim(dataSaida);
                checkQuartoService.Atualizar(cq);
            }

            Object statusRec = view.getjComboBoxStatusRecebimento().getSelectedItem();
            char statusRecChar = (statusRec != null && statusRec.toString().equals("Ativo"))
                    ? 'A' : 'P';

            Receber receber = new Receber();
            receber.setCheck(checkParaCheckout);
            receber.setDataHoraCadastro(LocalDateTime.now());
            receber.setValorOriginal(valorOriginal);
            receber.setDesconto(BigDecimal.ZERO);
            receber.setAcrescimo(BigDecimal.ZERO);
            receber.setValorPago(valorPago);
            receber.setObs(view.getjTextFieldObservacao1().getText());
            receber.setStatus(statusRecChar);
            receberService.Criar(receber);

            JOptionPane.showMessageDialog(view,
                    "Check-out realizado com sucesso!\nCheck ID: " + checkParaCheckout.getId()
                    + "\nValor pago: R$ " + valorPago);
            limparCamposCheckout();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                    "Erro ao realizar check-out: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void handleBuscar() {
        int[] codigoHolder = {0};
        TelaBuscaCheck telaBusca = new TelaBuscaCheck(null, true);
        new ControllerBuscaCheck(telaBusca, val -> codigoHolder[0] = val);
        telaBusca.setVisible(true);

        if (codigoHolder[0] != 0) {
            try {
                Check check = checkService.Carregar(codigoHolder[0]);
                if (check != null) {
                    modoEdicao = true;
                    definirEstadoBotoes(true);
                    view.getjTextFieldId().setText(String.valueOf(check.getId()));
                    view.getjFormattedTextFieldDataEntrada().setEditable(true);
                    view.getjFormattedTextFieldDataEntrada().setText(
                            Utilities.formatarData(check.getDataHoraEntrada()));
                    view.getjComboBoxStatus().setSelectedItem(
                            check.getStatus() == 'A' ? "Ativo" : "Inativo");
                    view.getjTextFieldObservacao().setText(check.getObs());

                    if (check.getReserva() != null) {
                        view.getjTextFieldReservaId().setText(
                                String.valueOf(check.getReserva().getId()));
                        reservaCheckIn = check.getReserva();
                    }
                    if (check.getCheckQuarto() != null
                            && check.getCheckQuarto().getQuarto() != null) {
                        quartoCheckIn = check.getCheckQuarto().getQuarto();
                        view.getjTextFieldQuarto().setText(
                                quartoCheckIn.getIdentificacao()
                                + " - " + quartoCheckIn.getDescricao());
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                        "Erro ao carregar check: " + ex.getMessage());
            }
        }
    }

    private void handleBuscarHospede() {
        int[] codigoHolder = {0};
        TelaBuscaHospede tela = new TelaBuscaHospede(null, true);
        new ControllerBuscaHospede(tela, val -> codigoHolder[0] = val);
        tela.setVisible(true);

        if (codigoHolder[0] != 0) {
            try {
                Hospede h = hospedeService.Carregar(codigoHolder[0]);
                if (h != null) {
                    hospedeCheckIn = h;
                    view.getjTextFieldHospede().setText(h.getNome());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                        "Erro ao carregar hóspede: " + ex.getMessage());
            }
        }
    }

    private void handleBuscarReserva() {
        int[] codigoHolder = {0};
        TelaBuscaReserva telaBusca = new TelaBuscaReserva(null, true);
        new ControllerBuscaReserva(telaBusca, val -> codigoHolder[0] = val);
        telaBusca.setVisible(true);

        if (codigoHolder[0] != 0) {
            try {
                Reserva r = reservaService.Carregar(codigoHolder[0]);
                if (r == null) {
                    JOptionPane.showMessageDialog(view, "Reserva não encontrada.");
                    return;
                }
                if (r.getStatus() != 'A') {
                    JOptionPane.showMessageDialog(view,
                            "A reserva selecionada não está ativa (status: '"
                            + r.getStatus() + "'). Selecione uma reserva com status 'A'.");
                    return;
                }
                reservaCheckIn = r;
                view.getjTextFieldReservaId().setText(String.valueOf(r.getId()));
                if (r.getDataPrevistaEntrada() != null) {
                    view.getjFormattedTextFieldDataEntrada().setText(
                            Utilities.formatarData(r.getDataPrevistaEntrada()));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                        "Erro ao carregar reserva: " + ex.getMessage());
            }
        }
    }

    private void handleBuscarQuartoCheckIn() {
        int[] codigoHolder = {0};
        TelaBuscaQuarto telaBusca = new TelaBuscaQuarto(null, true);
        new ControllerBuscaQuarto(telaBusca, val -> codigoHolder[0] = val);
        telaBusca.setVisible(true);

        if (codigoHolder[0] != 0) {
            try {
                Quarto q = quartoService.Carregar(codigoHolder[0]);
                if (q != null) {
                    quartoCheckIn = q;
                    view.getjTextFieldQuarto().setText(
                            q.getIdentificacao() + " - " + q.getDescricao());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                        "Erro ao carregar quarto: " + ex.getMessage());
            }
        }
    }

    private void handleBuscarVeiculo() {
        int[] codigoHolder = {0};
        TelaBuscaVeiculo telaBusca = new TelaBuscaVeiculo(null, true);
        new ControllerBuscaVeiculo(telaBusca, val -> codigoHolder[0] = val);
        telaBusca.setVisible(true);

        if (codigoHolder[0] != 0) {
            try {
                Veiculo v = veiculoService.Carregar(codigoHolder[0]);
                if (v != null) {
                    veiculoCheckIn = v;
                    view.getjTextFieldVeiculo().setText(v.getPlaca() + " - " + v.getCor());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                        "Erro ao carregar veículo: " + ex.getMessage());
            }
        }
    }

    private void handleBuscarVaga() {
        int[] codigoHolder = {0};
        TelaBuscaVaga telaBusca = new TelaBuscaVaga(null, true);
        new ControllerBuscaVagaEstacionamento(telaBusca, val -> codigoHolder[0] = val);
        telaBusca.setVisible(true);

        if (codigoHolder[0] != 0) {
            try {
                VagaEstacionamento vaga = vagaService.Carregar(codigoHolder[0]);
                if (vaga != null) {
                    vagaCheckIn = vaga;
                    view.getjTextFieldVaga().setText(vaga.getDescricao());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                        "Erro ao carregar vaga: " + ex.getMessage());
            }
        }
    }

    private void handleBuscarCheckParaCheckout() {
        int[] codigoHolder = {0};
        TelaBuscaCheck telaBusca = new TelaBuscaCheck(null, true);
        new ControllerBuscaCheck(telaBusca, val -> codigoHolder[0] = val);
        telaBusca.setVisible(true);

        if (codigoHolder[0] != 0) {
            try {
                Check check = checkService.Carregar(codigoHolder[0]);
                if (check == null) {
                    JOptionPane.showMessageDialog(view, "Check-in não encontrado.");
                    return;
                }
                if (check.getStatus() == 'F') {
                    JOptionPane.showMessageDialog(view,
                            "Este check-in já foi finalizado (checkout realizado).");
                    return;
                }
                checkParaCheckout = check;
                preencherCamposCheckout(check);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                        "Erro ao carregar check: " + ex.getMessage());
            }
        }
    }

    private boolean validarCheckIn() {
        if (hospedeCheckIn == null) {
            int confirm = JOptionPane.showConfirmDialog(view,
                    "Nenhum hóspede selecionado. Deseja buscar agora?",
                    "Hóspede obrigatório", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                handleBuscarHospede();
            }
            if (hospedeCheckIn == null) return false;
        }

        if (quartoCheckIn == null) {
            JOptionPane.showMessageDialog(view, "Selecione um Quarto para o check-in.");
            return false;
        }

        String dataEntrada = view.getjFormattedTextFieldDataEntrada().getText();
        if (dataEntrada == null || Utilities.apenasNumeros(dataEntrada).length() != 8) {
            JOptionPane.showMessageDialog(view, "Informe a Data de Entrada (dd/mm/aaaa).");
            view.getjFormattedTextFieldDataEntrada().requestFocus();
            return false;
        }

        if (veiculoCheckIn != null && vagaCheckIn == null) {
            JOptionPane.showMessageDialog(view,
                    "Veículo informado sem vaga. Selecione uma Vaga ou remova o veículo.");
            return false;
        }

        return true;
    }

    private void preencherCamposCheckout(Check check) {
        String quartoInfo = "Check #" + check.getId();
        if (check.getCheckQuarto() != null && check.getCheckQuarto().getQuarto() != null) {
            quartoInfo += " — Quarto "
                    + check.getCheckQuarto().getQuarto().getIdentificacao();
        }
        view.getjTextFieldQuarto1().setText(quartoInfo);

        view.getjFormattedTextFieldDataSaida().setEditable(true);
        view.getjFormattedTextFieldDataSaida().setText(Utilities.getDataHoje());

        long dias = 1;
        if (check.getDataHoraEntrada() != null) {
            dias = java.time.Duration.between(
                    check.getDataHoraEntrada(), LocalDateTime.now()).toDays();
            if (dias < 1) dias = 1;
        }
        view.getjTextFieldValorPagar().setText(dias + " diária(s) — informe o valor");

        view.getjTextFieldValorPago().setText("0,00");
        view.getjComboBoxStatusRecebimento().setEnabled(true);
        view.getjComboBoxStatusRecebimento().setSelectedItem("Ativo");
    }

    private void limparCamposCheckIn() {
        hospedeCheckIn = null;
        quartoCheckIn  = null;
        veiculoCheckIn = null;
        vagaCheckIn    = null;
        reservaCheckIn = null;

        view.getjTextFieldId().setText("");
        view.getjTextFieldReservaId().setText("");
        view.getjTextFieldHospede().setText("");
        view.getjFormattedTextFieldDataEntrada().setText("");
        view.getjTextFieldQuarto().setText("");
        view.getjTextFieldVeiculo().setText("");
        view.getjTextFieldVaga().setText("");
        view.getjTextFieldObservacao().setText("");
        view.getjComboBoxStatus().setSelectedIndex(0);
    }

    private void limparCamposCheckout() {
        checkParaCheckout = null;
        view.getjTextFieldQuarto1().setText("");
        view.getjFormattedTextFieldDataSaida().setText("");
        view.getjFormattedTextFieldDataSaida().setEditable(false);
        view.getjTextFieldValorPagar().setText("R$");
        view.getjTextFieldValorPago().setText("R$");
        view.getjTextFieldObservacao1().setText("");
        view.getjComboBoxStatusRecebimento().setEnabled(false);
        view.getjComboBoxStatusRecebimento().setSelectedIndex(0);
    }

    private void definirEstadoBotoes(boolean editando) {
        Utilities.ativaDesativa(view.getjPanelBotoes(), !editando);
    }

    private static LocalDateTime parseData(String texto) {
        if (texto == null) return null;
        String nums = Utilities.apenasNumeros(texto);
        if (nums.length() != 8) return null;
        try {
            return java.time.LocalDate
                    .parse(nums, DateTimeFormatter.ofPattern("ddMMuuuu"))
                    .atStartOfDay();
        } catch (Exception e) {
            return null;
        }
    }
}