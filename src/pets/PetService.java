package pets;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> listarTodos() {
        List<Pet> petsLista = repo.listarTodos();
        List<String> pets = new ArrayList<>();

        int contador = 1;
        for (Pet pet : petsLista) {
            pets.add(contador + ". " + formatarPet(pet));
            contador++;
        }

        if (pets.isEmpty()) {
            return List.of("[]");
        }

        return pets;
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
}
