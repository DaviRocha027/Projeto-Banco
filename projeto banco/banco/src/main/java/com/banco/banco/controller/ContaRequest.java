package com.banco.banco.controller;

public class ContaRequest {
    private String numero; // Número da conta
    private String tipo;   // Tipo da conta: "Corrente", "Especial" ou "Poupança"
    private double saldo;  // Saldo inicial
    private Double limite; // Limite da conta especial (pode ser nulo)
    private Double rendimento; // Rendimento da conta poupança (pode ser nulo)

    // Getters e Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
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
}
