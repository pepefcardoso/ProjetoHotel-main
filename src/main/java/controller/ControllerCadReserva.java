package controller;

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Reserva;
import service.ReservaService;
import utilities.Utilities;
import view.TelaBuscaReserva;
import view.TelaCadastroReserva;

public final class ControllerCadReserva extends AbstractControllerCad<Reserva, TelaCadastroReserva> {
    public ControllerCadReserva(TelaCadastroReserva view) {
        super(view, new ReservaService());
    }

    @Override
    protected void inicializarView() {
        super.inicializarView();
        view.getjFormattedTextFieldPrevisaoEntrada().setEditable(false);
        view.getjFormattedTextFieldDataPrevisaoSaida().setEditable(false);
    }

    @Override
    public void handleNovo() {
        super.handleNovo();
        view.getjFormattedTextFieldPrevisaoEntrada().setEditable(true);
        view.getjFormattedTextFieldDataPrevisaoSaida().setEditable(true);
    }

    @Override
    public void handleCancelar() {
        super.handleCancelar();
        view.getjFormattedTextFieldPrevisaoEntrada().setEditable(false);
        view.getjFormattedTextFieldDataPrevisaoSaida().setEditable(false);
    }

    @Override
    protected void configurarListenersAdicionais() {
        //
    }

    @Override
    protected void handleAcoesAdicionais(ActionEvent evento) {
        //
    }

    @Override
    public boolean isFormularioValido() {
        String entrada = view.getjFormattedTextFieldPrevisaoEntrada().getText();
        String saida   = view.getjFormattedTextFieldDataPrevisaoSaida().getText();

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
        LocalDateTime dtSaida   = parseData(saida);

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
        view.getjComboBoxStatus().setSelectedItem(reserva.getStatus() == 'A' ? "Ativo" : "Inativo");
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

    @Override protected JTextField     getTextFieldId()   { return view.getjTextFieldId(); }
    @Override protected JComboBox<String> getComboBoxStatus() { return view.getjComboBoxStatus(); }
    @Override protected JPanel         getPanelBotoes()   { return view.getjPanelBotoes(); }
    @Override protected JPanel         getPanelDados()    { return view.getjPanelDados(); }
    @Override protected JButton        getButtonNovo()    { return view.getjButtonNovo(); }
    @Override protected JButton        getButtonCancelar(){ return view.getjButtonCancelar(); }
    @Override protected JButton        getButtonGravar()  { return view.getjButtonGravar(); }
    @Override protected JButton        getButtonBuscar()  { return view.getjButtonBuscar(); }
    @Override protected JButton        getButtonSair()    { return view.getjButtonSair(); }

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