package formulario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FormularioService {
    private static final Path pathForm = Path.of("data/formulario.txt");

    public List<String> lerPerguntas() {
        try {
            return Files.readAllLines(pathForm);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o formulário", e);
        }
    }
}
