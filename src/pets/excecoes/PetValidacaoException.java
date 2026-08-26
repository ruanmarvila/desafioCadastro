package pets.excecoes;

public class PetValidacaoException extends RuntimeException {
    public PetValidacaoException(String mensagem) {
        super(mensagem);
    }
}
