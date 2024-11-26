package com.banco.banco.view;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;


public class ContaRequest {
	 @NotNull(message = "O número da conta é obrigatório.")
	    private String numero;

	    @NotNull(message = "O saldo inicial é obrigatório.")
	    private Double saldo;

	    @NotNull(message = "O tipo da conta é obrigatório.")
	    private String tipo;

	    private Double limite;
	    private Double rendimento; 
    // Construtor
    public ContaRequest(String numero, Double saldo, String tipo, Double limite, Double rendimento) {
        this.numero = numero;
        this.saldo = saldo;
        this.tipo = tipo;
        this.limite = limite;
        this.rendimento = rendimento;
    }
    public ContaRequest() {
        // Construtor padrão
    }


    // Getters e setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Double getRendimento() {
        return rendimento;
    }

    public void setRendimento(Double rendimento) {
        this.rendimento = rendimento;
    }
    
    @Override
    public String toString() {
        return "ContaRequest{" +
                "numero='" + numero + '\'' +
                ", saldo=" + saldo +
                ", tipo='" + tipo + '\'' +
                ", limite=" + limite +
                ", rendimento=" + rendimento +
                '}';
    }
}
