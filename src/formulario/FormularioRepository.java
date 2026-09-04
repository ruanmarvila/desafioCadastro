package formulario;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import formulario.excecoes.PerguntaNaoEncontradaexception;

public class FormularioRepository {
    private static final Path pathForm = Path.of("data/formulario.txt");

    public void salvarPergunta(Pergunta pergunta) {
        List<String> linhas = new ArrayList<>(lerLinhas());
        linhas.add(pergunta.getNumero() + " - " + pergunta.getTexto());
        salvarLinhas(linhas);
    }

    public List<String> lerLinhas() {
        try {
            return Files.readAllLines(pathForm);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o formulário", e);
        }
    }

    public void atualizarPergunta(Pergunta pergunta, String novoTexto) {
        List<String> linhas = lerLinhas();
        ListIterator<String> it = linhas.listIterator();
        boolean econtrado = false;

        while (it.hasNext()) {
            String numero = it.next().split(" - ")[0];

            if (numero.equals(String.valueOf(pergunta.getNumero()))) {
                it.set(numero + " - " + novoTexto);
                econtrado = true;
                break;
            }
        }

        if (!econtrado) {
            throw new PerguntaNaoEncontradaexception("Pergunta não encontrada.");
        }
        
        salvarLinhas(linhas);
    }

    private void salvarLinhas(List<String> linhas) {
        String conteudo = String.join("\n", linhas);

        try (BufferedWriter writer = Files.newBufferedWriter(pathForm)) {
            writer.write(conteudo.toString());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar no formulário", e);
        }
    }
}
