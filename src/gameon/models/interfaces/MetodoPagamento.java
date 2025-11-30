package gameon.models.interfaces;

import gameon.models.DTO.OrdemDTO;

public interface MetodoPagamento {
	public boolean processarPagamento(OrdemDTO ordem);
	public String descricao();
}
