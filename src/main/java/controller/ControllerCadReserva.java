package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.TelaCadastroReserva;
import utilities.Utilities;
import view.TelaBuscaHospede;

public class ControllerCadReserva implements ActionListener {

    TelaCadastroReserva telaCadastroReserva;
    private int idHospedeSelecionado = 0;

    public ControllerCadReserva(TelaCadastroReserva telaCadastroReserva) {
        this.telaCadastroReserva = telaCadastroReserva;
        
        this.telaCadastroReserva.getjButtonNovo().addActionListener(this);
        this.telaCadastroReserva.getjButtonCancelar().addActionListener(this);
        this.telaCadastroReserva.getjButtonGravar().addActionListener(this);
        this.telaCadastroReserva.getjButtonBuscar().addActionListener(this);
        this.telaCadastroReserva.getjButtonSair().addActionListener(this);
        this.telaCadastroReserva.getjButtonBuscarHospede().addActionListener(this);
        
        Utilities.ativaDesativa(this.telaCadastroReserva.getjPanelBotoes(), true);
        Utilities.limpaComponentes(this.telaCadastroReserva.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == this.telaCadastroReserva.getjButtonNovo()) {
            Utilities.ativaDesativa(this.telaCadastroReserva.getjPanelBotoes(), false);
            Utilities.limpaComponentes(this.telaCadastroReserva.getjPanelDados(), true);
            
            this.telaCadastroReserva.getjTextFieldId().setEnabled(false);
            this.telaCadastroReserva.getjTextFieldHospede().setEditable(false);
            this.telaCadastroReserva.getjComboBoxStatus().setSelectedIndex(0);
        }
        
        else if (e.getSource() == this.telaCadastroReserva.getjButtonCancelar()) {
            Utilities.ativaDesativa(this.telaCadastroReserva.getjPanelBotoes(), true);
            Utilities.limpaComponentes(this.telaCadastroReserva.getjPanelDados(), false);
        }
        
        else if (e.getSource() == this.telaCadastroReserva.getjButtonGravar()) {
            try {
                model.Reserva reserva = new model.Reserva();
                
                service.HospedeService hospedeService = new service.HospedeService();
                reserva.setHospede(hospedeService.Carregar(this.idHospedeSelecionado));
                
                reserva.PrevisaoEntrada(this.telaCadastroReserva.getjFormattedTextFieldPrevisaoEntrada().getText());
                reserva.setDataHoraPrevisaoSaida(this.telaCadastroReserva.getjFormattedTextFieldSaida().getText());
                reserva.setObservacao(this.telaCadastroReserva.getjTextFieldObservacao().getText());
                
                String status = this.telaCadastroReserva.getjComboBoxStatus().getSelectedItem().toString();
                reserva.setStatus(status.equals("Ativo") ? 'A' : 'I');
                
                service.ReservaService reservaService = new service.ReservaService();
                reservaService.Criar(reserva);
                
                Utilities.ativaDesativa(this.telaCadastroReserva.getjPanelBotoes(), true);
                Utilities.limpaComponentes(this.telaCadastroReserva.getjPanelDados(), false);
                
                javax.swing.JOptionPane.showMessageDialog(null, "Reserva salva com sucesso!");
                
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
            }
        }
        
        else if (e.getSource() == this.telaCadastroReserva.getjButtonBuscarHospede()) {
            TelaBuscaHospede telaBusca = new TelaBuscaHospede(null, true);
            
            new ControllerBuscaHospede(telaBusca, (Integer idSelecionado) -> {
                if (idSelecionado != null && idSelecionado > 0) {
                    try {
                        service.HospedeService hospedeService = new service.HospedeService();
                        model.Hospede hospede = hospedeService.Carregar(idSelecionado);
                        
                        if (hospede != null) {
                            this.idHospedeSelecionado = idSelecionado;
                            this.telaCadastroReserva.getjTextFieldHospede().setText(hospede.getNome());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            
            telaBusca.setVisible(true);
        }
        
        else if (e.getSource() == this.telaCadastroReserva.getjButtonSair()) {
            this.telaCadastroReserva.dispose();
        }
    }
}