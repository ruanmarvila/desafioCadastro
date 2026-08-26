package pets;

import java.util.Map;

import pets.excecoes.SexoPetInvalidoException;

public enum SexoPet {
    MACHO, FEMEA;

    private static final Map<SexoPet, String> SEXO_FORMATADO = Map.of(
        MACHO, "Macho",
        FEMEA, "Fêmea"
    );

    public static SexoPet fromOpcao(String opcao) {
        return switch(opcao) {
            case "1" -> MACHO;
            case "2" -> FEMEA;
            default -> throw new SexoPetInvalidoException("Opção inválida: " + opcao);
        };
    }

    public String getFormatado() {
        return SEXO_FORMATADO.getOrDefault(this, this.name());
    }
}
