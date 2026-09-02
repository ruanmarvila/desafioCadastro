package pets;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import pets.excecoes.IdadeInvalidaException;
import pets.excecoes.PesoInvalidoException;

public class PetService {
    private static PetRepository repo = new PetRepository();

    public Pet cadastrar(String nome, String tipoOpcao, String sexoOpcao, String[] enderecoCampos, 
        String[] idadeCampos, String pesoStr, String raca){

        TipoPet tipoPet = TipoPet.fromOpcao(tipoOpcao);
        SexoPet sexoPet = SexoPet.fromOpcao(sexoOpcao);
        Endereco endereco = new Endereco(enderecoCampos);
        Double idade = parseIdade(idadeCampos);
        Double peso = parsePeso(pesoStr);

        Pet pet = new Pet(nome, tipoPet, sexoPet, endereco, idade, peso, raca);
        repo.salvarPet(pet);
        return pet;
    }

    public List<String> buscarTodos() {
        return formatarLista(repo.buscarTodos());
    }

    public List<Pet> buscarPorCriterios(String tipoOpcao, Map<CriterioBusca, String> criterios) {
        TipoPet tipoPet = TipoPet.fromOpcao(tipoOpcao);
        List<Pet> todos = repo.buscarTodos();

        Predicate<Pet> filtro = pet -> pet.getTipoPet() == tipoPet;

        for (Map.Entry<CriterioBusca, String> entry : criterios.entrySet()) {
            filtro = filtro.and(criarPredicate(entry.getKey(), entry.getValue()));
        }

       return todos.stream().filter(filtro).toList();
    }

    public Pet alterarPet(Pet petEscolhido, String novoNome, String[] novoEndereco, String[] novaIdade, String novoPeso, String novaRaca) {
        if (novoNome != null && !novoNome.isBlank()) {
            petEscolhido.setNome(novoNome);
        }
        Double idade = parseIdade(novaIdade);
        if (idade != null) {
            petEscolhido.setIdade(idade);
        }
        Double peso = parsePeso(novoPeso);
        if (peso != null) {
            petEscolhido.setPeso(peso);
        }
        if (novaRaca != null && !novaRaca.isBlank()) {
            petEscolhido.setRaca(novaRaca);
        }
        if (novoEndereco.length > 0 && novoEndereco[0] != null && !novoEndereco[0].isBlank()) {
            petEscolhido.getEndereco().setRua(novoEndereco[1]);
        }
        if (novoEndereco.length > 1 && novoEndereco[1] != null && !novoEndereco[0].isBlank()) {
            petEscolhido.getEndereco().setNumero(novoEndereco[1]);
        }
        if (novoEndereco.length > 2 && novoEndereco[2] != null && !novoEndereco[0].isBlank()) {
            petEscolhido.getEndereco().setCidade(novoEndereco[2]);
        }

        repo.atualizarPet(petEscolhido);
        return petEscolhido;
    }

    public void deletarPet(Pet pet) {
        repo.deletarPet(pet);
    }

    public List<String> formatarLista(List<Pet> pets) {
        List<String> formatados = new ArrayList<>();
        int contador = 1;
        for (Pet pet : pets) {
            formatados.add(contador + ". " + formatarPet(pet));
            contador++;
        }

        return formatados;
    }

    private static Double parseIdade(String[] idadeCampos) {
        String idadeStr = idadeCampos[0];
        String sufixo = idadeCampos.length > 1 ? idadeCampos[1] : null;

        if (idadeStr == null || idadeStr.isBlank()) {
            return null;
        }

        String idadeNormalizada = idadeStr.replace(",", ".");

        Double idade;
        try {
            idade = Double.parseDouble(idadeNormalizada);
        } catch (NumberFormatException e) {
            throw new IdadeInvalidaException("Idade deve ser um número válido");
        }

        if (sufixo != null && sufixo.equalsIgnoreCase("meses")) {
            idade /= 12;
        }
        return idade;
    }

    private static Double parsePeso(String pesoStr) {
        if (pesoStr == null || pesoStr.isBlank()) {
            return null;
        }

        String pesoNormalizado = pesoStr.replace(",", ".");

        Double peso;
        try {
            peso = Double.parseDouble(pesoNormalizado);
        } catch (NumberFormatException e) {
            throw new PesoInvalidoException("Peso deve ser um número válido.");
        }
        return peso;
    }

    private String formatarPet(Pet pet) {
        return pet.getNome() + " - " + 
        pet.getTipoPet().getFormatado() + " - " + 
        pet.getSexoPet().getFormatado() + " - Rua " + 
        pet.getEndereco().getRua() + ", " + 
        pet.getEndereco().getNumeroFormatado() + ", " + 
        pet.getEndereco().getCidade() + " - " + 
        pet.getIdade() + " anos" + " - " + 
        pet.getPeso() + "kg - " + 
        pet.getRaca();
    }

    private Predicate<Pet> criarPredicate(CriterioBusca criterio, String valor) {
        return switch(criterio) {
            case NOME -> pet -> normalizar(pet.getNome()).contains(normalizar(valor));
            case SEXO -> pet -> pet.getSexoPet() == SexoPet.fromOpcao(valor);
            case IDADE -> pet -> pet.getIdade() != null
                && pet.getIdade().equals(Double.parseDouble(valor.replace(",", ".")));
            case PESO -> pet -> pet.getPeso() != null
                && pet.getPeso().equals(Double.parseDouble(valor.replace(",", ".")));
            case RACA -> pet -> pet.getRaca() != null
                && normalizar(pet.getRaca()).equals(normalizar(valor));
            case ENDERECO -> pet -> pet.getEndereco() != null
                && (normalizar(pet.getEndereco().getRua()).contains(normalizar(valor))
                    || normalizar(pet.getEndereco().getCidade()).contains(normalizar(valor)));
        };
    }

    private String normalizar(String texto) {
        if (texto == null) return "";
        String semAcento = Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return semAcento.toLowerCase();
    }
}
