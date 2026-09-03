package formulario;

import java.util.Scanner;

public class MenuFormulario {
    private final Scanner scanner;
    private final FormularioService formService;

    public MenuFormulario(Scanner scanner) {
        this.scanner = scanner;
        this.formService = new FormularioService();
    }

    public void exibir() {
        while (true) {
            System.out.println(
                "1. Criar nova pergunta\n" +
                "2. Alterar pergunta existente\n" +
                "3. Excluir pergunta existente\n" +
                "4. Voltar para o menu inicial\n" +
                "5. Sair"
            );

            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> criarPergunta();
                case "4" -> {return;}
                case "5" -> {
                    System.out.println("Saindo...");
                    System.exit(0);
                }
            }
        }
    }

    private void criarPergunta() {
        System.out.println("Digite uma nova pergunta:");
        String pergunta = scanner.nextLine().trim();
        
        formService.criarPergunta(pergunta);
        System.out.println("Pergunta criada com sucesso!");
    }
}
