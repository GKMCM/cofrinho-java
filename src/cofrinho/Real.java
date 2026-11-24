package cofrinho;

/*
 * Moeda do Brasil.
 * Esta e o caso mais simples: como o total do cofrinho ja e calculado em
 * Real, a conversao e 1 para 1. Mesmo assim ela PRECISA implementar
 * converter(), porque o contrato foi definido la na classe abstrata Moeda.
 * Achei legal justamente por isso: o Cofrinho trata o Real igualzinho aos
 * outros, sem nenhum "if" para tratar caso especial.
 */
public class Real extends Moeda {

	// Real para Real e 1,00. Deixei a constante so para manter o padrao.
	public static final double COTACAO_REAL = 1.00;

	public Real(double valor) {
		super(valor);
	}

	@Override
	public double converter() {
		return valor * COTACAO_REAL;
	}

	@Override
	public String info() {
		return String.format(LOCAL_BR,
				"Real   (Brasil)         - R$  %.2f  ->  R$ %.2f   [cotacao usada: %.2f]",
				valor, converter(), COTACAO_REAL);
	}
}
