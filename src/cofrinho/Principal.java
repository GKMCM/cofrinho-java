package cofrinho;

import java.util.Locale;
import java.util.Scanner;

/*
 * ============================================================
 *  Atividade Pratica - Programacao Orientada a Objetos (922326)
 *  Aluno: Gabriell Marques
 *  RU: 3923658
 *  Sistema: "Cofrinho de moedas"
 * ============================================================
 *
 * Classe que tem o main e cuida so da conversa com o usuario (o menu).
 * Toda a regra de negocio eu deixei no Cofrinho e nas classes de Moeda,
 * para nao misturar as coisas.
 *
 * Organizacao do projeto:
 *   Moeda     -> classe abstrata (mae) com info() e converter()
 *   Dolar     -> filha de Moeda, cotacao 5,00
 *   Euro      -> filha de Moeda, cotacao 5,50
 *   Real      -> filha de Moeda, cotacao 1,00
 *   Cofrinho  -> guarda um ArrayList<Moeda> e opera em cima dele
 *   Principal -> menu / entrada de dados
 */
public class Principal {

	// Locale do Brasil para imprimir os valores com virgula (R$ 77,50).
	private static final Locale LOCAL_BR = Locale.forLanguageTag("pt-BR");

	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);
		Cofrinho cofrinho = new Cofrinho();

		System.out.println("========================================");
		System.out.println("     COFRINHO DE MOEDAS - POO/UNINTER   ");
		System.out.println("========================================");

		boolean continuar = true;

		while (continuar) {

			mostrarMenu();

			/*
			 * Leio a opcao com um metodo proprio que ja trata texto invalido.
			 * Se vier null e porque a entrada acabou (por exemplo quando o
			 * programa e executado com um arquivo redirecionado), entao eu
			 * encerro em vez de ficar num laco infinito.
			 */
			Integer opcao = lerInteiro(teclado, "Escolha uma opcao: ");
			if (opcao == null) {
				System.out.println();
				System.out.println("Entrada encerrada. Fechando o programa.");
				break;
			}

			switch (opcao.intValue()) {

			case 1:
				adicionarMoeda(teclado, cofrinho);
				break;

			case 2:
				removerMoeda(teclado, cofrinho);
				break;

			case 3:
				cofrinho.listagemMoedas();
				break;

			case 4:
				// Chama o metodo do cofrinho, que por sua vez usa o
				// converter() de cada moeda (polimorfismo).
				System.out.println(String.format(LOCAL_BR,
						"Total no cofrinho convertido para Real: R$ %.2f",
						cofrinho.totalConvertido()));
				break;

			case 0:
				continuar = false;
				System.out.println("Cofrinho fechado. Ate logo!");
				break;

			default:
				// Numero valido, mas que nao existe no menu (ex.: 9).
				System.out.println("Opcao invalida! Escolha um numero entre 0 e 4.");
			}
		}

		teclado.close();
	}

	// Desenha o menu na tela. Separei num metodo para nao repetir o texto.
	private static void mostrarMenu() {
		System.out.println();
		System.out.println("----------------- MENU -----------------");
		System.out.println("1 - Adicionar moeda");
		System.out.println("2 - Remover moeda");
		System.out.println("3 - Listar moedas do cofrinho");
		System.out.println("4 - Total convertido para Real");
		System.out.println("0 - Sair");
		System.out.println("----------------------------------------");
	}

	/*
	 * Pergunta o tipo e o valor e cria a moeda.
	 *
	 * Detalhe que eu achei o mais importante do trabalho: a variavel "nova"
	 * e declarada como Moeda (o tipo mae), mas recebe um Dolar, um Euro ou um
	 * Real. Isso so e possivel por causa da HERANCA, e e o que deixa o
	 * cofrinho guardar tudo junto na mesma lista.
	 */
	private static void adicionarMoeda(Scanner teclado, Cofrinho cofrinho) {

		System.out.println();
		System.out.println("Qual o tipo da moeda?");
		System.out.println("  1 - Dolar (EUA)");
		System.out.println("  2 - Euro (Zona do Euro)");
		System.out.println("  3 - Real (Brasil)");

		Integer tipo = null;
		// Fica pedindo ate o usuario digitar 1, 2 ou 3.
		while (tipo == null || tipo.intValue() < 1 || tipo.intValue() > 3) {
			tipo = lerInteiro(teclado, "Tipo: ");
			if (tipo == null) {
				return; // acabou a entrada, volta para o menu
			}
			if (tipo.intValue() < 1 || tipo.intValue() > 3) {
				System.out.println("Tipo invalido! Digite 1, 2 ou 3.");
			}
		}

		Double valor = lerValorPositivo(teclado, "Valor da moeda: ");
		if (valor == null) {
			return;
		}

		Moeda nova;
		if (tipo.intValue() == 1) {
			nova = new Dolar(valor.doubleValue());
		} else if (tipo.intValue() == 2) {
			nova = new Euro(valor.doubleValue());
		} else {
			nova = new Real(valor.doubleValue());
		}

		cofrinho.adicionar(nova);
		System.out.println("Moeda adicionada: " + nova.info());
	}

	/*
	 * Remove uma moeda. Preferi mostrar a lista numerada e pedir a posicao,
	 * porque assim o usuario nao precisa acertar o valor exato digitado antes
	 * (e tambem resolve o caso de ter duas moedas iguais no cofrinho).
	 */
	private static void removerMoeda(Scanner teclado, Cofrinho cofrinho) {

		// Primeira validacao: nao da para remover de um cofrinho vazio.
		if (cofrinho.estaVazio()) {
			System.out.println("O cofrinho esta vazio, nao ha o que remover.");
			return;
		}

		cofrinho.listagemMoedas();

		Integer posicao = lerInteiro(teclado,
				"Numero da moeda que deseja remover (0 para cancelar): ");
		if (posicao == null) {
			return;
		}
		if (posicao.intValue() == 0) {
			System.out.println("Remocao cancelada.");
			return;
		}

		// A lista comeca em 0 para o Java, mas mostrei a partir de 1 para o
		// usuario, entao aqui eu desconto 1.
		Moeda removida = cofrinho.removerPorIndice(posicao.intValue() - 1);

		if (removida == null) {
			System.out.println("Nao existe moeda nesse numero. Nada foi removido.");
		} else {
			System.out.println("Moeda removida: " + removida.info());
		}
	}

	/*
	 * ----------- metodos auxiliares de leitura -----------
	 *
	 * Optei por ler sempre a LINHA inteira e converter na mao, em vez de usar
	 * nextInt()/nextDouble() direto. Com o nextInt(), se o usuario digita uma
	 * letra o Scanner lanca excecao e trava o programa num laco. Lendo a linha
	 * e usando try/catch eu consigo avisar o erro e perguntar de novo.
	 */

	// Devolve a linha digitada, ou null se nao houver mais entrada.
	private static String lerLinha(Scanner teclado) {
		if (!teclado.hasNextLine()) {
			return null;
		}
		return teclado.nextLine().trim();
	}

	// Le um numero inteiro, repetindo a pergunta enquanto vier algo invalido.
	private static Integer lerInteiro(Scanner teclado, String pergunta) {
		while (true) {
			System.out.print(pergunta);
			String linha = lerLinha(teclado);

			if (linha == null) {
				return null;
			}
			if (linha.isEmpty()) {
				System.out.println("Voce nao digitou nada. Tente novamente.");
				continue;
			}

			try {
				return Integer.valueOf(Integer.parseInt(linha));
			} catch (NumberFormatException e) {
				// Cai aqui quando o usuario digita letra, simbolo, etc.
				System.out.println("Isso nao e um numero inteiro valido: " + linha);
			}
		}
	}

	// Le um valor com casas decimais e exige que seja maior que zero.
	private static Double lerValorPositivo(Scanner teclado, String pergunta) {
		while (true) {
			System.out.print(pergunta);
			String linha = lerLinha(teclado);

			if (linha == null) {
				return null;
			}
			if (linha.isEmpty()) {
				System.out.println("Voce nao digitou nada. Tente novamente.");
				continue;
			}

			// Aceito tanto 2,50 quanto 2.50 - troco a virgula por ponto
			// porque o Double.parseDouble so entende ponto.
			String normalizada = linha.replace(',', '.');

			try {
				double valor = Double.parseDouble(normalizada);
				if (valor <= 0) {
					System.out.println("O valor da moeda precisa ser maior que zero.");
					continue;
				}
				return Double.valueOf(valor);
			} catch (NumberFormatException e) {
				System.out.println("Isso nao e um valor valido (exemplo: 2,50): " + linha);
			}
		}
	}
}
