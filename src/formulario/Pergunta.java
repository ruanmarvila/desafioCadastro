package formulario;

public class Pergunta {
    private static final int ORIGINAIS = 7;

    private int numero;
    private String texto;

    public Pergunta(Integer numero, String texto) {
        this.numero = numero;
        this.texto = texto;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getTexto() {
        return texto;
    }

    public boolean isOriginal() {
        if (numero > ORIGINAIS) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return numero + " - " + texto;
    }
}
