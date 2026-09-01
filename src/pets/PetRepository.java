package pets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PetRepository {
    private static final Path PASTA = Path.of("data/petsCadastrados");

    public PetRepository() {
        criarPasta();
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

    public List<Pet> listarTodos() {
        File pasta = new File(PASTA.toString());
        File[] arquivos = pasta.listFiles((dir, nome) -> nome.endsWith(".txt"));
        List<Pet> pets = new ArrayList<>();

        if (arquivos == null) return pets;
        for (File arquivo : arquivos) {
            pets.add(lerArquivo(arquivo));
        }

        return pets;
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

    private Pet lerArquivo(File arquivo) {
        try {
            List<String> linhas = Files.readAllLines(arquivo.toPath());

            String nome = linhas.get(0).split(" - ")[1];
            TipoPet tipo = TipoPet.fromTexto(linhas.get(1).split(" - ")[1]);
            SexoPet sexo = SexoPet.fromTexto(linhas.get(2).split(" - ")[1]);
            String idadeStr = linhas.get(4).split(" - ")[1];
            String pesoStr = linhas.get(5).split(" - ")[1];
            String raca = linhas.get(6).split(" - ")[1];

            String endereco = linhas.get(3).split(" - ", 2)[1];
            String semPrefixo = endereco.startsWith("Rua ") ? endereco.substring(4) : endereco;
            String[] enderecoPartes = Arrays.stream(semPrefixo.split(","))
                    .map(String::trim)
                    .map(campo -> campo.equals(Constantes.NAO_INFORMADO) ? null : campo)
                    .toArray(String[]::new);
            Endereco petEndereco = new Endereco(enderecoPartes);

            Double idade = idadeStr.startsWith(Constantes.NAO_INFORMADO) ? null :
                Double.parseDouble(idadeStr.split(" ")[0]);
            Double peso = pesoStr.startsWith(Constantes.NAO_INFORMADO) ? null :
                Double.parseDouble(pesoStr.split("kg")[0].trim());
            String racaNormalizada = raca.equals(Constantes.NAO_INFORMADO) ? null : raca;

            return new Pet(nome, tipo, sexo, petEndereco, idade, peso, racaNormalizada);
 
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo", e);
        }
    }
}
