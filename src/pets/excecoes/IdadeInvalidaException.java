package pets.excecoes;

public class IdadeInvalidaException extends PetValidacaoException {
    public IdadeInvalidaException(String mensagem) {
        super(mensagem);
    }
}
