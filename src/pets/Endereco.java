package pets;

import pets.excecoes.EnderecoInvalidoException;

public class Endereco {
    private static final String NAO_INFORMADO = "NÃO INFORMADO";

    private String rua;
    private String numero;
    private String cidade;

    public Endereco(String rua, String numero, String cidade) {
        setRua(rua);
        setNumero(numero);
        setCidade(cidade);
    }

    public Endereco(String[] campos) {
        this(
            campos.length > 0 ? campos[0] : null,
            campos.length > 1 ? campos[1] : null,
            campos.length > 2 ? campos[2] : null
        );
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        if (rua == null || rua.isBlank()) {
            throw new EnderecoInvalidoException("Rua é obrigatória.");
        }
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getNumeroFormatado() {
        return numero != null ? numero : NAO_INFORMADO;
    }

    public void setNumero(String numero) {
        this.numero = numero == null || numero.isBlank() ? null : rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        if (cidade == null || cidade.isBlank()) {
            throw new EnderecoInvalidoException("Cidade é obrigatória.");
        }
        this.cidade = cidade;
    }
}
