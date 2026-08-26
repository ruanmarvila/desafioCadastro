package pets.excecoes;

public class TipoPetInvalidoException extends PetValidacaoException {
    public TipoPetInvalidoException(String mensagem) {
        super(mensagem);
    }
}
