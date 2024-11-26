package com.banco.banco;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Especial")
public class ContaEspecial extends Conta {

    @Column(nullable = false)
    private double limite;

    // Construtor
    public ContaEspecial(String numero, double saldo, double limite) {
        this.setNumero(numero);
        this.setSaldo(saldo);
        this.setTipo("ContaEspecial");
        this.limite = limite;
    }

    public ContaEspecial() {
        // Construtor padrão
    }

    // Getters e setters
    public Double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }
}
