package gameon.models.valuesobjects;

import gameon.models.interfaces.Validate;

public class Cpf implements Validate {
	private final String cpf;
	private static final String CPF_REGEX = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";
	
    public Cpf(String cpf) {
        this.cpf = cpf;
        
        if (!isValid()) {
            throw new IllegalArgumentException("Cpf inválido: formato incorreto.");
        }
    }
    
    public String getCpf() {
        return cpf;
    }

	@Override
	public boolean isValid() {
		if (cpf == null) return false;
		
		return cpf.matches(CPF_REGEX);
	}

}
