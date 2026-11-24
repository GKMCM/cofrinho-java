package cofrinho;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
 * O cofrinho em si.
 *
 * Ele nao sabe (e nem quer saber) quais tipos de moeda existem: guarda
 * tudo como "Moeda" e conversa com elas so pelos metodos info() e
 * converter(). Por causa disso, se um dia eu quiser criar uma classe Iene
 * ou Libra, basta estender Moeda que esta classe aqui continua funcionando
 * sem eu mexer em nenhuma linha.
 */
public class Cofrinho {

	/*
	 * A colecao pedida no enunciado. Declarei como List (a interface) e
	 * instanciei como ArrayList - fica mais facil trocar a estrutura depois.
	 * Private para ninguem de fora mexer na lista por fora dos metodos
	 * (encapsulamento).
	 */
	private List<Moeda> listaMoedas = new ArrayList<Moeda>();

	// Joga mais uma moeda dentro do cofrinho.
	public void adicionar(Moeda moeda) {
		if (moeda != null) {
			listaMoedas.add(moeda);
		}
	}

	/*
	 * Remove uma moeda "igual" a que foi passada (mesmo tipo e mesmo valor).
	 * Quem faz a comparacao e o equals() que eu sobrescrevi na classe Moeda.
	 * Retorna true se achou e removeu, false se nao tinha no cofrinho.
	 */
	public boolean remover(Moeda moeda) {
		return listaMoedas.remove(moeda);
	}

	/*
	 * Versao por posicao, que e a que uso no menu: o usuario ve a lista
	 * numerada e escolhe qual quer tirar. Devolve a moeda removida, ou null
	 * se o numero digitado nao existir na lista.
	 */
	public Moeda removerPorIndice(int indice) {
		if (indice < 0 || indice >= listaMoedas.size()) {
			return null;
		}
		return listaMoedas.remove(indice);
	}

	/*
	 * Mostra todas as moedas na tela.
	 *
	 * AQUI ESTA O POLIMORFISMO: a variavel do for e do tipo Moeda, mas a
	 * chamada m.info() executa o info() do Dolar, do Euro ou do Real,
	 * conforme o objeto que estiver naquela posicao. Sem nenhum if/switch
	 * para descobrir o tipo.
	 */
	public void listagemMoedas() {
		if (listaMoedas.isEmpty()) {
			System.out.println("O cofrinho esta vazio, nao ha moedas para listar.");
			return;
		}

		System.out.println("--- Moedas no cofrinho (" + listaMoedas.size() + ") ---");
		int posicao = 1;
		for (Moeda m : listaMoedas) {
			System.out.println(posicao + " - " + m.info());
			posicao++;
		}
		System.out.println("-------------------------------------");
	}

	/*
	 * Soma tudo que tem no cofrinho, ja convertido para Real.
	 *
	 * Mesma ideia do metodo acima: eu so chamo converter() e cada moeda sabe
	 * se virar usando a propria cotacao. Se amanha eu criar mais um tipo de
	 * moeda, este total continua certo automaticamente.
	 */
	public double totalConvertido() {
		double total = 0;
		for (Moeda m : listaMoedas) {
			total += m.converter();
		}
		return total;
	}

	// Metodos extras que criei so para deixar o menu mais amigavel.
	public boolean estaVazio() {
		return listaMoedas.isEmpty();
	}

	public int quantidade() {
		return listaMoedas.size();
	}

	@Override
	public String toString() {
		return String.format(Locale.forLanguageTag("pt-BR"),
				"Cofrinho com %d moeda(s), total de R$ %.2f", quantidade(), totalConvertido());
	}
}
