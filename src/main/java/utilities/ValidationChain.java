package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.JComponent;
import javax.swing.JTextField;

public class ValidationChain {

    private final List<Validator> validators = new ArrayList<>();
    private String errorMessage;

    public ValidationChain add(Validator validator) {
        validators.add(validator);
        return this;
    }

    public boolean validate() {
        for (Validator validator : validators) {
            if (!validator.validate()) {
                this.errorMessage = validator.getErrorMessage();
                validator.focusComponent();
                return false;
            }
        }
        return true;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static ValidationChain criar() {
        return new ValidationChain();
    }

    public interface Validator {

        boolean validate();

        String getErrorMessage();

        void focusComponent();
    }

    public static class ValidatorBuilder {

        public static CampoTextoValidator campoTexto(JTextField campo, String nomeCampo) {
            return new CampoTextoValidator(campo, nomeCampo);
        }

        public static CampoNumericoValidator campoNumerico(JTextField campo, String nomeCampo) {
            return new CampoNumericoValidator(campo, nomeCampo);
        }

        public static CustomValidator custom(Predicate<Void> validacao, String mensagemErro, JComponent componente) {
            return new CustomValidator(validacao, mensagemErro, componente);
        }
    }

    public static class CampoTextoValidator implements Validator {

        private final JTextField campo;
        private final String nomeCampo;

        public CampoTextoValidator(JTextField campo, String nomeCampo) {
            this.campo = campo;
            this.nomeCampo = nomeCampo;
        }

        @Override
        public boolean validate() {
            return utilities.ValidadorCampos.validarCampoTexto(campo.getText());
        }

        @Override
        public String getErrorMessage() {
            return "O campo " + nomeCampo + " é obrigatório.";
        }

        @Override
        public void focusComponent() {
            campo.requestFocus();
        }
    }

    public static class CampoNumericoValidator implements Validator {

        private final JTextField campo;
        private final String nomeCampo;

        public CampoNumericoValidator(JTextField campo, String nomeCampo) {
            this.campo = campo;
            this.nomeCampo = nomeCampo;
        }

        @Override
        public boolean validate() {
            return utilities.ValidadorCampos.validarCampoNumero(campo.getText());
        }

        @Override
        public String getErrorMessage() {
            return "O campo " + nomeCampo + " é inválido.";
        }

        @Override
        public void focusComponent() {
            campo.requestFocus();
        }
    }

    public static class CustomValidator implements Validator {

        private final Predicate<Void> validacao;
        private final String mensagemErro;
        private final JComponent componente;

        public CustomValidator(Predicate<Void> validacao, String mensagemErro, JComponent componente) {
            this.validacao = validacao;
            this.mensagemErro = mensagemErro;
            this.componente = componente;
        }

        @Override
        public boolean validate() {
            return validacao.test(null);
        }

        @Override
        public String getErrorMessage() {
            return mensagemErro;
        }

        @Override
        public void focusComponent() {
            if (componente != null) {
                componente.requestFocus();
            }
        }
    }
}
