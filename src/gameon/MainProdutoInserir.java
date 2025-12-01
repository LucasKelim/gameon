package gameon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gameon.models.Produto;
import gameon.models.BO.ProdutoBO;

public class MainProdutoInserir {
	
	private static List<Produto> produtos = new ArrayList<Produto>(
			Arrays.asList(
					new Produto("Bola 1", "Bola de futebol", 69.69, 10, true),
					new Produto("Bola 2", "Bola de basquete", 69.69, 10, true),
					new Produto("Bola 3", "Bola de futebol", 69.69, 10, true),
					new Produto("Bola 4", "Bola de futebol", 69.69, 10, true),
					new Produto("Bola 5", "Bola de futebol", 69.69, 10, true),
					new Produto("Bola 6", "Bola de futebol", 69.69, 10, true),
					new Produto("Bola 7", "Bola de futebol", 69.69, 10, true),
					new Produto("Bola 8", "Bola de futebol", 69.69, 10, true),
					new Produto("Bola 9", "Bola de futebol", 69.69, 10, true),
					new Produto("Bola 10", "Bola de futebol", 69.69, 10, true),
					new Produto("Bola 11", "Bola de futebol", 69.69, 10, true)
			)
	);

	public static void main(String[] args) {
		
		ProdutoBO produtoBO = new ProdutoBO();

		for (Produto produto : produtos) {
			if (produtoBO.inserir(produto) != null) {
				System.out.println("Produto inserido");
			} else {
				System.out.println("Erro ao inserir Produto");
			}
		}
		
	}

}
