package pets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PetRepository {
    private static final Path PASTA = Path.of("data/petsCadastrados");

    public PetRepository() {
        criarPasta();
    }

    private void criarPasta() {
        try {
            Files.createDirectories(PASTA);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar a pasta", e);
        }
    }

    private Path criarArquivo(String nomePet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm'-'");
        Path pathPet = Path.of(
            PASTA.toString(), LocalDateTime.now().format(formatter) + nomePet + ".txt"
        );
        try {
            Files.createFile(pathPet);
            return pathPet;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar o arquivo", e);
        }
    }

    public void salvarPet(Pet pet) {
        Path arquivo = criarArquivo(pet.getNomeNormalizado());
        Endereco petEndereco = pet.getEndereco();

        try (BufferedWriter writer = Files.newBufferedWriter(arquivo)) {
            writer.write("1 - "+pet.getNome()+"\n"+
                        "2 - "+pet.getTipoPet().getFormatado()+"\n"+
                        "3 - "+pet.getSexoPet().getFormatado()+"\n"+
                        "4 - Rua "+petEndereco.getRua()+", "+petEndereco.getNumeroFormatado()+", "+petEndereco.getCidade()+"\n"+
                        "5 - "+pet.getIdadeFormatada()+" anos\n"+
                        "6 - "+pet.getPesoFormatado()+"kg\n"+
                        "7 - "+pet.getRacaFormatada());

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar no arquivo", e);
        }
    }
}
