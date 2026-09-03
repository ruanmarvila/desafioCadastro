import java.util.Scanner;

import formulario.MenuFormulario;
import pets.MenuPet;

public class TelaInicial {
    private final Scanner scanner;
    private final MenuPet menuPet;
    private final MenuFormulario menuForm;

    public TelaInicial(Scanner scanner) {
        this.scanner = scanner;
        this.menuPet = new MenuPet(scanner);
        this.menuForm = new MenuFormulario(scanner);
    }

    public void exibir() {
        while (true) {
            System.out.println(
                "1. Iniciar o sistema para cadastro de PETS\n" + 
                "2. Iniciar o sistema para alterar formulário\n" +
                "3. Sair"
            );
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> menuPet.exibir();
                case "2" -> menuForm.exibir();
                case "3" -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
