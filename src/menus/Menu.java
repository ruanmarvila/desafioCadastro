package menus;

import java.util.List;
import java.util.Scanner;

import formulario.FormularioService;
import pets.Pet;
import pets.PetService;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private final FormularioService formService = new FormularioService();
    private final PetService petService = new PetService();

    public void menuInicial() {
        while (true) {
            System.out.println(
                "1. Cadastrar um novo pet\n" +
                "2. Alterar os dados do pet cadastrado\n" +
                "3. Deletar um pet cadastrado\n" +
                "4. Listar todos os pets cadastrados\n" +
                "5. Listar pets por algum critério (idade, nome, raça)\n" +
                "6. Sair"
            );

            try {
                System.out.println("Escolha uma opção de 1 a 6");
                String opcao = scanner.nextLine();

                switch (opcao) {
                    case "1" -> {
                        cadastrarPet();
                    }
                    case "6" -> {
                        System.out.println("Saindo...");
                    }
                    default -> {
                        System.out.println("Opção inválida. Tente novamente!");
                    }
                }

                if (opcao.equals("6")) {
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void cadastrarPet() {
        List<String> perguntas = formService.lerPerguntas();

        System.out.println(perguntas.get(0));
        String nome = scanner.nextLine().trim();

        System.out.println(perguntas.get(1));
        System.out.println(
            "1. Cachorro\n" +
            "2. Gato"
        );
        String tipo = scanner.nextLine().trim();

        System.out.println(perguntas.get(2));
        System.out.println(
            "1. Macho\n" +
            "2. Fêmea"
        );
        String sexo = scanner.nextLine().trim();

        System.out.println(perguntas.get(3));
        String[] endereco = scanner.nextLine().trim().split("\\s+");

        System.out.println(perguntas.get(4));
        String[] idade = scanner.nextLine().trim().split("\\s+");

        System.out.println(perguntas.get(5));
        String peso = scanner.nextLine().trim();

        System.out.println(perguntas.get(6));
        String raca = scanner.nextLine().trim();

        Pet pet = petService.cadastrar(nome, tipo, sexo, endereco, idade, peso, raca);
        System.out.println("Pet cadastrado com sucesso: " + pet.getNome());
    }
}
