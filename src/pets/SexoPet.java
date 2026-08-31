package pets;

import java.util.Map;

import pets.excecoes.SexoPetInvalidoException;

public enum SexoPet {
    MACHO, FEMEA;

    private static final Map<SexoPet, String> SEXO_FORMATADO = Map.of(
        MACHO, "Macho",
        FEMEA, "Fêmea"
    );

    public String getFormatado() {
        return SEXO_FORMATADO.getOrDefault(this, this.name());
    }

    public static SexoPet fromOpcao(String opcao) {
        return switch(opcao) {
            case "1" -> MACHO;
            case "2" -> FEMEA;
            default -> throw new SexoPetInvalidoException("Opção inválida: " + opcao);
        };
    }

    public static SexoPet fromTexto(String sexo) {
        return switch(sexo.trim().toUpperCase()) {
            case "MACHO" -> MACHO;
            case "FÊMEA", "FEMEA" -> FEMEA;
            default -> throw new SexoPetInvalidoException("Sexo inválido:" + sexo);
        };
    }
}
