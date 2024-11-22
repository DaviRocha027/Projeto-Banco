import com.google.gson.Gson;

public class TestGson {
    public static void main(String[] args) {
        // Criando um objeto de teste
        Pessoa pessoa = new Pessoa("João", 30);

        // Convertendo o objeto para JSON
        Gson gson = new Gson();
        String json = gson.toJson(pessoa);
        System.out.println("Objeto em JSON: " + json);

        // Convertendo JSON para objeto
        Pessoa pessoa2 = gson.fromJson(json, Pessoa.class);
        System.out.println("Objeto reconvertido: " + pessoa2.getNome() + ", " + pessoa2.getIdade());
    }
}

class Pessoa {
    private String nome;
    private int idade;

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }
}
