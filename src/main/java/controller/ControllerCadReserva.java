package controller;

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Quarto;
import model.Reserva;
import model.ReservaQuarto;
import service.QuartoService;
import service.ReservaQuartoService;
import service.ReservaService;
import utilities.Utilities;
import view.TelaBuscaQuarto;
import view.TelaBuscaReserva;
import view.TelaCadastroReserva;

public final class ControllerCadReserva extends AbstractControllerCad<Reserva, TelaCadastroReserva> {

    private Quarto quartoSelecionado = null;

    private final ReservaQuartoService reservaQuartoService = new ReservaQuartoService();

    public ControllerCadReserva(TelaCadastroReserva view) {
        super(view, new ReservaService());
    }

    @Override
    protected void inicializarView() {
        super.inicializarView();
        Utilities.setAlwaysDisabled(view.getjTextFieldQuarto(), true);
        view.getjFormattedTextFieldPrevisaoEntrada().setEditable(false);
        view.getjFormattedTextFieldDataPrevisaoSaida().setEditable(false);
    }

    @Override
    public void handleNovo() {
        super.handleNovo();
        quartoSelecionado = null;
        view.getjFormattedTextFieldPrevisaoEntrada().setEditable(true);
        view.getjFormattedTextFieldDataPrevisaoSaida().setEditable(true);
    }

    @Override
    public void handleCancelar() {
        super.handleCancelar();
        quartoSelecionado = null;
        view.getjFormattedTextFieldPrevisaoEntrada().setEditable(false);
        view.getjFormattedTextFieldDataPrevisaoSaida().setEditable(false);
    }

    @Override
    public void handleGravar() {
        if (!isFormularioValido()) {
            return;
        }

        boolean isNovoCadastro = view.getjTextFieldId().getText().trim().isEmpty();

        try {
            Reserva reserva;

            if (isNovoCadastro) {
                reserva = construirDoFormulario();
                service.Criar(reserva);
                salvarReservaQuarto(reserva);
                showMessage("Reserva criada com sucesso!");
            } else {
                int id = Integer.parseInt(view.getjTextFieldId().getText());
                reserva = service.Carregar(id);

                reserva.setDataPrevistaEntrada(parseData(view.getjFormattedTextFieldPrevisaoEntrada().getText()));
                reserva.setDataPrevistaSaida(parseData(view.getjFormattedTextFieldDataPrevisaoSaida().getText()));
                reserva.setObs(view.getjTextFieldObservacao().getText());
                reserva.setStatus(getStatusDoFormulario());

                service.Atualizar(reserva);
                atualizarReservaQuarto(reserva);
                showMessage("Reserva atualizada com sucesso!");
            }

            utilities.Utilities.ativaDesativa(view.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(view.getjPanelDados(), false);
            limparRelacionamentos();

        } catch (Exception ex) {
            showError("Erro ao salvar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void salvarReservaQuarto(Reserva reserva) throws Exception {
        if (quartoSelecionado == null) {
            return;
        }

        ReservaQuarto rq = new ReservaQuarto();
        rq.setReserva(reserva);
        rq.setQuarto(quartoSelecionado);
        rq.setDataHoraInicio(reserva.getDataPrevistaEntrada());
        rq.setDataHoraFim(reserva.getDataPrevistaSaida());
        rq.setObs("");
        rq.setStatus('A');
        reservaQuartoService.Criar(rq);
    }

    private void atualizarReservaQuarto(Reserva reserva) throws Exception {
        if (quartoSelecionado == null) {
            return;
        }

        boolean encontrouQuarto = false;
        List<ReservaQuarto> existentes = reservaQuartoService.findByReservaId(reserva.getId());

        if (existentes != null) {
            for (ReservaQuarto rq : existentes) {
                if (rq.getQuarto() != null && rq.getQuarto().getId() == quartoSelecionado.getId()) {
                    rq.setDataHoraInicio(reserva.getDataPrevistaEntrada());
                    rq.setDataHoraFim(reserva.getDataPrevistaSaida());
                    rq.setStatus('A');
                    reservaQuartoService.Atualizar(rq);
                    encontrouQuarto = true;
                } else if (rq.getStatus() == 'A') {
                    rq.setStatus('I');
                    reservaQuartoService.Atualizar(rq);
                }
            }
        }

        if (!encontrouQuarto) {
            salvarReservaQuarto(reserva);
        }
    }

    @Override
    protected void limparRelacionamentos() {
        quartoSelecionado = null;
        view.getjTextFieldQuarto().setText("");
    }

    @Override
    protected void configurarListenersAdicionais() {
        view.getjButtonBuscarQuarto().addActionListener(this);
    }

    @Override
    protected void handleAcoesAdicionais(ActionEvent evento) {
        if (evento.getSource() == view.getjButtonBuscarQuarto()) {
            handleBuscarQuarto();
        }
    }

    private void handleBuscarQuarto() {
        int[] codigoHolder = {0};
        TelaBuscaQuarto tela = new TelaBuscaQuarto(null, true);
        new ControllerBuscaQuarto(tela, val -> codigoHolder[0] = val);
        tela.setVisible(true);

        if (codigoHolder[0] != 0) {
            try {
                Quarto q = new QuartoService().Carregar(codigoHolder[0]);
                if (q != null) {
                    quartoSelecionado = q;
                    view.getjTextFieldQuarto().setText(
                            q.getIdentificacao() + " – " + q.getDescricao());
                }
            } catch (Exception ex) {
                showError("Erro ao carregar quarto: " + ex.getMessage());
            }
        }
    }

    @Override
    public boolean isFormularioValido() {
        if (quartoSelecionado == null) {
            showMessage("Selecione um quarto para a reserva.");
            view.getjButtonBuscarQuarto().requestFocus();
            return false;
        }

        String entrada = view.getjFormattedTextFieldPrevisaoEntrada().getText();
        String saida = view.getjFormattedTextFieldDataPrevisaoSaida().getText();

        if (Utilities.apenasNumeros(entrada).length() != 8) {
            showMessage("Informe a Data Prevista de Entrada (dd/mm/aaaa).");
            view.getjFormattedTextFieldPrevisaoEntrada().requestFocus();
            return false;
        }
        if (Utilities.apenasNumeros(saida).length() != 8) {
            showMessage("Informe a Data Prevista de Saída (dd/mm/aaaa).");
            view.getjFormattedTextFieldDataPrevisaoSaida().requestFocus();
            return false;
        }

        LocalDateTime dtEntrada = parseData(entrada);
        LocalDateTime dtSaida = parseData(saida);

        if (dtEntrada == null) {
            showMessage("Data Prevista de Entrada inválida.");
            return false;
        }
        if (dtSaida == null || !dtSaida.isAfter(dtEntrada)) {
            showMessage("A Data de Saída deve ser posterior à Data de Entrada.");
            return false;
        }
        return true;
    }

    @Override
    public Reserva construirDoFormulario() {
        Reserva reserva = new Reserva();
        reserva.setDataHoraReserva(LocalDateTime.now());
        reserva.setDataPrevistaEntrada(parseData(view.getjFormattedTextFieldPrevisaoEntrada().getText()));
        reserva.setDataPrevistaSaida(parseData(view.getjFormattedTextFieldDataPrevisaoSaida().getText()));
        reserva.setObs(view.getjTextFieldObservacao().getText());
        reserva.setStatus(getStatusDoFormulario());
        return reserva;
    }

    @Override
    protected void preencherFormulario(Reserva reserva) {
        view.getjFormattedTextFieldPrevisaoEntrada().setEditable(true);
        view.getjFormattedTextFieldDataPrevisaoSaida().setEditable(true);
        view.getjFormattedTextFieldPrevisaoEntrada().setText(
                Utilities.formatarData(reserva.getDataPrevistaEntrada()));
        view.getjFormattedTextFieldDataPrevisaoSaida().setText(
                Utilities.formatarData(reserva.getDataPrevistaSaida()));
        view.getjTextFieldObservacao().setText(reserva.getObs());
        view.getjComboBoxStatus().setSelectedItem(
                reserva.getStatus() == 'A' ? "Ativo" : "Inativo");

        carregarQuartoDaReserva(reserva.getId());
    }

    private void carregarQuartoDaReserva(int reservaId) {
        try {
            List<ReservaQuarto> lista = reservaQuartoService.findByReservaId(reservaId);

            java.util.Optional<ReservaQuarto> quartoOpt = lista.stream()
                    .filter(rq -> rq.getStatus() == 'A' && rq.getQuarto() != null)
                    .findFirst();

            if (quartoOpt.isPresent()) {
                ReservaQuarto rq = quartoOpt.get();
                quartoSelecionado = rq.getQuarto();
                view.getjTextFieldQuarto().setText(
                        rq.getQuarto().getIdentificacao() + " – " + rq.getQuarto().getDescricao()
                );
            } else {
                showError("Inconsistência de dados: A reserva carregada não possui um quarto ativo associado.");
            }
        } catch (Exception ex) {
            showError("Erro ao tentar carregar o quarto da reserva: " + ex.getMessage());
        }
    }

    @Override
    protected void setId(Reserva entidade, int id) {
        entidade.setId(id);
    }

    @Override
    protected JDialog criarTelaBusca() {
        return new TelaBuscaReserva(null, true);
    }

    @Override
    protected void criarControllerBusca(JDialog telaBusca, Consumer<Integer> callback) {
        new ControllerBuscaReserva((TelaBuscaReserva) telaBusca, callback);
    }

    @Override
    protected void focarPrimeiroCampo() {
        view.getjFormattedTextFieldPrevisaoEntrada().requestFocus();
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

    private static LocalDateTime parseData(String texto) {
        if (texto == null) {
            return null;
        }
        String nums = Utilities.apenasNumeros(texto);
        if (nums.length() != 8) {
            return null;
        }
        try {
            return java.time.LocalDate
                    .parse(nums, DateTimeFormatter.ofPattern("ddMMuuuu"))
                    .atStartOfDay();
        } catch (Exception e) {
            return null;
        }
    }
}
