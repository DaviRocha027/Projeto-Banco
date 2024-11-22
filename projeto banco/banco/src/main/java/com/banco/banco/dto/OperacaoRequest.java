package com.banco.banco.dto;

public class OperacaoRequest {

    private String numero;
    private double valor;

    // Getters e setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
