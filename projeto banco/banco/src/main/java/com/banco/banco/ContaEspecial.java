package com.banco.banco;

import jakarta.persistence.Entity;

@Entity
public class ContaEspecial extends Conta {
    private double limite;

    public ContaEspecial() {}

    public ContaEspecial(String numero, double saldo, double limite) {
        super();
        this.limite = limite;
    }

    public Double getLimite() { return limite; }
    public void setLimite(double limite) { this.limite = limite; }
}
