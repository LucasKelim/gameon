package gameon.metodosPagamento;

import gameon.models.DTO.OrdemDTO;
import gameon.models.interfaces.MetodoPagamento;

public class Pix implements MetodoPagamento {

	@Override
	public boolean processarPagamento(OrdemDTO ordem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String descricao() {
		return "PIX";
	}

}
