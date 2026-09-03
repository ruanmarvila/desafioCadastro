package formulario;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FormularioRepository {
    private static final Path pathForm = Path.of("data/formulario.txt");

    public void salvarPergunta(Pergunta pergunta) {
        try (BufferedWriter writer = Files.newBufferedWriter(pathForm, StandardOpenOption.APPEND)) {
            writer.append(pergunta.getNumero() + " - " + pergunta.getTexto());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a pergunta no formulário", e);
        }
    }

    public List<String> lerPerguntas() {
        try {
            return Files.readAllLines(pathForm);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o formulário", e);
        }
    }
}
