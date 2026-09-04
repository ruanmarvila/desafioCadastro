package pets;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import formulario.FormularioService;
import formulario.Pergunta;
import formulario.RespostaExtras;

public class MenuPet {
    private final Scanner scanner;
    private final FormularioService formService;
    private final PetService petService;

    public MenuPet(Scanner scanner) {
        this.scanner = scanner;
        this.formService = new FormularioService();
        this.petService = new PetService();
    }

    public void exibir() {
        while (true) {
            System.out.println(
                "1. Cadastrar um novo pet\n" +
                "2. Listar pets por algum critério (idade, nome, raça)\n" +
                "3. Alterar os dados do pet cadastrado\n" +
                "4. Deletar um pet cadastrado\n" +
                "5. Listar todos os pets cadastrados\n" +
                "6. Sair"
            );

                System.out.println("Escolha uma opção de 1 a 6");
                String opcao = scanner.nextLine().trim();

                switch (opcao) {
                    case "1" -> cadastrarPet();
                    case "2" -> {
                        ResultadoBusca resultado = listarPetsFiltrados();
                        exibirPets(resultado.pets(), resultado.criterios());
                    }
                    case "3" -> alterarPet();
                    case "4" -> deletarPet();
                    case "5" -> exibirPets(listarPets(), null);
                    case "6" -> {
                        System.out.println("Saindo...");
                        System.exit(0);
                    }
                    default -> System.out.println("Opção inválida. Tente novamente!");
                };
        }
    }

    private void cadastrarPet() {
        List<Pergunta> perguntas = formService.lerPerguntas();

        System.out.println(perguntas.get(0).getTexto());
        String nome = scanner.nextLine().trim();

        System.out.println(perguntas.get(1).getTexto());
        System.out.println(
            "1. Cachorro\n" +
            "2. Gato"
        );
        String tipo = scanner.nextLine().trim();

        System.out.println(perguntas.get(2).getTexto());
        System.out.println(
            "1. Macho\n" +
            "2. Fêmea"
        );
        String sexo = scanner.nextLine().trim();

        System.out.println(perguntas.get(3).getTexto());
        System.out.println("Rua:");
        String rua = scanner.nextLine().trim();

        System.out.println("Número da casa:");
        String numero = scanner.nextLine().trim();

        System.out.println("Cidade:");
        String cidade = scanner.nextLine().trim();

        String[] enderecoCampos = {rua, numero, cidade};

        System.out.println(perguntas.get(4).getTexto());
        String[] idade = scanner.nextLine().trim().split("\\s+");

        System.out.println(perguntas.get(5).getTexto());
        String peso = scanner.nextLine().trim();

        System.out.println(perguntas.get(6).getTexto());
        String raca = scanner.nextLine().trim();

        List<RespostaExtras> extras = new ArrayList<>();
        if (perguntas.size() > 7) {
            for (int i = 7; i < perguntas.size(); i++) {
                System.out.println(perguntas.get(i).getTexto());
                String respota = scanner.nextLine().trim();
                extras.add(new RespostaExtras(perguntas.get(i), respota));
            }
        }

        Pet pet = petService.cadastrar(nome, tipo, sexo, enderecoCampos, idade, peso, raca, extras);
        System.out.println("Pet cadastrado com sucesso: " + pet.getNome());
    }

    private List<Pet> listarPets() {
        return petService.buscarTodos();
    }

    private ResultadoBusca listarPetsFiltrados() {
        System.out.println(
            "Escolha o tipo do animal: " +
            "1. Cachorro\n" +
            "2. Gato"
        );
        String tipoOpcao = scanner.nextLine().trim();

        System.out.println("Quer buscar por data de cadastro?");
        String resposta = scanner.nextLine().trim().toUpperCase();

        Integer mes = null, ano = null;
        if (resposta.equals("SIM")) {
            System.out.println("Digite o mês:");
            mes = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Digite o ano:");
            ano =  Integer.parseInt(scanner.nextLine().trim());
        } 

        Map<CriterioBusca, String> criteriosOpcionais = criteriosOpcionais();
        List<Pet> resultado = petService.buscarPorCriterios(tipoOpcao, criteriosOpcionais, mes, ano);

        return new ResultadoBusca(resultado, criteriosOpcionais);
    }

    private void alterarPet() {
        Pet petEscolhido = buscarESelecionarPet();
        List<Pergunta> perguntasExtras = formService.lerPerguntasExtras();

        System.out.println("Novo nome: ");
        String novoNome = scanner.nextLine().trim();

        System.out.println("Novo endereço: ");
        System.out.println("Rua:");
        String novaRua = scanner.nextLine().trim();

        System.out.println("Número da casa:");
        String novoNumero = scanner.nextLine().trim();

        System.out.println("Cidade:");
        String novaCidade = scanner.nextLine().trim();

        String[] novoEnderecoCampos = {novaRua, novoNumero, novaCidade};

        System.out.println("Nova idade:");
        String[] novaIdade = scanner.nextLine().trim().split("\\s+");

        System.out.println("Novo peso:");
        String novoPeso = scanner.nextLine().trim();

        System.out.println("Nova raça:");
        String novaRaca = scanner.nextLine().trim();

        List<RespostaExtras> extras = new ArrayList<>();
        for (Pergunta pergunta : perguntasExtras) {
            System.out.println(pergunta);
            String resposta = scanner.nextLine().trim();
            extras.add(new RespostaExtras(pergunta, resposta));
        }

        Pet pet = petService.alterarPet(petEscolhido, novoNome, novoEnderecoCampos, novaIdade, novoPeso, novaRaca);
        System.out.println("Pet atualizado com sucesso: " + pet.getNome());
    }

    public void deletarPet() {
        Pet petEscolhido = buscarESelecionarPet();

        String resposta;
        while (true) {
            System.out.println("Tem certeza que deseja excluir: " + petEscolhido.getNome());
            resposta = scanner.nextLine().trim().toUpperCase();

            if (resposta.equals("SIM") || resposta.equals("NÃO")) {
                break;
            }
        }

        if (resposta.equals("NÃO")) {
            System.out.println("Exclusão cancelada.");
            return;
        }
        petService.deletarPet(petEscolhido);
        System.out.println("Pet excluído com sucesso!");
    }

    private Map<CriterioBusca, String> criteriosOpcionais() {
        Map<CriterioBusca, String> criterios = new LinkedHashMap<>();

        for(int i = 0; i < 2; i++) {
            System.out.println(
                "Escolha um critéiro opcional (ou 0 para pular):\n" +
                "1. Nome\n2. Sexo\n3. Idade\n4. Peso\n5. Raça\n 6. Endereço"
            );
            String opcao = scanner.nextLine().trim();

            if (opcao.equals("0")) break;

            CriterioBusca criterio = CriterioBusca.fromOpcao(opcao);

            System.out.println("Digite o valor para " + criterio + ":");
            String valor = scanner.nextLine().trim();
            criterios.put(criterio, valor);
        }

        return criterios;
    }

    private void exibirPets(List<Pet> pets, Map<CriterioBusca, String> criterios) {
        if (pets.isEmpty()) {
            System.out.println("Nenhum pet encontrado.");
            return;
        }

        petService.formatarLista(pets, criterios).forEach(System.out::println);
    }

    private Pet buscarESelecionarPet() {
        ResultadoBusca busca = listarPetsFiltrados();
        exibirPets(busca.pets(), busca.criterios());

        if (busca.pets().isEmpty()) return null;

        int escolha;
        while (true) {
            System.out.println("Escolha o número do pet que deseja alterar: ");
            escolha = Integer.parseInt(scanner.nextLine().trim());

            if (escolha > 0 && escolha <=busca.pets().size()) {
                break;
            }
        }
        return busca.pets().get(escolha - 1);
    }
    
    private record ResultadoBusca(List<Pet> pets, Map<CriterioBusca, String> criterios) {}
}
