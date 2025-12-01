package gameon.factories;

import gameon.metodosPagamento.CartaoCredito;
import gameon.metodosPagamento.Pix;
import gameon.models.interfaces.MetodoPagamento;

public class MetodoPagamentoFactory {
	public static MetodoPagamento criar(String metodo) {
		switch (metodo) {
			case "PIX":
				return new Pix();
			
			case "CARTAO_CREDITO":
				return new CartaoCredito();
	
			default:
				return null;
		}
	}
}
