package com.banco.banco;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Corrente")
public class ContaCorrente extends Conta {

    // Construtor
    public ContaCorrente(String numero, double saldo) {
        this.setNumero(numero);
        this.setSaldo(saldo);
        this.setTipo("ContaCorrente");
    }

    public ContaCorrente() {
        // Construtor padrão
    }
}
