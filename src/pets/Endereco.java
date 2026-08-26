package pets;

import pets.excecoes.EnderecoInvalidoException;

public class Endereco {
    private static final String NAO_INFORMADO = "NÃO INFORMADO";

    private String rua;
    private String numero;
    private String cidade;

    private Endereco(String rua, String numero, String cidade) {
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
    }

    public static Endereco criarEndereco(String[] campos) {
        if (campos.length < 3) {
            throw new EnderecoInvalidoException("Endereço inválido.");
        }

        String rua = campos[0].isBlank() ? null : campos[0];
        String numero = campos[1].isBlank() ? null : campos[1];
        String cidade = campos[2].isBlank() ? null : campos[2];

        if (rua == null || cidade == null) {
            throw new EnderecoInvalidoException("Rua e cidade são obrigatórios.");
        }

        return new Endereco(rua, numero, cidade);
    }

    public String getNumero() {
        return numero;
    }

    public String getNumeroFormatado() {
        return numero != null ? numero : NAO_INFORMADO;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
