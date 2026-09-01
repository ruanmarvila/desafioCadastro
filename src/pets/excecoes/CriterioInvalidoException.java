package pets.excecoes;

public class CriterioInvalidoException extends PetValidacaoException {
    public CriterioInvalidoException(String mensagem) {
        super(mensagem);
    }
}
