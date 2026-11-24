# Cofrinho de moedas (Java)

Sistema de console em Java que simula um cofrinho de moedas, desenvolvido na disciplina
**Programação Orientada a Objetos** do Bacharelado em Engenharia de Software do
Centro Universitário Internacional UNINTER.

## Funcionalidades

Menu com as opções:

1. Adicionar moeda (Dólar, Euro ou Real)
2. Remover moeda
3. Listar as moedas do cofrinho
4. Calcular o total convertido para Real
0. Sair

## Conceitos de POO usados

- **Classe abstrata** `Moeda`, com os métodos abstratos `info()` e `converter()`
- **Herança:** `Dolar`, `Euro` e `Real` estendem `Moeda`
- **Polimorfismo:** o `Cofrinho` guarda uma `List<Moeda>` e chama `info()` e `converter()` sem saber o tipo exato de cada moeda
- **Encapsulamento:** a lista de moedas é privada e só é alterada pelos métodos do `Cofrinho`
- `equals()` e `hashCode()` sobrescritos para remover moedas por tipo e valor

## Como executar

Com o JDK instalado, na pasta do projeto:

```
javac -d bin src/cofrinho/*.java
java -cp bin cofrinho.Principal
```

Ou importe a pasta no Eclipse (File → Import → Existing Projects into Workspace) e execute a classe `Principal`.
