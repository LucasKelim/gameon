package gameon;

import gameon.models.Admin;
import gameon.models.BO.AdminBO;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;

public class MainAdminInserir {

	public static void main(String[] args) {
		
		AdminBO adminBO = new AdminBO();
		Admin admin = new Admin();
		admin.setNome("Admin");
		admin.setEmail(new Email("admin@admin.com"));
		admin.setSenha(new Senha("12345678"));
		
		if (adminBO.inserir(admin) != null) {
			System.out.println("Admin inserido");
		} else {
			System.out.println("Erro ao inserir Admin");
		}
	}

}
