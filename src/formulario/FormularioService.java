package formulario;

import java.util.List;

public class FormularioService {
    private static final FormularioRepository repo = new FormularioRepository();

    public List<String> lerPerguntas() {
        return repo.lerPerguntas();
    }

    public void criarPergunta(String novaPergunta) {
        int numeroPergunta = repo.lerPerguntas().size() + 1;
        Pergunta pergunta = new Pergunta(numeroPergunta, novaPergunta);
        repo.salvarPergunta(pergunta);
    }
}
