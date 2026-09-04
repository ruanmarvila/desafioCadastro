package formulario;

import java.util.List;
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
                case "2" -> alterarPergunta();
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

    private void alterarPergunta() {
        List<Pergunta> perguntas = formService.lerPerguntasExtras();

        if (perguntas.isEmpty()) {
            System.out.println("Nenhuma pergunta encontrada.");
            return;
        }

        perguntas.forEach(System.out::println);

        Pergunta perguntaEscolhida = null;
        while (perguntaEscolhida == null) {
            System.out.println("Escolha o número da pergunta que deseja alterar:");
            String entrada = scanner.nextLine().trim();

            int escolha;
            try {
                escolha = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
                continue;
            }

            int numeroEscolha = escolha;
            perguntaEscolhida = perguntas.stream()
                .filter(p -> p.getNumero() == numeroEscolha)
                .findFirst()
                .orElse(null);

            if (perguntaEscolhida == null) {
                System.out.println("Número inválido, escolha uma das perguntas listadas.");
            }
        }

        System.out.println("Nova pergunta:");
        String novoTexto = scanner.nextLine().trim();

        formService.alterarPergunta(perguntaEscolhida, novoTexto);
        System.out.println("Pergunta alterada com sucesso.");
    }
}
