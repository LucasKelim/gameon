package gameon.models.valuesobjects;

public class Senha {
    private final String valor;

    public Senha(String senha) {
        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres");
        }
        this.valor = senha;
    }

    public String getValor() {
        return valor;
    }
}
