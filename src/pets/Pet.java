package pets;

import pets.excecoes.IdadeInvalidaException;
import pets.excecoes.NomeInvalidoException;
import pets.excecoes.PesoInvalidoException;
import pets.excecoes.RacaInvalidaException;

public class Pet {
    private static final String NAO_INFORMADO = "NÃO INFORMADO";

    private String nome;
    private TipoPet tipoPet;
    private SexoPet sexoPet;
    private Endereco endereco;
    private Double idade;
    private Double peso;
    private String raca;

    public Pet(String nome, TipoPet tipoPet, SexoPet sexoPet, Endereco endereco, Double idade, Double peso, String raca) {
        setNome(nome);
        this.tipoPet = tipoPet;
        this.sexoPet = sexoPet;
        this.endereco = endereco;
        setIdade(idade);
        setPeso(peso);
        setRaca(raca);
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new NomeInvalidoException("O nome é obrigatório.");
        }
        if (!nome.matches("[a-zA-ZÀ-ÿ]+( [a-zA-ZÀ-ÿ]+)+")) {
            throw new NomeInvalidoException("O pet precisa de nome e sobrenome");
        }
    }
    
    public String getNome() {
        return nome;
    }

    public String getNomeNormalizado() {
        return getNome().replaceAll("\\s+", "").toUpperCase();
    }

    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    public TipoPet getTipoPet() {
        return tipoPet;
    }

    public SexoPet getSexoPet() {
        return sexoPet;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Double getIdade() {
        return idade;
    }

    public String getIdadeFormatada() {
        return idade != null ? String.valueOf(idade) : NAO_INFORMADO;
    }

    public void setIdade(Double idade) {
        if (idade != null && idade > 20) {
            throw new IdadeInvalidaException("Idade não pode ser maior que 20 anos.");
        }
        this.idade = idade;
    }

    public Double getPeso() {
        return peso;
    }

    public String getPesoFormatado() {
        return peso != null ? String.valueOf(peso) : NAO_INFORMADO;
    }

    public void setPeso(Double peso) {
        if (peso != null && (peso < 0.5 || peso > 60)) {
            throw new PesoInvalidoException("O peso precisa estar entre 0.5kg e 60kg.");
        }
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public String getRacaFormatada() {
        return raca != null || raca.isBlank() ? raca : NAO_INFORMADO;
    }

    public void setRaca(String raca) {
        if (raca != null && !raca.matches("[a-zA-Z]+")) {
            throw new RacaInvalidaException("Raça não pode conter números nem caracteres especiais.");
        }
        this.raca = raca;
    }
}
