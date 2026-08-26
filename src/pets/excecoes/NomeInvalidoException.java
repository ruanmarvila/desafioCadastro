package pets.excecoes;

public class NomeInvalidoException extends PetValidacaoException {
    public NomeInvalidoException(String mensagem) {
        super(mensagem);
    }
}
