package pets;

import pets.excecoes.CriterioInvalidoException;

public enum CriterioBusca {
    NOME, SEXO, IDADE, PESO, RACA, ENDERECO;

    public static CriterioBusca fromOpcao(String opcao) {
        return switch (opcao) {
            case "1" -> NOME;
            case "2" -> SEXO;
            case "3" -> IDADE;
            case "4" -> PESO;
            case "5" -> RACA;
            case "6" -> ENDERECO;
            default -> throw new CriterioInvalidoException("Opção inválida: " + opcao);
        };
    }
}
