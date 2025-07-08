package utilities;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Utilities {
    
    public static void ativaDesativa(JPanel painel, boolean ativa) {
    Component[] vetComponentes = painel.getComponents();
    for (Component componenteAtual : vetComponentes) {
        if (componenteAtual instanceof JButton jButton) {
            if ("0".equals(jButton.getActionCommand())) {
                componenteAtual.setEnabled(ativa);
            } else {
                componenteAtual.setEnabled(!ativa);
            }
        }
    }
}
    
    public static void limpaComponentes(JPanel painel, boolean ativa) {
        Component[] vetComponentes = painel.getComponents();
        for (Component componenteAtual : vetComponentes) {
            if (componenteAtual instanceof JTextField jTextField) {
                jTextField.setText("");
                componenteAtual.setEnabled(ativa);
            } else if (componenteAtual instanceof JFormattedTextField jFormattedTextField) {
                jFormattedTextField.setText("");
                componenteAtual.setEnabled(ativa);
            } else if (componenteAtual instanceof JComboBox jComboBox) {
                jComboBox.setSelectedIndex(-1);
            } else if (componenteAtual instanceof JCheckBox jCheckBox) {
                jCheckBox.setSelected(false);
                componenteAtual.setEnabled(ativa);
            } else if (componenteAtual instanceof JPasswordField jPasswordField) {
                jPasswordField.setText("");
                componenteAtual.setEnabled(ativa);
            }
        }
    }
}