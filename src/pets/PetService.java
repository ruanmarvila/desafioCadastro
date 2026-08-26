package pets;

import pets.excecoes.IdadeInvalidaException;
import pets.excecoes.PesoInvalidoException;

public class PetService {
    private static PetRepository repo = new PetRepository();

    private static Double validarIdade(String[] campos) {
        String idadeStr = campos[0];
        String sufixo = campos.length > 1 ? campos[1] : null;

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

    private static Double validarPeso(String pesoStr) {
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

    public Pet cadastrar(String nome, String tipoOpcao, String sexoOpcao, String[] enderecoCampos, 
        String[] idadeCampos, String pesoStr, String raca){

        TipoPet tipoPet = TipoPet.fromOpcao(tipoOpcao);
        SexoPet sexoPet = SexoPet.fromOpcao(sexoOpcao);
        Endereco endereco = Endereco.criarEndereco(enderecoCampos);
        Double idade = validarIdade(idadeCampos);
        Double peso = validarPeso(pesoStr);

        Pet pet = new Pet(nome, tipoPet, sexoPet, endereco, idade, peso, raca);
        repo.salvarPet(pet);
        return pet;
    }
}
