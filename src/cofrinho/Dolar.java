package cofrinho;

/*
 * Moeda dos Estados Unidos.
 * Herda tudo de Moeda (o atributo valor, o toString, o equals) e so
 * implementa o que e particular dela: a cotacao e o texto da listagem.
 */
public class Dolar extends Moeda {

	/*
	 * ATENCAO: cotacao FIXA e apenas ILUSTRATIVA, escolhida por mim so para o
	 * trabalho funcionar. Nao e a cotacao real do dia. Deixei como constante
	 * (static final) porque o valor e o mesmo para todos os dolares e assim,
	 * se precisar mudar, mudo num lugar so.
	 */
	public static final double COTACAO_REAL = 5.00;

	public Dolar(double valor) {
		super(valor); // reaproveita o construtor da classe mae
	}

	/*
	 * Sobrescrita (@Override) do metodo abstrato converter().
	 * Aqui e onde o polimorfismo aparece na pratica: o Cofrinho chama
	 * converter() sem saber que e um Dolar, e cai nesta linha.
	 */
	@Override
	public double converter() {
		return valor * COTACAO_REAL;
	}

	@Override
	public String info() {
		return String.format(LOCAL_BR,
				"Dolar  (Estados Unidos) - US$ %.2f  ->  R$ %.2f   [cotacao usada: %.2f]",
				valor, converter(), COTACAO_REAL);
	}
}
