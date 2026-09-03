package formulario;

public class Pergunta {
    
    private Integer numero;
    private String texto;

    public Pergunta(Integer numero, String texto) {
        this.numero = numero;
        this.texto = texto;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
