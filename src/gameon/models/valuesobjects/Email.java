package gameon.models.valuesobjects;

import gameon.models.interfaces.Validate;

public class Email implements Validate {
    private final String email;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    
    public Email(String email) {
        this.email = email;
        
        if (!isValid()) {
            throw new IllegalArgumentException("Email inválido: formato incorreto.");
        }
    }
    
    public String getEmail() {
    	return email;
    }

    @Override
    public boolean isValid() {
        if (email == null) return false;
        
        return email.matches(EMAIL_REGEX);
    }
}
