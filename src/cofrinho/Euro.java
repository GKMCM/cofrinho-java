package cofrinho;

/*
 * Moeda da Zona do Euro. Mesma ideia do Dolar, so muda a cotacao e o texto.
 * Repare que nao precisei reescrever nada de valor/equals/toString:
 * tudo isso veio de graca por HERANCA da classe Moeda.
 */
public class Euro extends Moeda {

	// Cotacao fixa e ILUSTRATIVA (nao e a cotacao real do dia).
	public static final double COTACAO_REAL = 5.50;

	public Euro(double valor) {
		super(valor);
	}

	@Override
	public double converter() {
		return valor * COTACAO_REAL;
	}

	@Override
	public String info() {
		return String.format(LOCAL_BR,
				"Euro   (Zona do Euro)   - EUR %.2f  ->  R$ %.2f   [cotacao usada: %.2f]",
				valor, converter(), COTACAO_REAL);
	}
}
