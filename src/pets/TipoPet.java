package pets;

import java.util.Map;

import pets.excecoes.TipoPetInvalidoException;

public enum TipoPet {
    CACHORRO, GATO;

    private static final Map<TipoPet, String> TIPO_FORMATADO = Map.of(
        CACHORRO, "Cachorro",
        GATO, "Gato"
    );

    public static TipoPet fromOpcao(String opcao) {
        return switch(opcao) {
            case "1" -> CACHORRO;
            case "2" -> GATO;
            default -> throw new TipoPetInvalidoException("Opção inválida: " + opcao);
        };
    }

    public String getFormatado() {
        return TIPO_FORMATADO.getOrDefault(this, this.name());
    }
}
