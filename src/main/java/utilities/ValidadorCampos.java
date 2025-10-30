package utilities;

public class ValidadorCampos {

    public static boolean validarCampoTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean validarCampoNumero(String numero) {
        return numero != null && numero.matches("\\d+");
    }

    public static boolean validarCampoEmail(String email) {
        return email != null && email.matches("^[\\w-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,4}$");
    }

    public static boolean validarCampoUsuario(String usuario) {
        return usuario != null && usuario.matches("^[a-zA-Z0-9._]{5,20}$");
    }

    public static boolean validarSenha(char[] senha) {
        return senha != null && senha.length >= 6;
    }

    public static boolean validarStatus(Object status) {
        return status != null && (status.toString().equals("Ativo") || status.toString().equals("Inativo"));
    }

    public static boolean validarSexo(Object sexo) {
        return sexo != null && (sexo.toString().equals("Masculino") || sexo.toString().equals("Feminino"));
    }

    public static boolean validarFone(String fone) {
        String apenasNumeros = Utilities.apenasNumeros(fone);
        return apenasNumeros.length() == 11;
    }

    public static boolean validarCep(String cep) {
        String apenasNumeros = Utilities.apenasNumeros(cep);
        return apenasNumeros.length() == 8;
    }

    public static boolean validarPlaca(String placa) {
        return validarCampoTexto(placa) && placa.length() == 7;
    }
    
    public static boolean validarCpf(String cpf) {
        cpf = Utilities.apenasNumeros(cpf);

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0;

            for (int i = 0; i < 9; i++) {
                soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * (10 - i);
            }

            int resto = soma % 11;
            int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

            if (digitoVerificador1 != Integer.parseInt(String.valueOf(cpf.charAt(9)))) {
                return false;
            }

            soma = 0;

            for (int i = 0; i < 10; i++) {
                soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * (11 - i);
            }

            resto = soma % 11;

            int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

            if (digitoVerificador2 != Integer.parseInt(String.valueOf(cpf.charAt(10)))) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean validarCnpj(String cnpj) {
        cnpj = Utilities.apenasNumeros(cnpj);

        if (cnpj.length() != 14) {
            return false;
        }

        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {
            int soma = 0;
            int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            for (int i = 0; i < 12; i++) {
                soma += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * pesos1[i];
            }

            int resto = soma % 11;
            int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

            if (digitoVerificador1 != Integer.parseInt(String.valueOf(cnpj.charAt(12)))) {
                return false;
            }

            soma = 0;
            int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            for (int i = 0; i < 13; i++) {
                soma += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * pesos2[i];
            }

            resto = soma % 11;
            int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

            if (digitoVerificador2 != Integer.parseInt(String.valueOf(cnpj.charAt(13)))) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
