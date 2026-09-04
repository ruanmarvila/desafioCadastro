package formulario;

import java.util.ArrayList;
import java.util.List;

public class FormularioService {
    private static final FormularioRepository repo = new FormularioRepository();

    public List<Pergunta> lerPerguntas() {
        List<String> linhas = repo.lerLinhas();
        List<Pergunta> perguntas = new ArrayList<>();

        for (String linha : linhas) {
            perguntas.add(parseLinha(linha));
        }
        return perguntas;
    }

    public List<Pergunta> lerPerguntasExtras() {
        return repo.lerLinhas().stream()
            .map(l -> parseLinha(l))
            .filter(p -> p.isOriginal() == false)
            .toList();
    }

    public void criarPergunta(String novaPergunta) {
        int numeroPergunta = repo.lerLinhas().size() + 1;
        Pergunta pergunta = new Pergunta(numeroPergunta, novaPergunta);
        repo.salvarPergunta(pergunta);
    }

    public void alterarPergunta(Pergunta pergunta, String novoTexto) {
        repo.atualizarPergunta(pergunta, novoTexto);
    }

    private Pergunta parseLinha(String linha) {
        String[] partes = linha.split(" - ", 2);
        Integer numero = Integer.parseInt(partes[0].trim());
        String texto = partes[1].trim();
        return new Pergunta(numero, texto);
    }
}
