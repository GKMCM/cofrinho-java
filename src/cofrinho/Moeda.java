package cofrinho;

import java.util.Locale;

/*
 * Classe MAE de todas as moedas do cofrinho.
 *
 * Ela e abstrata porque "moeda" sozinha nao existe de verdade: o que existe
 * mesmo e um Dolar, um Euro ou um Real. Entao aqui eu guardo so o que todas
 * elas tem em comum (o valor) e obrigo cada filha a dizer:
 *   - como ela se apresenta na tela  -> info()
 *   - quanto ela vale em Real        -> converter()
 *
 * Esses dois metodos sao abstratos de proposito: e isso que permite o
 * POLIMORFISMO la no Cofrinho, onde eu percorro uma lista de "Moeda" sem
 * saber (nem precisar saber) qual e o tipo exato de cada uma.
 */
public abstract class Moeda {

	/*
	 * Locale do Brasil para formatar os numeros com virgula (R$ 12,50).
	 * Deixei protected e static para as classes filhas reaproveitarem sem
	 * precisar criar um Locale novo em cada uma.
	 */
	protected static final Locale LOCAL_BR = Locale.forLanguageTag("pt-BR");

	/*
	 * Valor da moeda na moeda dela mesma: 10 dolares, 5 euros, 2 reais...
	 * Usei protected para as filhas conseguirem ler direto no converter().
	 */
	protected double valor;

	public Moeda(double valor) {
		this.valor = valor;
	}

	public double getValor() {
		return valor;
	}

	/*
	 * Metodos abstratos = nao tem corpo aqui. Quem herda e OBRIGADO a
	 * implementar, senao nem compila. Na hora da execucao o Java escolhe
	 * sozinho a versao certa (Dolar, Euro ou Real).
	 */
	public abstract String info();

	public abstract double converter();

	/*
	 * Aproveitei o info() no toString() so para conseguir dar um
	 * System.out.println(moeda) direto quando eu quiser depurar.
	 */
	@Override
	public String toString() {
		return info();
	}

	/*
	 * equals() e hashCode() comparando o TIPO da classe + o valor.
	 * Precisei disso porque o metodo remover(Moeda) do Cofrinho usa o
	 * ArrayList.remove(), e o ArrayList procura o objeto chamando equals().
	 * Sem isso ele so removeria se fosse exatamente o mesmo objeto na memoria.
	 * O getClass() != obj.getClass() garante que um Dolar de 5 nunca seja
	 * considerado igual a um Euro de 5.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Moeda outra = (Moeda) obj;
		return Double.compare(this.valor, outra.valor) == 0;
	}

	@Override
	public int hashCode() {
		final int primo = 31;
		long bits = Double.doubleToLongBits(valor);
		int resultado = getClass().getName().hashCode();
		resultado = primo * resultado + (int) (bits ^ (bits >>> 32));
		return resultado;
	}
}
