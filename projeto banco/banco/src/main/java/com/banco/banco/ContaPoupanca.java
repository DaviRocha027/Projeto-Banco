package com.banco.banco;

import jakarta.persistence.Entity;

@Entity
public class ContaPoupanca extends Conta {
    private double rendimento;

    public ContaPoupanca() {}

    public ContaPoupanca(String numero, double saldo, double rendimento) {
        super();
        this.rendimento = rendimento;
    }

    public Double getRendimento() { return rendimento; }
    public void setRendimento(double rendimento) { this.rendimento = rendimento; }
}